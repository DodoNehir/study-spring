package com.example.jdbcpractice.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:jdbc.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:jdbc-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class DataSourceConfig {
    //설정 파일을 하나의 bean 으로 만들기
    // ConfigurationProperties 덕분에 jdbc-local.properties에서 정의한 값들이 주입된다.
    @Bean
    @ConfigurationProperties("datasource.jdbc")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }
}
