package crud.expense.configuration

data class WarningOrError(val code: String?, val message: String?)
data class WarningOrErrorResponse(val warningsOrErrors: List<WarningOrError>)

object WarningCode {
    const val CATEGORY_ALREADY = "Category_already_exists"
    const val PERSON_ALREADY = "Person_already_exists"
}

object ErrorCode {
    const val UNKNOWN_ERROR = "Unknown_error"
    const val NO_CATEGORY_BY_NAME = "Category_not_found_by_name"
    const val NO_CATEGORY_BY_ID = "Category_not_found_by_id"
    const val NO_CATEGORIES = "Categories_not_found"
    const val NO_PERSONS = "Persons_not_found"
    const val NO_PERSON_BY_ID = "Person_not_found_by_id"
    const val NO_EXPENSE_BY_CATEGORY_NAME = "Expense_not_found_by_category_name"
    const val NO_EXPENSE_BY_PERSON_ID = "Expense_not_found_by_person_id"
    const val NO_EXPENSE_WITH_COST_LESS = "Expense_not_found_with_cost_less"
    const val NO_EXPENSE_WITH_COST_GREATER = "Expense_not_found_with_cost_greater"
    const val NO_EXPENSE_BETWEEN_START_DATE_AND_END_DATE = "Expense_not_found_between_start_date_and_end_date"
    const val ILLEGAL_ARGUMENT = "Illegal_argument"
}