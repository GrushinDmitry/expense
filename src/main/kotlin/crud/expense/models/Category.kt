package crud.expense.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("CATEGORY")
data class Category(
    @Id
    val id: Long,
    val name: String
)