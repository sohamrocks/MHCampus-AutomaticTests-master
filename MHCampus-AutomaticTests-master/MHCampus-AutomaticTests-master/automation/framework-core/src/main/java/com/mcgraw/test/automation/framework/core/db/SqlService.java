package com.mcgraw.test.automation.framework.core.db;

import java.io.InputStream;

import javax.sql.DataSource;

import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.mcgraw.test.automation.framework.core.common.ResourceUtils;

public class SqlService extends JdbcDaoSupport {
	
	private int queryTimeout;

	public void executeResourceSqlScript(String resourceFileName) {
		InputStream sql = ResourceUtils.getResourceAsInputStream(resourceFileName);
		executeResourceSqlScript(sql);
	}
	
	public void executeResourceSqlScript(InputStream queryStream) {
		JdbcTestUtils.executeSqlScript(getJdbcTemplate(),
			new InputStreamResource(queryStream), false);
	}
	
	@Override
	protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
		JdbcTemplate result=new JdbcTemplate(dataSource);
		result.setQueryTimeout(queryTimeout);
		return result;
	}

	public int getQueryTimeout() {
		return queryTimeout;
	}

	public void setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
	}
}
