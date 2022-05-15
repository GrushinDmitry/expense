package crud.expense

import crud.expense.models.Category
import crud.expense.models.Person
import crud.expense.services.CategoryService
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
    private val expenseService: ExpenseService,
    private val personService: PersonService,
    private val categoryService: CategoryService
) {
  //  val actualCategories = categoryService.findAll().toIterable().associate { it.id to it.name }.toMutableMap()
/*    val actualPersons =
        personService.findAll().toIterable().associate { it.id to PersonNames(it.firstname, it.lastname) }
            .toMutableMap()

    data class PersonNames(
        val firstname: String, val lastname: String
    )*/

    @PostMapping("/person",consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPerson(@RequestBody person: Person) = personService.create(person)

/*    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editItem(@RequestBody itemRequest: ItemRequest, @PathVariable id: Long) =
        itemService.update(id, itemRequest.toDomain())*/

    @GetMapping("/person/{id}")
    fun findPerson(@PathVariable id: Long) = personService.findById(id)

    @GetMapping("/person")
    fun findPerson() = personService.findAll()

    @DeleteMapping("/person/{id}")
    fun deletePerson(@PathVariable id: Long) = personService.deleteById(id)
}

