package crud.expense

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class ExpenseApplication

fun main(args: Array<String>) {
	runApplication<ExpenseApplication>(*args)
}
