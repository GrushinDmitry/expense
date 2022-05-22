package crud.expense.controllers

import crud.expense.models.Expense
import crud.expense.services.ExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expense-accounting/expense", produces = [MediaType.APPLICATION_JSON_VALUE])
internal class ExpenseController(
    private val expenseService: ExpenseService
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody expense: Expense) = expenseService.create(expense)

    @GetMapping("/category-and-person")
    @ResponseStatus(HttpStatus.FOUND)
    fun getByCategoryName(@RequestParam name: String, @RequestParam id: Long) =
        expenseService.getByCategoryNameAndPersonId(name, id)

    @DeleteMapping("/category-and-person")
    fun deleteByCategoryName(@RequestParam name: String, @RequestParam id: Long) =
        expenseService.deleteByCategoryNameAndPersonId(name, id)

}


