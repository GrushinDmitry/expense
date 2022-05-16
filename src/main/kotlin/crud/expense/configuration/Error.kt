package crud.expense.configuration

class CategoryAlreadyExistsException(override val message: String) : RuntimeException()

class CategoryNotExistsException(override val message: String) : RuntimeException()

class PersonAlreadyExistsException(override val message: String) : RuntimeException()

class PersonNotExistsException(override val message: String) : RuntimeException()
