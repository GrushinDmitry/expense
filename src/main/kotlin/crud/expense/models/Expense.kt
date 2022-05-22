package crud.expense.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("EXPENSE")
data class Expense(
    @Id
    val id: Long?,
    val cost: Long,
    val dateCreation: LocalDate?,
    val categoryName: String,
    val personId: Long
)
