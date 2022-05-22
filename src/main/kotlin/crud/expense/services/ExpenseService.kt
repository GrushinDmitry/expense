package crud.expense.services

import crud.expense.configuration.ErrorCode
import crud.expense.configuration.MemberServiceNotFoundException
import crud.expense.models.Expense
import crud.expense.repositories.Expenses
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class ExpenseService(
    private val expenses: Expenses,
    private val personService: PersonService,
    private val categoryService: CategoryService
) {

    fun create(expense: Expense): Mono<Expense> = personService.getById(expense.personId)
        .flatMap { categoryService.getByName(expense.categoryName) }
        .flatMap { expenses.save(expense.copy(id = null, data = null)) }

/*    fun deleteByCategoryName(categoryName: String) =
        getByCategoryNameWithValidate(categoryName).flatMap { expenses.deleteByCategoryNameIgnoreCase(categoryName) }

    fun getByCategoryName(categoryName: String): Flux<Expense> = getByCategoryNameWithValidate(categoryName)*/

    private fun getByCategoryNameWithValidate(categoryName: String): Flux<Expense> =
        expenses.findByCategoryNameIgnoreCase(categoryName)
            .switchIfEmpty(
                Mono.error(
                    MemberServiceNotFoundException(
                        ErrorCode.NO_EXPENSE_BY_CATEGORY_NAME,
                        categoryName
                    )
                )
            )

/*    fun deleteByPersonId(id: Long) = getByPersonIdWithValidate(id).flatMap { expenses.deleteByPersonId(id) }

    fun getByPersonId(id: Long): Flux<Expense> = getByPersonIdWithValidate(id)*/

    private fun getByPersonIdWithValidate(id: Long): Flux<Expense> =
        expenses.findByPersonId(id)
            .switchIfEmpty(
                Mono.error(
                    MemberServiceNotFoundException(
                        ErrorCode.NO_EXPENSE_BY_PERSON_ID,
                        id.toString()
                    )
                )
            )

    fun deleteByCategoryNameAndPersonId(categoryName: String, personId: Long) =
        getByCategoryNameAndPersonId(categoryName, personId).flatMap { expenses.deleteByCategoryNameIgnoreCase(categoryName) }

    fun getByCategoryNameAndPersonId(categoryName: String, personId: Long): Flux<Expense> = getByCategoryNameAndPersonIdWithValidate(categoryName,personId)

    private fun getByCategoryNameAndPersonIdWithValidate(categoryName: String, personId: Long): Flux<Expense> =
       expenses.existsByPersonId(personId).flatMap { it. }

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

}

