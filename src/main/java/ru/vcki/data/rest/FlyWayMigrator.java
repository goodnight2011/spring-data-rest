package ru.vcki.data.rest;

import org.flywaydb.core.Flyway;
import org.springframework.data.domain.ExampleMatcher;

import javax.sql.DataSource;

public class FlyWayMigrator {

    public void migrate(String url, String user, String pass){
        Flyway flyway = new Flyway();
        flyway.setLocations("classpath:/db/migrations");
        flyway.setDataSource(url, user, pass);
        flyway.migrate();
    }
}
