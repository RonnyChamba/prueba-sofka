package com.assessment.sofka.mscorepersona.repository;

import com.assessment.sofka.mscorepersona.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Integer> {
}
