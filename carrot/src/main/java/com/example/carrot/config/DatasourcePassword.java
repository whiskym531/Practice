package com.example.carrot.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Author: warm
 * Date: 2021/7/26
 * Description:
 */
@Configuration
public class DatasourcePassword {

    @Autowired
    private HikariDataSource dataSource;
    @Bean
    public void dataSourcePassword() {
        dataSource.setPassword("root");//设置数据库密码
    }
}
