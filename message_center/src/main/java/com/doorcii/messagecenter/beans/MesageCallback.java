package com.doorcii.messagecenter.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("root")
public class MesageCallback {
	
	@XStreamImplicit(itemFieldName="note")
	List<Note> notelist;
	
	public List<Note> getNotelist() {
		return notelist;
	}

	public void setNotelist(List<Note> notelist) {
		this.notelist = notelist;
	}

	public static class Note {
		private String note_code;
		private String rece_state;
		private String rece_time;
		public String getNote_code() {
			return note_code;
		}
		public void setNote_code(String note_code) {
			this.note_code = note_code;
		}
		public String getRece_state() {
			return rece_state;
		}
		public void setRece_state(String rece_state) {
			this.rece_state = rece_state;
		}
		public String getRece_time() {
			return rece_time;
		}
		public void setRece_time(String rece_time) {
			this.rece_time = rece_time;
		}
		@Override
		public String toString() {
			return "Note [note_code=" + note_code + ", rece_state="
					+ rece_state + ", rece_time=" + rece_time + "]";
		}
		
	}

	@Override
	public String toString() {
		return "MesageCallback [notelist=" + notelist + "]";
	}
	
}

