package crud.expense.services

import crud.expense.models.Expense
import crud.expense.repositories.Expenses
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class ExpenseService(private val expenses: Expenses) {

    fun create(expense: Expense): Mono<Expense> = expenses.save(expense)

    fun deleteByCategoryName(categoryName: String) = expenses.deleteByCategoryNameIgnoreCase(categoryName)

    fun deleteByUserId(id: Long) = expenses.deleteByUserId(id)

    fun findByUserId(id: Long): Flux<Expense> =
        expenses.findByUserId(id).switchIfEmpty(Flux.error(RuntimeException("Expense with userId=$id not found")))

    fun findByCategoryName(categoryName: String): Flux<Expense> = expenses.findByCategoryNameIgnoreCase(categoryName)
        .switchIfEmpty(Flux.error(RuntimeException("Expense with categoryName=$categoryName not found")))

    fun findByCostLessThan(cost: Int, pageNum: Int, pageSize: Int, direction: Sort.Direction): Flux<Expense> {
        validatePageParamsAndSortDirection(pageNum, pageSize, direction)
        validateCost(cost)
        return expenses.findByCostLessThan(cost, PageRequest.of((pageNum - 1), pageSize, Sort.by("cost")))
            .switchIfEmpty(Flux.error(RuntimeException("Expense with cost less than $cost not found")))
    }

    fun findByCostGreaterThan(cost: Int, pageNum: Int, pageSize: Int, direction: Sort.Direction): Flux<Expense> {
        validatePageParamsAndSortDirection(pageNum, pageSize, direction)
        validateCost(cost)
        return expenses.findByCostGreaterThan(
            cost, PageRequest.of((pageNum - 1), pageSize, Sort.by(direction, "cost"))
        ).switchIfEmpty(Flux.error(RuntimeException("Expense with cost more than $cost not found")))
    }

    fun findByDataBetween(
        startDate: LocalDate,
        endData: LocalDate,
        pageNum: Int,
        pageSize: Int,
        direction: Sort.Direction
    ): Flux<Expense> {
        validatePageParamsAndSortDirection(pageNum, pageSize, direction)
        validateData(startDate, endData)
        return expenses.findByDataBetween(
            startDate,
            endData,
            PageRequest.of((pageNum - 1), pageSize, Sort.by(direction, "data"))
        )
            .switchIfEmpty(Flux.error(RuntimeException("Expense during the time between $startDate and $endData not found")))
    }

    private fun validatePageParamsAndSortDirection(pageNum: Int, pageSize: Int, direction: Sort.Direction) {
        require(pageNum > 0 && pageSize > 0) { "The page params pageNum=$pageNum and pageSize=$pageSize must be positive" }
        require(direction in Sort.Direction.values()) { "The direction $direction not found" }
    }

    private fun validateCost(cost: Int) {
        require(cost > 0) { "The cost=$cost must be positive" }
    }

    private fun validateData(startDate: LocalDate, endData: LocalDate) {
        require(endData >= startDate) { "The endData=$endData must be after startData=$startDate" }
    }

/*    enum class Direction(val direction: Sort.Direction) {
        ASCENDING(Sort.Direction.ASC),
        DESCENDING(Sort.Direction.DESC)
    }*/

/*    private val direction = mapOf(
        "ascending" to Sort.Direction.ASC,
        "descending" to Sort.Direction.DESC
    )*/
}

