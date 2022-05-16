package crud.expense.services

import crud.expense.configuration.CategoryAlreadyExistsException
import crud.expense.configuration.CategoryNotExistsException
import crud.expense.configuration.PersonAlreadyExistsException
import crud.expense.configuration.PersonNotExistsException
import crud.expense.models.Category
import crud.expense.models.Person
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ExpenseAccountingService(
    private val expenseService: ExpenseService,
    private val personService: PersonService,
    private val categoryService: CategoryService
) {

    data class PersonNames(
        val firstname: String, val lastname: String
    )

    fun createCategory(category: Category): Mono<Category> {
        if (categoryService.findByName(category.name) != Mono.empty<Category>()) Mono.error<Category>(
            CategoryAlreadyExistsException("Category: ${category.name} already exists")
        )
        return categoryService.create(category)
    }

    fun deleteByNameCategory(name: String) {
        findByNameCategoryWithValidate(name)
        categoryService.deleteByName(name)
    }

    fun getAllCategory(): Flux<Category> =
        categoryService.findAll().switchIfEmpty(Flux.error(CategoryNotExistsException("Categories not exists")))

    fun getByNameCategory(name: String): Mono<Category> = findByNameCategoryWithValidate(name)

    fun findByNameCategoryWithValidate(name: String): Mono<Category> = categoryService.findByName(name)
        .switchIfEmpty(Mono.error(CategoryNotExistsException("Category: $name not exists")))

    fun createPerson(person: Person): Mono<Person> {
        if (personService.findByNames(person.firstname, person.lastname) != Mono.empty<Person>()) Mono.error<Person>(
            PersonAlreadyExistsException("Person with firstname: ${person.firstname} and lastname: ${person.lastname} already exists")
        )
        return personService.create(person)
    }

    fun deleteByIdPerson(id: Long) {
        findByIdPersonWithValidate(id)
        personService.deleteById(id)
    }

    fun getAllPerson(): Flux<Person> =
        personService.findAll().switchIfEmpty(Flux.error(PersonNotExistsException("Persons not exists")))

    fun getByIdPerson(id: Long): Mono<Person> = findByIdPersonWithValidate(id)

    fun findByIdPersonWithValidate(id: Long): Mono<Person> = personService.findById(id)
        .switchIfEmpty(Mono.error(PersonNotExistsException("Person with id=$id not exists")))
}