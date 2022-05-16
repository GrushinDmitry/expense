package crud.expense.repositories

import crud.expense.models.Expense
import crud.expense.models.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface Persons : ReactiveCrudRepository<Person, Long> {

    fun findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname: String, lastname: String): Mono<Person>
}