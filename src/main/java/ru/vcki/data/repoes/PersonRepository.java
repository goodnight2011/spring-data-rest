package ru.vcki.data.repoes;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.vcki.data.pojo.Person;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    Person findByFirstName(String firstName);
}
