package com.eracambodia.era.configuration.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EraDataSource {

    @Bean(name = "dataSource")
    public DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5432/era")
                .username("postgres")
                .password("123")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
