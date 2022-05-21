package crud.expense.configuration

class MemberServiceNotFoundException(override val message: String, val value: String? = null) : RuntimeException()

class MemberServiceAlreadyExistsException(override val message: String, val value: String? = null) : RuntimeException()