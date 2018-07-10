package ru.vcki.data.spring;

import lombok.val;
import org.hibernate.SessionFactory;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.vcki.data.rest.EmbPg;
import ru.vcki.data.rest.FlyWayMigrator;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories("ru.vcki.data.repoes")
@EnableTransactionManagement
@Configuration
public class Config {

    @Bean(destroyMethod = "close")
    public EmbPg embPg(){
        val embpg = new EmbPg();
        new FlyWayMigrator().migrate(embpg.getCleanUrl(), embpg.getUser(), embpg.getPassword());
        return embpg;
    }


    @Bean
    public DataSource dataSource(EmbPg embPg){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(Driver.class.getCanonicalName());
        ds.setUrl(embPg.getUrl());
        ds.setUsername(embPg.getUser());
        ds.setPassword(embPg.getPassword());
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        val tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }

/*    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds){
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(ds);
        sf.setPackagesToScan("ru.vcki.data.pojo");
        val props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        sf.setHibernateProperties(props);
        return sf;
    }*/

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.vcki.data.pojo");
        factory.setDataSource(dataSource);
        return factory;
    }

}
