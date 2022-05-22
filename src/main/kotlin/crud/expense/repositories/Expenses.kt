package crud.expense.repositories

import crud.expense.models.Expense
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface Expenses : ReactiveCrudRepository<Expense, Long> {

    fun findByCategoryNameIgnoreCaseAndPersonId(categoryName: String, pageable: Pageable, personId: Long): Flux<Expense>

    fun findFirstByPersonId(personId: Long): Flux<Expense>

    fun findFirstByCategoryName(categoryName: String): Flux<Expense>

    fun deleteByCategoryNameIgnoreCase(categoryName: String): Mono<Void>

    fun findByCostLessThanAndPersonId(cost: Int, pageable: Pageable, personId: Long): Flux<Expense>

    fun findByCostGreaterThanAndPersonId(cost: Int, pageable: Pageable, personId: Long): Flux<Expense>

    fun findByDateCreation(
        dateCreation: LocalDate,
      /*  endDate: LocalDate,*/
/*        pageable: Pageable,
        personId: Long*/
    ): Flux<Expense>
}