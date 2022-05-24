package crud.expense.controllers

import crud.expense.models.Expense
import crud.expense.services.ExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/expense", produces = [MediaType.APPLICATION_JSON_VALUE])
internal class ExpenseController(
    private val expenseService: ExpenseService
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody expense: Expense) = expenseService.create(expense)

    @GetMapping("/category-and-person")
    @ResponseStatus(HttpStatus.FOUND)
    fun getByCategoryNameAndPersonId(
        @RequestParam categoryName: String,
        @RequestParam pageNum: Int = 1,
        @RequestParam pageSize: Int = 5,
        @RequestParam personId: Long
    ) =
        expenseService.getByCategoryNameAndPersonId(categoryName, pageNum, pageSize, personId)

    @DeleteMapping("/category-and-person")
    fun deleteByCategoryNameAndPersonId(@RequestParam categoryName: String, @RequestParam personId: Long) =
        expenseService.deleteByCategoryNameAndPersonId(categoryName, personId)

    @GetMapping("/cost-less-and-person")
    @ResponseStatus(HttpStatus.FOUND)
    fun getByCostLessThanAndPersonId(
        @RequestParam cost: Int,
        @RequestParam pageNum: Int = 1,
        @RequestParam pageSize: Int = 5,
        @RequestParam personId: Long
    ) = expenseService.getByCostLessThanAndPersonId(cost, pageNum, pageSize, personId)

    @GetMapping("/cost-greater-and-person")
    @ResponseStatus(HttpStatus.FOUND)
    fun getByCostGreaterThanAndPersonId(
        @RequestParam cost: Int,
        @RequestParam pageNum: Int = 1,
        @RequestParam pageSize: Int = 5,
        @RequestParam personId: Long
    ) = expenseService.getByCostGreaterThanAndPersonId(cost, pageNum, pageSize, personId)

    @GetMapping("/date-creation-between-and-person")
    @ResponseStatus(HttpStatus.FOUND)
    fun getByDateBetweenAndPersonId(
        @RequestParam startDate: String,
        @RequestParam endDate: String,
        @RequestParam pageNum: Int = 1,
        @RequestParam pageSize: Int = 5,
        @RequestParam personId: Long
    ) = expenseService.getByDateCreationBetweenAndPersonId(
        LocalDate.parse(startDate),
        LocalDate.parse(endDate),
        pageNum,
        pageSize,
        personId
    )
}


