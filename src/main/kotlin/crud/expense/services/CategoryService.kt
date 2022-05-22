package crud.expense.services

import crud.expense.configuration.ErrorCode
import crud.expense.configuration.MemberServiceAlreadyExistsException
import crud.expense.configuration.MemberServiceNotFoundException
import crud.expense.configuration.WarningCode
import crud.expense.models.Category
import crud.expense.repositories.Categories
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryService(private val categories: Categories) {

    fun create(category: Category): Mono<Category> = categories.findByNameIgnoreCase(category.name)
        .flatMap<Category> { Mono.error(MemberServiceAlreadyExistsException(WarningCode.CATEGORY_ALREADY, it.name)) }
        .switchIfEmpty(categories.save(category.copy(id = null)))

    fun deleteByName(name: String): Mono<Int> = getByNameWithValidate(name).flatMap { categories.deleteByNameIgnoreCase(it.name) }

    fun getAll(): Flux<Category> = categories.findAll().switchIfEmpty(Flux.error(MemberServiceNotFoundException(ErrorCode.NO_CATEGORIES)))

    fun getByName(name: String): Mono<Category> = getByNameWithValidate(name)

    fun update(updatedCategory: Category, id: Long): Mono<Category> =
        getByIdWithValidate(id).flatMap { categories.save(updatedCategory.copy(id = it.id)) }



    private fun getByNameWithValidate(name: String): Mono<Category> = categories.findByNameIgnoreCase(name)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_CATEGORY_BY_NAME, name)))

    private fun getByIdWithValidate(id: Long): Mono<Category> = categories.findById(id)
        .switchIfEmpty(Mono.error(MemberServiceNotFoundException(ErrorCode.NO_CATEGORY_BY_ID, id.toString())))
}