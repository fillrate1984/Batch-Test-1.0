package com.datasource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceConfiguration extends DriverManagerDataSource {

    public DataSourceConfiguration() {
        setDriverClassName("com.mysql.jdbc.Driver");
        setUrl("jdbc:mysql://localhost:3306/batchtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        setUsername("root");
        setPassword("1111");
    }
}
