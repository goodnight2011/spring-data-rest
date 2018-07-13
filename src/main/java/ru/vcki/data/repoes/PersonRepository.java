package ru.vcki.data.repoes;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.vcki.data.pojo.Person;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    Person findByFirstName(@Param("name") String firstName);
}
