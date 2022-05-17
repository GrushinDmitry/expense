package crud.expense.services

import crud.expense.models.Category
import crud.expense.repositories.Categories
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryService(private val categories: Categories) {

    fun create(category: Category): Mono<Category> = categories.save(category)

    fun deleteByName(name: String) = categories.deleteByNameIgnoreCase(name)

    fun findAll(): Flux<Category> = categories.findAll()

    fun findByName(name: String): Mono<Category> = categories.findByNameIgnoreCase(name)

    fun existByName(name: String): Mono<Boolean> = categories.existsByNameIgnoreCase(name)

    fun findById(id: Long): Mono<Category> = categories.findById(id)

    fun update(updatedCategory: Category, id: Long): Mono<Category> =
        categories.save(updatedCategory.copy(id = id))

}