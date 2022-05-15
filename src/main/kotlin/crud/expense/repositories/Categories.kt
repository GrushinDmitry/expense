package crud.expense.repositories

import crud.expense.models.Category
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface Categories : ReactiveCrudRepository<Category, Long> {

    fun findByNameIgnoreCase(name: String): Mono<Category>

    fun deleteByNameIgnoreCase(name: String): Mono<Void>
}