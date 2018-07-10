import lombok.Cleanup;
import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.vcki.data.pojo.Person;
import ru.vcki.data.repoes.PersonRepository;
import ru.vcki.data.spring.Config;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class SpringTests {

    @Autowired
    private EntityManager em;

    @Test
    public void testHibernate(){
       val person = em.find(Person.class, 1l);
       assertEquals("Alexandr", person.getFirstName());
       assertEquals("Busovikov", person.getLastName());
    }

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void personRepositoryTest() {
       personRepository.findAll().forEach(person -> System.out.println(person.getFirstName() + " were found"));
       val alexandr = personRepository.findByFirstName("Alexandr");
       assertEquals("Alexandr", alexandr.getFirstName());
       assertEquals("Busovikov", alexandr.getLastName());
       val nullPers = personRepository.findByFirstName("nullName");
       assertNull(nullPers);
    }
}
