package com.doorcii.messagecenter.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class GroupSequenceDAO implements SequenceDAO {

	private static final Logger log = Logger.getLogger(GroupSequenceDAO.class);

	private static final int DEFAULT_INNER_STEP = 1000;

	private static final int DEFAULT_RETRY_TIMES = 2;

	private static final String DEFAULT_TABLE_NAME = "sequence";
	private static final String DEFAULT_NAME_COLUMN_NAME = "name";
	private static final String DEFAULT_VALUE_COLUMN_NAME = "value";
	private static final String DEFAULT_GMT_MODIFIED_COLUMN_NAME = "gmt_modified";

	private static final Boolean DEFAULT_ADJUST = false;

	private static final long DELTA = 100000000L;

	private int startNum = 100;
	
	/**
	 * 数据源
	 */
	private DataSource dataSource;

	/**
	 * 自适应开关
	 */
	private boolean adjust = DEFAULT_ADJUST;
	/**
	 * 重试次数
	 */
	private int retryTimes = DEFAULT_RETRY_TIMES;

	/**
	 * 内步长
	 */
	private int innerStep = DEFAULT_INNER_STEP;

	/**
	 * 序列所在的表名
	 */
	private String tableName = DEFAULT_TABLE_NAME;

	/**
	 * 存储序列名称的列名
	 */
	private String nameColumnName = DEFAULT_NAME_COLUMN_NAME;

	/**
	 * 存储序列值的列名
	 */
	private String valueColumnName = DEFAULT_VALUE_COLUMN_NAME;

	/**
	 * 存储序列最后更新时间的列名
	 */
	private String gmtModifiedColumnName = DEFAULT_GMT_MODIFIED_COLUMN_NAME;

	private volatile String selectSql;
	private volatile String updateSql;
	private volatile String insertSql;

	public void init() throws SequenceException {
		if(dataSource == null) {
			throw new SequenceException("data source does not exist.");
		}
	}

	/**
	 * 检查并初试某个sequence 1、如果sequece不存在，插入值，并初始化值 2、如果已经存在，但有重叠，重新生成
	 * 3、如果已经存在，且无重叠。
	 * 
	 * @throws SequenceException
	 */
	public void adjust(String name) throws SequenceException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(getSelectSql());
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			int item = 0;
			while (rs.next()) {
				item++;
				long val = rs.getLong(this.getValueColumnName());
				this.adjustUpdate(val, name);
			}
			if (item == 0) {
				this.adjustInsert(name);
			}
		} catch (SQLException e) {// 吞掉SQL异常
			log.error("初值校验和自适应过程中出错.", e);
			throw e;
		} finally {
			closeResultSet(rs);
			rs = null;
			closeStatement(stmt);
			stmt = null;
			closeConnection(conn);
			conn = null;
	
		}
	}

	/**
	 * 更新
	 * @param index
	 * @param value
	 * @param name
	 * @throws SequenceException
	 * @throws SQLException
	 */
	private void adjustUpdate(long value, String name)
			throws SequenceException, SQLException {
		long newValue = value + innerStep;// 设置成新的调整值
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(getUpdateSql());
			stmt.setLong(1, newValue);
			stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			stmt.setString(3, name);
			stmt.setLong(4, value);
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SequenceException(
						"faild to auto adjust init value at  " + name
								+ " update affectedRow =0");
			}
			log.info("更新初值成功!" + "sequence Name："+ name + "更新过程：" + value + "-->" + newValue);
		} catch (SQLException e) { // 吃掉SQL异常，抛Sequence异常
			log.error(
					"由于SQLException,更新初值自适应失败！sequence Name：" + name
							+ "更新过程：" + value + "-->" + newValue, e);
			throw new SequenceException(
					"由于SQLException,更新初值自适应失败！sequence Name：" + name
					+ "更新过程：" + value + "-->" + newValue, e);
		} finally {
			closeStatement(stmt);
			stmt = null;
			closeConnection(conn);
			conn = null;
		}
	}

	/**
	 * 插入新值
	 * 
	 * @param index
	 * @param name
	 * @return
	 * @throws SequenceException
	 * @throws SQLException
	 */
	private void adjustInsert(String name) throws SequenceException,
			SQLException {
		long newValue = startNum;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(getInsertSql());
			stmt.setString(1, name);
			stmt.setLong(2, newValue);
			stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SequenceException(
						"faild to auto adjust init value at  " + name
								+ " update affectedRow =0");
			}
			log.info("name:" + name + "插入初值:"+ name + "value:" + newValue);

		} catch (SQLException e) { // 吃掉SQL异常，抛sequence异常
			log.error(
					"由于SQLException,插入初值自适应失败！sequence Name：" + name
							+ "   value:" + newValue, e);
			throw new SequenceException(
					"由于SQLException,插入初值自适应失败！sequence Name：" + name
							+ "   value:" + newValue, e);
		} finally {
			closeResultSet(rs);
			rs = null;
			closeStatement(stmt);
			stmt = null;
			closeConnection(conn);
			conn = null;
		}
	}

	public SequenceRange nextRange(String name) throws SequenceException {
		if (name == null) {
			log.error("序列名为空！");
			throw new IllegalArgumentException("序列名称不能为空");
		}

		long oldValue;
		long newValue;

		boolean readSuccess;
		boolean writeSuccess;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		for(int i=0;i<retryTimes;i++)
		{
			// 查询
			try {
				conn = dataSource.getConnection();
				stmt = conn.prepareStatement(getSelectSql());
				stmt.setString(1, name);
				rs = stmt.executeQuery();
				rs.next();
				oldValue = rs.getLong(1);
				if (oldValue < 0) {
					StringBuilder message = new StringBuilder();
					message.append(
							"Sequence value cannot be less than zero, value = ")
							.append(oldValue);
					message.append(", please check table ").append(
							getTableName());
					log.info(message);

					continue;
				}
				if (oldValue > Long.MAX_VALUE - DELTA) {
					StringBuilder message = new StringBuilder();
					message.append("Sequence value overflow, value = ").append(
							oldValue);
					message.append(", please check table ").append(
							getTableName());
					log.info(message);
					continue;
				}
				newValue = oldValue + innerStep;
			} catch (SQLException e) {
				log .error("取范围过程中--查询出错！name:" + name,e);
				continue;
			} finally {
				closeResultSet(rs);
				rs = null;
				closeStatement(stmt);
				stmt = null;
				closeConnection(conn);
				conn = null;
			}
			readSuccess = true;

			try {
				conn = dataSource.getConnection();
				stmt = conn.prepareStatement(getUpdateSql());
				stmt.setLong(1, newValue);
				stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				stmt.setString(3, name);
				stmt.setLong(4, oldValue);
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 0) {
					continue;
				}

			} catch (SQLException e) {
				log .error("取范围过程中--更新出错！name:" + name,e);
				continue;
			} finally {
				closeStatement(stmt);
				stmt = null;
				closeConnection(conn);
				conn = null;
			}
			writeSuccess = true;
			if (readSuccess && writeSuccess)
				return new SequenceRange(oldValue + 1, oldValue
						+ innerStep);
		}
		log.error("说有数据源都不可用！且重试"+this.retryTimes+"次后，仍然失败!");
		throw new SequenceException("dataSource faild to get value!");
	}

	private String getInsertSql() {
		if (insertSql == null) {
			synchronized (this) {
				if (insertSql == null) {
					StringBuilder buffer = new StringBuilder();
					buffer.append("insert into ").append(getTableName())
							.append("(");
					buffer.append(getNameColumnName()).append(",");
					buffer.append(getValueColumnName()).append(",");
					buffer.append(getGmtModifiedColumnName()).append(
							") values(?,?,?);");
					insertSql = buffer.toString();
				}
			}
		}
		return insertSql;
	}

	private String getSelectSql() {
		if (selectSql == null) {
			synchronized (this) {
				if (selectSql == null) {
					StringBuilder buffer = new StringBuilder();
					buffer.append("select ").append(getValueColumnName());
					buffer.append(" from ").append(getTableName());
					buffer.append(" where ").append(getNameColumnName())
							.append(" = ?");

					selectSql = buffer.toString();
				}
			}
		}

		return selectSql;
	}

	private String getUpdateSql() {
		if (updateSql == null) {
			synchronized (this) {
				if (updateSql == null) {
					StringBuilder buffer = new StringBuilder();
					buffer.append("update ").append(getTableName());
					buffer.append(" set ").append(getValueColumnName())
							.append(" = ?, ");
					buffer.append(getGmtModifiedColumnName()).append(
							" = ? where ");
					buffer.append(getNameColumnName()).append(" = ? and ");
					buffer.append(getValueColumnName()).append(" = ?");

					updateSql = buffer.toString();
				}
			}
		}

		return updateSql;
	}

	private static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.debug("Could not close JDBC ResultSet", e);
			} catch (Throwable e) {
				log.debug("Unexpected exception on closing JDBC ResultSet", e);
			}
		}
	}

	private static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.debug("Could not close JDBC Statement", e);
			} catch (Throwable e) {
				log.debug("Unexpected exception on closing JDBC Statement", e);
			}
		}
	}

	private static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug("Could not close JDBC Connection", e);
			} catch (Throwable e) {
				log.debug("Unexpected exception on closing JDBC Connection", e);
			}
		}
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getInnerStep() {
		return innerStep;
	}

	public void setInnerStep(int innerStep) {
		this.innerStep = innerStep;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getNameColumnName() {
		return nameColumnName;
	}

	public void setNameColumnName(String nameColumnName) {
		this.nameColumnName = nameColumnName;
	}

	public String getValueColumnName() {
		return valueColumnName;
	}

	public void setValueColumnName(String valueColumnName) {
		this.valueColumnName = valueColumnName;
	}

	public String getGmtModifiedColumnName() {
		return gmtModifiedColumnName;
	}

	public void setGmtModifiedColumnName(String gmtModifiedColumnName) {
		this.gmtModifiedColumnName = gmtModifiedColumnName;
	}

	public boolean isAdjust() {
		return adjust;
	}

	public void setAdjust(boolean adjust) {
		this.adjust = adjust;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}
