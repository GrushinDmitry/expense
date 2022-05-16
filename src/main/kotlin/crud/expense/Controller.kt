package crud.expense

import crud.expense.models.Category
import crud.expense.models.Person
import crud.expense.services.CategoryService
import crud.expense.services.ExpenseAccountingService
import crud.expense.services.ExpenseService
import crud.expense.services.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/expense-accounting", produces = [MediaType.APPLICATION_JSON_VALUE])
internal class Controller(
    private val expenseAccountingService: ExpenseAccountingService
) {

    @PostMapping("/person", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPerson(@RequestBody person: Person) = expenseAccountingService.createPerson(person)

    @GetMapping("/person/{id}")
    fun getPerson(@PathVariable id: Long) = expenseAccountingService.getByIdPerson(id)

    @GetMapping("/person")
    fun getPerson() = expenseAccountingService.getAllPerson()

    @DeleteMapping("/person/{id}")
    fun deletePerson(@PathVariable id: Long) = expenseAccountingService.deleteByIdPerson(id)
}

