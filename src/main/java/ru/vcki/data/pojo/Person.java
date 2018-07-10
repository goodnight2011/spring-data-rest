package ru.vcki.data.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @Column(name="id")
    @SequenceGenerator(name = "person_gen", sequenceName = "person_pk_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "person_gen")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
