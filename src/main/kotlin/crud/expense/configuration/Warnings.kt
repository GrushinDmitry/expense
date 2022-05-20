package crud.expense.configuration

data class Warnings(val code: String?, val message: String?)
data class WarningResponse(val warnings: List<Warnings>)

class MemberServiceNotFoundException(override val message: String, val value: String? = null) : RuntimeException()

class MemberServiceAlreadyExistsException(override val message: String, val value: String? = null) : RuntimeException()

object ErrorCode {
    const val NO_CATEGORY_BY_NAME = "Category_not_found_by_name"
    const val NO_CATEGORY_BY_ID = "Category_not_found_by_id"
    const val CATEGORY_ALREADY = "Category_already_exists"
    const val NO_CATEGORIES = "Categories_not_found"
    const val NO_PERSONS = "Persons_not_found"
    const val NO_PERSON_BY_ID = "Person_not_found_by_id"
    const val PERSON_ALREADY = "Person_already_exists"
    const val UNKNOWN_ERROR = "Unknown_error"
    const val NO_EXPENSE_BY_CATEGORY_NAME = "Expense_not_found_by_category_name"
}