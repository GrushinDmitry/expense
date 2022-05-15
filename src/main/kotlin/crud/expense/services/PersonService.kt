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

    fun findAll(): Flux<Person> = persons.findAll().switchIfEmpty(Flux.error(RuntimeException("No person found")))

    fun findById(id: Long): Mono<Person> =
        persons.findById(id).switchIfEmpty(Mono.error(RuntimeException("Person with id=$id not found")))
}