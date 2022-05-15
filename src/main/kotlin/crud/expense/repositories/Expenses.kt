package crud.expense.repositories

import crud.expense.models.Expense
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface Expenses : ReactiveCrudRepository<Expense, Long> {

    fun findByCategoryNameIgnoreCase(name: String): Flux<Expense>

    fun findByUserId(id: Long): Flux<Expense>

    fun deleteByUserId(id: Long): Mono<Void>

    fun deleteByCategoryNameIgnoreCase(categoryName: String): Mono<Void>

    fun findByCostLessThan(cost: Int, pageable: Pageable): Flux<Expense>

    fun findByCostGreaterThan(cost: Int, pageable: Pageable): Flux<Expense>

    fun findByDataBetween(starDate: LocalDate, endDate: LocalDate, pageable: Pageable): Flux<Expense>
}