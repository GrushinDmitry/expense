package crud.expense.repositories

import crud.expense.models.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface Persons : ReactiveCrudRepository<Person, Long>