package com.topBalance.wishTree.config;



import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/config.properties")
public class DBConfig {


    @Autowired
    private ApplicationContext applicationContext; // 현재 프로젝트

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {

        return new HikariConfig();
    }


    @Bean
    public DataSource dataSource(HikariConfig config) {

        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception{

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();


        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setMapperLocations(
                applicationContext.getResources("classpath:/mappers/**.xml")  );

        sessionFactoryBean.setTypeAliasesPackage("com.topBalance.wishTree");

        sessionFactoryBean.setConfigLocation(
                applicationContext.getResource("classpath:mybatis-config.xml"));

        // 설정 내용이 모두 적용된 객체 반환
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }


    @Bean
    public DataSourceTransactionManager
    dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}