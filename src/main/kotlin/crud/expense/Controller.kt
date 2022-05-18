package crud.expense

import crud.expense.models.Category
import crud.expense.models.Person
import crud.expense.services.ExpenseAccountingService
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
    @ResponseStatus(HttpStatus.FOUND)
    fun getPerson(@PathVariable id: Long) = expenseAccountingService.getByIdPerson(id)

    @GetMapping("/person")
    fun getAllPerson() = expenseAccountingService.getAllPerson()

    @DeleteMapping("/person/{id}")
    fun deletePerson(@PathVariable id: Long) = expenseAccountingService.deleteByIdPerson(id)

    @PutMapping("/person/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editPerson(@RequestBody updatePerson: Person, @PathVariable id: Long) =
        expenseAccountingService.updatePerson(updatePerson, id)

    @PostMapping("/category", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@RequestBody category: Category) = expenseAccountingService.createCategory(category)

    @GetMapping("/category/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getCategory(@PathVariable name: String) = expenseAccountingService.getByNameCategory(name)

    @GetMapping("/category")
    fun getAllCategory() = expenseAccountingService.getAllCategory()

    @DeleteMapping("/category/{id}")
    fun deleteCategory(@PathVariable id: Long) = expenseAccountingService.deleteByNameCategory(id)

    @PutMapping("/category/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editCategory(@RequestBody updateCategory: Category, @PathVariable id: Long) =
        expenseAccountingService.updateCategory(updateCategory, id)
}


