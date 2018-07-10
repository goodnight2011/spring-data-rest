import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;
import ru.vcki.data.rest.EmbPg;
import ru.vcki.data.rest.FlyWayMigrator;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertEquals;


public class AllTests {

    @Test
    @SneakyThrows
    public void testEmbeddedDb() {
       @Cleanup EmbPg embPg = new EmbPg();
       @Cleanup Connection conn = DriverManager.getConnection(embPg.getUrl(), embPg.getUser(), embPg.getPassword());
       @Cleanup val stmt = conn.createStatement();
       @Cleanup val rs = stmt.executeQuery("select 5+5");
       rs.next();
       assertEquals(10, rs.getInt(1));
    }

    @SneakyThrows
    @Test
    public void testFlywayWorks(){
        @Cleanup val embPg = new EmbPg();
        new FlyWayMigrator().migrate(embPg.getCleanUrl(), embPg.getUser(), embPg.getPassword());
        @Cleanup val conn = DriverManager.getConnection(embPg.getUrl(), embPg.getUser(), embPg.getPassword());
        @Cleanup val stmt = conn.createStatement();
        @Cleanup val rs = stmt.executeQuery("select count(*) from person");
        rs.next();
        assertEquals(3, rs.getInt(1));
    }
}
