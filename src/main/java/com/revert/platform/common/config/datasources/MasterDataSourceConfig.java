package com.revert.platform.common.config.datasources;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.github.pagehelper.PageHelper;
import com.revert.platform.common.annotation.MasterMySqlDao;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.revert.**.mapper", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    @Value("${platform.dataSource.type}")
    private String datasourceType;

    @Value("${platform.druid.stat.loginUsername}")
    private String loginUsername;

    @Value("${platform.druid.stat.loginPassword}")
    private String loginPassword;

    /**
     * 数据源
     * @return
     */
    @Primary
    @Bean(name="masterDataSource",initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    /**
     * 根据数据源创建对应的jdbcTemplate
     */
    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    /**
     * mybatis SQLsessionManage
     */

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(
                resolver.getResources("classpath*:com/revert/"+datasourceType+"/**/mapper/impI/*Mapper.xml")
        );
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pagePlugin()});
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * druid 连接池 界面优化版
     * @return
     */
    @Bean
    @Primary
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", loginUsername);
        reg.addInitParameter("loginPassword", loginPassword);
        reg.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return reg;
    }

    /**
     * mybatis 分页插件
     */
    public PageHelper pagePlugin(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", datasourceType);
        properties.setProperty("reasonable", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }



}
