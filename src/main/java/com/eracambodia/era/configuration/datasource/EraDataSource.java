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
                .url("jdbc:postgresql://ec2-54-221-210-97.compute-1.amazonaws.com:5432/dfotv5hpoiu33h")
                .username("mzvvacdhmvtzhb")
                .password("c7980b63fb09912f6e4ac0bd0aeed0069ba6eb41e706b0bb23340e60c73d98e8")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
