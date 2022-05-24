package crud.expense.services

import crud.expense.configuration.ErrorCode
import crud.expense.configuration.MemberServiceAlreadyExistsException
import crud.expense.configuration.MemberServiceNotFoundException
import crud.expense.configuration.WarningCode
import crud.expense.models.Person
import crud.expense.repositories.Persons
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PersonService(private val persons: Persons) {

    fun create(person: Person): Mono<Person> = getByNames(person.firstname, person.lastname).flatMap<Person> {
            Mono.error(
                MemberServiceAlreadyExistsException(
                    WarningCode.PERSON_ALREADY, formMessageParameter(it.firstname, it.lastname)
                )
            )
        }.switchIfEmpty(persons.save(person.copy(id = null)))

    fun deleteById(id: Long) = getByIdWithValidate(id).flatMap { persons.deleteById(id) }

    fun getAll(): Flux<Person> =
        persons.findAll().switchIfEmpty(Flux.error(MemberServiceNotFoundException(ErrorCode.NO_PERSONS)))

    fun getById(id: Long): Mono<Person> = getByIdWithValidate(id)

    fun getByNames(firstname: String, lastname: String): Mono<Person> =
        persons.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname)

    fun update(updatedPerson: Person, id: Long): Mono<Person> =
        getByIdWithValidate(id).flatMap { persons.save(updatedPerson.copy(id = it.id)) }

    private fun getByIdWithValidate(id: Long): Mono<Person> = persons.findById(id)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_PERSON_BY_ID, id.toString())))

    private fun formMessageParameter(firstname: String, lastname: String): String =
        listOf(firstname, lastname).joinToString(" ")
}