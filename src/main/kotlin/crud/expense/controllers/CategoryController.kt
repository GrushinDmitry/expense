package crud.expense.controllers

import crud.expense.models.Category
import crud.expense.services.CategoryService
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
@RequestMapping("/category", produces = [MediaType.APPLICATION_JSON_VALUE])
internal class CategoryController(
    private val categoryService: CategoryService
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody category: Category) = categoryService.create(category)

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun get(@PathVariable name: String) = categoryService.getByName(name)

    @GetMapping
    fun getAll() = categoryService.getAll()

    @DeleteMapping("/{name}")
    fun deleteCategory(@PathVariable name: String) = categoryService.deleteByName(name)

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun edit(@RequestBody updatedCategory: Category, @PathVariable id: Long) =
        categoryService.update(updatedCategory, id)

}


