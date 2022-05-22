package crud.expense.controllers

import crud.expense.models.Person
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
@RequestMapping("/expense-accounting/person", produces = [MediaType.APPLICATION_JSON_VALUE])
internal class PersonController(
    private val personService: PersonService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody person: Person) = personService.create(person)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun get(@PathVariable id: Long) = personService.getById(id)

    @GetMapping()
    fun getAll() = personService.getAll()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = personService.deleteById(id)

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun edit(@RequestBody updatedPerson: Person, @PathVariable id: Long) =
        personService.update(updatedPerson, id)

}


