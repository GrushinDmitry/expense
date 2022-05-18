package crud.expense.repositories

import crud.expense.models.Category
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface Categories : ReactiveCrudRepository<Category, Long> {

    fun findByNameIgnoreCase(name: String): Mono<Category>

    fun existsByNameIgnoreCase(name: String): Mono<Boolean>

    fun deleteByNameIgnoreCase(name: String)
}