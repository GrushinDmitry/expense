package crud.expense.services

import crud.expense.configuration.ErrorCode
import crud.expense.configuration.ExpenseAccountingException
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

    fun createCategory(category: Category): Mono<Category> =
        categoryService.findByName(category.name).filter { it.name.isNotEmpty() }
            .switchIfEmpty(Mono.error(ExpenseAccountingException(ErrorCode.CATEGORY_ALREADY, category.name)))

/*        return categoryService.create(category)
    }*/

    //.switchIfEmpty(categoryService.create(category)) //subscribe { throw ExpenseAccountingException(ErrorCode.CATEGORY_ALREADY, category.name) }
    /* return categoryService.create(category)*/

    fun deleteByIdCategory(id: Long) =
        findByIdCategoryWithValidate(id).flatMap {
            categoryService.deleteById(it.id!!)
        }

    fun deleteByNameCategory(name: String) =
        findByNameCategoryWithValidate(name).flatMap {
            categoryService.deleteByName(it.name)
        }

    fun getAllCategory(): Flux<Category> =
        categoryService.findAll().switchIfEmpty(Flux.error(ExpenseAccountingException(ErrorCode.NO_CATEGORIES)))

    fun getByNameCategory(name: String): Mono<Category> = findByNameCategoryWithValidate(name)

    fun updateCategory(updatedCategory: Category, id: Long): Mono<Category> {
        findByIdCategoryWithValidate(id)
        return categoryService.update(updatedCategory, id)
    }

    fun findByNameCategoryWithValidate(name: String): Mono<Category> = categoryService.findByName(name)
        .switchIfEmpty(Mono.error(ExpenseAccountingException(ErrorCode.NO_CATEGORY_BY_NAME, name)))

    fun findByIdCategoryWithValidate(id: Long): Mono<Category> = categoryService.findById(id)
        .switchIfEmpty(Mono.error(ExpenseAccountingException(ErrorCode.NO_CATEGORY_BY_ID, id.toString())))

    fun createPerson(person: Person): Mono<Person> {
        if (personService.findByNames(person.firstname, person.lastname) != Mono.empty<Person>()) Mono.error<Person>(
            ExpenseAccountingException(
                ErrorCode.NO_PERSON_BY_FIRSTNAME_LASTNAME, listOf(person.firstname, person.lastname).joinToString(" ")
            )
        )
        return personService.create(person)
    }

    fun deleteByIdPerson(id: Long) {
        findByIdPersonWithValidate(id)
        personService.deleteById(id)
    }

    fun getAllPerson(): Flux<Person> =
        personService.findAll().switchIfEmpty(Flux.error(ExpenseAccountingException(ErrorCode.NO_PERSONS)))

    fun getByIdPerson(id: Long): Mono<Person> = findByIdPersonWithValidate(id)

    fun updatePerson(updatedPerson: Person, id: Long): Mono<Person> {
        findByIdPersonWithValidate(id)
        return personService.update(updatedPerson, id)
    }

    fun findByIdPersonWithValidate(id: Long): Mono<Person> = personService.findById(id)
        .switchIfEmpty(Mono.error(ExpenseAccountingException(ErrorCode.NO_PERSON_BY_ID, id.toString())))

}