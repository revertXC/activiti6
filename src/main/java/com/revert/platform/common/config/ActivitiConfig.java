package com.revert.platform.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * xiecong
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration{

    /**
     * 数据源
     * @return
     */
    @Bean(name="activitiDataSource",initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.activiti")
    protected DataSource activitiDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

        return baseSpringProcessEngineConfiguration(
                activitiDataSource(),
                transactionManager,
                springAsyncExecutor);
    }
}
