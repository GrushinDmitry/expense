package crud.expense.repositories

import crud.expense.models.Expense
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface Expenses : ReactiveCrudRepository<Expense, Long> {

    fun findByCategoryNameIgnoreCase(name: String): Flux<Expense>

    fun findByPersonId(id: Long): Flux<Expense>

    fun existsByPersonId(id: Long): Mono<Boolean>

    fun deleteByCategoryNameIgnoreCase(categoryName: String): Mono<Void>

    fun findByCostLessThan(cost: Int, pageable: Pageable): Flux<Expense>

    fun findByCostGreaterThan(cost: Int, pageable: Pageable): Flux<Expense>

    fun findByDataBetween(starDate: LocalDate, endDate: LocalDate, pageable: Pageable): Flux<Expense>
}