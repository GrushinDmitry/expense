package crud.expense.configuration

data class Error(val code: String?, val message: String?)
data class ErrorResponse(val errors: List<Error>)

class ExpenseAccountingException(override val message: String, val value: String? = null) : RuntimeException()

object ErrorCode {
    const val NO_CATEGORY_BY_NAME = "Category_not_found_by_name"
    const val NO_CATEGORY_BY_ID = "Category_not_found_by_id"
    const val CATEGORY_ALREADY = "Category_already_exists"
    const val NO_CATEGORIES = "Categories_not_found"
    const val NO_PERSONS = "Persons_not_found"
    const val NO_PERSON_BY_ID = "Person_not_found_by_id"
    const val NO_PERSON_BY_FIRSTNAME_LASTNAME = "Person_not_found_by_firstname_and_lastname"
    const val UNKNOWN_ERROR = "Unknown_error"
}