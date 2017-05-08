package com.gaston.tpdlc2017.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;


/**
 * Created by ppioli on 07/05/17.
 */
@Configuration
public class DataBaseConfig {
    public static final String MYSQL_DB_DRIVER_CLASS= "com.mysql.jdbc.Driver";
    public static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/db_tpdlc2017";
    public static final String MYSQL_DB_USERNAME = "tpdlc";
    public static final String MYSQL_DB_PASSWORD = "dlc2017";
   // public static final String MYSQL_DB_USERNAME = "root";
   // public static final String MYSQL_DB_PASSWORD = "root";

    @Bean
    public DataSource dataSource() {

        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(MYSQL_DB_URL);
        mysqlDS.setUser(MYSQL_DB_USERNAME);
        mysqlDS.setPassword(MYSQL_DB_PASSWORD);
        return mysqlDS;
    }

}