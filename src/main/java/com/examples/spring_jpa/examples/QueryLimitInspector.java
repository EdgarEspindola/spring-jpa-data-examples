package com.examples.spring_jpa.examples;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class QueryLimitInspector implements StatementInspector {

	@Override
	public String inspect(String sql) {
		// Limit the number of rows returned by the query
        String sqlInLowerCase = sql.toLowerCase();
        if (sqlInLowerCase.startsWith("select") && !sqlInLowerCase.contains("limit") && !sqlInLowerCase.contains("fetch")) {
            // Add a LIMIT clause to the SQL query
		    return sql + " LIMIT 100";
        }

        return sql;
	}

}
