package com.jkpark.study.common.dbunit;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbUnitConfig {

	@Bean
	public DatabaseConfigBean dbUnitDatabaseConfig() {
		var config = new DatabaseConfigBean();
		//config.setAllowEmptyFields(true);
		config.setDatatypeFactory(new H2DataTypeFactory());
		return config;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource) {
		var dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDataSource(dataSource);
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitDatabaseConfig());
		return dbUnitDatabaseConnection;
	}
}
