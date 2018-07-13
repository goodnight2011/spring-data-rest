package ru.vcki.data.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.hibernate.SessionFactory;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import ru.vcki.data.rest.EmbPg;
import ru.vcki.data.rest.FlyWayMigrator;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan("ru.vcki.data.controllers")
@EnableTransactionManagement
@EnableJpaRepositories("ru.vcki.data.repoes")
public class Config {

    @Bean
    public RepositoryRestConfigurerAdapter repositoryRestConfigurer(){
        return new RepositoryRestConfigurerAdapter(){
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setBasePath("/api");
            }
        };
    }

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
        tm.setJpaDialect(new HibernateJpaDialect());
        return tm;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        val vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        val factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);

        val jpaDialect = new HibernateJpaDialect();
        factory.setJpaDialect(jpaDialect);
        factory.setPackagesToScan("ru.vcki.data.pojo");
        factory.setDataSource(dataSource);
        return factory;
    }



}
