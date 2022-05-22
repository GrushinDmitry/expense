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

    fun create(expense: Expense): Mono<Expense> =
        personService.getById(expense.personId).flatMap { categoryService.getByName(expense.categoryName) }
            .flatMap { expenses.save(expense.copy(id = null, dateCreation = null)) }

    fun deleteByCategoryNameAndPersonId(categoryName: String, personId: Long) =
        findFirstByPersonIdWithValidate(personId).flatMap {
            findFirstByCategoryNameWithValidate(categoryName).flatMap {
                expenses.deleteByCategoryNameIgnoreCase(
                    categoryName
                )
            }
        }

    fun getByCategoryNameAndPersonId(categoryName: String, pageNum: Int, pageSize: Int, personId: Long): Flux<Expense> {
        validatePageParams(pageNum, pageSize)
        return findFirstByPersonIdWithValidate(personId).flatMap {
            expenses.findByCategoryNameIgnoreCaseAndPersonId(
                categoryName, PageRequest.of((pageNum - 1), pageSize, Sort.by("categoryName")), personId
            ).switchIfEmpty(
                Mono.error(
                    MemberServiceNotFoundException(
                        ErrorCode.NO_EXPENSE_BY_CATEGORY_NAME, categoryName
                    )
                )
            )
        }
    }

    fun getByCostLessThanAndPersonId(cost: Int, pageNum: Int, pageSize: Int, personId: Long): Flux<Expense> {
        validatePageParams(pageNum, pageSize)
        validateCost(cost)
        return findFirstByPersonIdWithValidate(personId).flatMap {
            expenses.findByCostLessThanAndPersonId(
                cost, PageRequest.of((pageNum - 1), pageSize, Sort.by("cost")), personId
            ).switchIfEmpty(
                Flux.error(
                    MemberServiceNotFoundException(
                        ErrorCode.NO_EXPENSE_WITH_COST_LESS, cost.toString()
                    )
                )
            )
        }
    }

    fun getByCostGreaterThanAndPersonId(cost: Int, pageNum: Int, pageSize: Int, personId: Long): Flux<Expense> {
        validatePageParams(pageNum, pageSize)
        validateCost(cost)
        return findFirstByPersonIdWithValidate(personId).flatMap {
            expenses.findByCostGreaterThanAndPersonId(
                cost, PageRequest.of((pageNum - 1), pageSize, Sort.by("cost")), personId
            ).switchIfEmpty(
                Flux.error(
                    MemberServiceNotFoundException(
                        ErrorCode.NO_EXPENSE_WITH_COST_GREATER, cost.toString()
                    )
                )
            )
        }
    }

    fun getByDateBetweenAndPersonId(
        startDate: LocalDate,
   /*     endDate: LocalDate,*/
  /*      pageNum: Int,
        pageSize: Int,
        personId: Long*/
    ): Flux<Expense> {
        //validatePageParams(pageNum, pageSize)
        //validateData(startDate, endDate)
        return findFirstByPersonIdWithValidate(1/*personId*/).flatMap {
            expenses.findByDateCreation(
                startDate, /*endDate,*/ /*PageRequest.of((pageNum - 1), pageSize, Sort.by("dateCreation")), personId*/
            ).switchIfEmpty(
                Flux.error(
                    MemberServiceNotFoundException(
                        ErrorCode.NO_EXPENSE_BETWEEN_START_DATE_AND_END_DATE, startDate.toString()
                    )
                )
            )
        }
    }

    private fun findFirstByPersonIdWithValidate(id: Long): Flux<Expense> =
        expenses.findFirstByPersonId(id).switchIfEmpty(
            Flux.error(
                MemberServiceNotFoundException(
                    ErrorCode.NO_EXPENSE_BY_PERSON_ID, id.toString()
                )
            )
        )

    private fun findFirstByCategoryNameWithValidate(categoryName: String): Flux<Expense> =
        expenses.findFirstByCategoryName(categoryName).switchIfEmpty(
            Flux.error(
                MemberServiceNotFoundException(
                    ErrorCode.NO_EXPENSE_BY_CATEGORY_NAME, categoryName
                )
            )
        )

    private fun validatePageParams(pageNum: Int, pageSize: Int) {
        require(pageNum > 0 && pageSize > 0) { "The page params pageNum=$pageNum and pageSize=$pageSize must be positive" }
    }

    private fun validateCost(cost: Int) {
        require(cost > 0) { "The cost=$cost must be positive" }
    }

    private fun validateData(startDate: LocalDate, endData: LocalDate) {
        require(endData >= startDate) { "The endData=$endData must be after startData=$startDate" }
    }

}

