package crud.expense

import crud.expense.configuration.ClientConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ClientConfig::class)
class ExpenseApplication

fun main(args: Array<String>) {
	runApplication<ExpenseApplication>(*args)
}
