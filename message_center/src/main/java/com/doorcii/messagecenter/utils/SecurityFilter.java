package com.doorcii.messagecenter.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doorcii.messagecenter.beans.Category;

public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String servletPath = httpRequest.getServletPath();
		if(servletPath.contains("/web/")) {
			Category category = (Category)httpRequest.getSession().getAttribute("_user_");
			HttpServletResponse resp=(HttpServletResponse)response; 
			if(null == category) {
				resp.sendRedirect("/login");
				return;
			}
		}
		chain.doFilter(httpRequest, response);
	}

	@Override
	public void destroy() {
	}
	
}
