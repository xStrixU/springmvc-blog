package me.xstrixu.springmvcblog.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@RequiredArgsConstructor
public class DatabaseConfig {

    private final Environment env;

    @Bean
    public DataSource dataSource() {
        var dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(env.getRequiredProperty("datasource.driverClassName"));
            dataSource.setJdbcUrl(env.getRequiredProperty("datasource.url"));
            dataSource.setUser(env.getRequiredProperty("datasource.username"));
            dataSource.setPassword(env.getRequiredProperty("datasource.password"));

            dataSource.setInitialPoolSize(env.getRequiredProperty("c3p0.initialPoolSize", Integer.class));
            dataSource.setMinPoolSize(env.getRequiredProperty("c3p0.minPoolSize", Integer.class));
            dataSource.setMaxPoolSize(env.getRequiredProperty("c3p0.maxPoolSize", Integer.class));
            dataSource.setMaxIdleTime(env.getRequiredProperty("c3p0.maxIdleTime", Integer.class));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException(ex);
        }

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        var properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.connection.useUnicode", true);
        properties.put("hibernate.connection.characterEncoding", "UTF-8");

        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("me.xstrixu.springmvcblog.model.entity");
        sessionFactory.setHibernateProperties(properties);

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }
}