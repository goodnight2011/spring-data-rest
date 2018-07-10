package ru.vcki.data.rest;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Paths;

public class EmbPg {
    private EmbeddedPostgres inst ;

    private final static int port = 15432;

    @SneakyThrows
    public EmbPg() {
       inst = EmbeddedPostgres.builder().
               setPort(port).
               setCleanDataDirectory(false).
               setDataDirectory(Paths.get("/home", "avb", "embedded-pg")).
               start();
    }

    public String getUrl(){
        return "jdbc:p6spy:postgresql://localhost:{port}/".replace("{port}", "" + port);
    }

    public String getCleanUrl(){
        return "jdbc:postgresql://localhost:{port}/".replace("{port}", "" + port);
    }

    public String getUser(){
        return "postgres";
    }

    public String getPassword(){
        return "postgres";
    }

    @SneakyThrows
    public void close() {
        inst.close();
    }
}
