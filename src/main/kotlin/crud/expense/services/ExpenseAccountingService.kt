package crud.expense.services

import crud.expense.configuration.ErrorCode
import crud.expense.configuration.MemberServiceAlreadyExistsException
import crud.expense.configuration.MemberServiceNotFoundException
import crud.expense.models.Category
import crud.expense.models.Expense
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
        categoryService.findByName(category.name)
            .flatMap<Category> { Mono.error(MemberServiceAlreadyExistsException(ErrorCode.CATEGORY_ALREADY, it.name)) }
            .switchIfEmpty(categoryService.create(category))

    fun deleteByNameCategory(name: String) =
        findByNameCategoryWithValidate(name).flatMap { categoryService.deleteByName(it.name) }

    fun getAllCategory(): Flux<Category> =
        categoryService.findAll().switchIfEmpty(Flux.error(MemberServiceNotFoundException(ErrorCode.NO_CATEGORIES)))

    fun getByNameCategory(name: String): Mono<Category> = findByNameCategoryWithValidate(name)

    fun updateCategory(updatedCategory: Category, id: Long): Mono<Category> {
        findByIdCategoryWithValidate(id)
        return categoryService.update(updatedCategory, id)
    }

    fun findByNameCategoryWithValidate(name: String): Mono<Category> = categoryService.findByName(name)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_CATEGORY_BY_NAME, name)))

    fun findByIdCategoryWithValidate(id: Long): Mono<Category> = categoryService.findById(id)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_CATEGORY_BY_ID, id.toString())))


    fun createPerson(person: Person): Mono<Person> = personService.findByNames(person.firstname, person.lastname)
        .flatMap<Person> {
            Mono.error(
                MemberServiceAlreadyExistsException(
                    ErrorCode.PERSON_ALREADY, listOf(it.firstname, it.lastname).joinToString(" ")
                )
            )
        }.switchIfEmpty(personService.create(person))

    fun deleteByIdPerson(id: Long) = findByIdPersonWithValidate(id).flatMap { personService.deleteById(id) }

    fun getAllPerson(): Flux<Person> =
        personService.findAll().switchIfEmpty(Flux.error(MemberServiceNotFoundException(ErrorCode.NO_PERSONS)))

    fun getByIdPerson(id: Long): Mono<Person> = findByIdPersonWithValidate(id)

    fun updatePerson(updatedPerson: Person, id: Long): Mono<Person> {
        findByIdPersonWithValidate(id)
        return personService.update(updatedPerson, id)
    }

    fun findByIdPersonWithValidate(id: Long): Mono<Person> = personService.findById(id)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_PERSON_BY_ID, id.toString())))


    fun findByCategoryNameExpense(categoryName: String): Flux<Expense> = expenseService.findByCategoryName(categoryName)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_EXPENSE_BY_CATEGORY_NAME, categoryName)))
}