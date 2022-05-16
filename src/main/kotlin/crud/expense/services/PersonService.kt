package crud.expense.services

import crud.expense.models.Person
import crud.expense.repositories.Persons
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PersonService(private val persons: Persons) {

    fun create(person: Person): Mono<Person> = persons.save(person)

    fun deleteById(id: Long) = persons.deleteById(id)

    fun findAll(): Flux<Person> = persons.findAll()

    fun findById(id: Long): Mono<Person> = persons.findById(id)

    fun findByNames(firstname: String, lastname: String): Mono<Person> =
        persons.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname)
}