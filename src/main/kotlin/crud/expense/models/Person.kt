package crud.expense.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("PERSON")
data class Person(
    @Id
    val id: Long,
    val firstname: String,
    val lastname: String
)


