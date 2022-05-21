package crud.expense.configuration

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.MessageSource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.Locale

@Component
class ExceptionHandler(val objectMapper: ObjectMapper, val messageSource: MessageSource) : ErrorWebExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    data class MessageResponse(val messageType: String, val codeAndMessage: List<WarningOrError>)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        return when (ex) {
            is MemberServiceNotFoundException -> handleMemberServiceNotFoundException(exchange, ex)
            is MemberServiceAlreadyExistsException -> handleMemberServiceAlreadyExistsException(exchange, ex)
            else -> handleUnknownError(exchange)
        }
    }

    private fun handleMemberServiceNotFoundException(
        exchange: ServerWebExchange,
        ex: MemberServiceNotFoundException
    ): Mono<Void> {
        return buildResponseAndLog(
            HttpStatus.NOT_FOUND,
            WarningOrErrorResponse(
                listOf(
                    WarningOrError(
                        code = ex.message, message = messageSource.getMessage(
                            ex.message, arrayOf(ex.value), Locale.getDefault()
                        )
                    )
                )
            ),
            exchange
        )
    }

    private fun handleMemberServiceAlreadyExistsException(
        exchange: ServerWebExchange,
        ex: MemberServiceAlreadyExistsException
    ): Mono<Void> {
        return buildResponseAndLog(
            HttpStatus.OK,
            WarningOrErrorResponse(
                listOf(
                    WarningOrError(
                        code = ex.message, message = messageSource.getMessage(
                            ex.message, arrayOf(ex.value), Locale.getDefault()
                        )
                    )
                )
            ),
            exchange
        )
    }

    private fun handleUnknownError(exchange: ServerWebExchange): Mono<Void> {
        return buildResponseAndLog(
            HttpStatus.INTERNAL_SERVER_ERROR,
            WarningOrErrorResponse(
                listOf(
                    WarningOrError(
                        code = ErrorCode.UNKNOWN_ERROR, message = messageSource.getMessage(
                            ErrorCode.UNKNOWN_ERROR, null, Locale.getDefault()
                        )
                    )
                )
            ),
            exchange
        )
    }

    private fun buildResponseAndLog(
        status: HttpStatus, warningOrErrorResponse: WarningOrErrorResponse,
        exchange: ServerWebExchange
    ): Mono<Void> {
        val bufferFactory = exchange.response.bufferFactory()
        val requestMessageType = if (status.isError) "Error" else "Warning"
        val dataBuffer: DataBuffer = try {
            bufferFactory.wrap(
                objectMapper.writeValueAsBytes(
                    MessageResponse(
                        requestMessageType,
                        warningOrErrorResponse.warningsOrErrors
                    )
                )
            )
        } catch (e: JsonProcessingException) {
            bufferFactory.wrap("".toByteArray())
        }
        exchange.response.statusCode = status
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON
        logResponse(status, warningOrErrorResponse, exchange)
        return exchange.response.writeWith(Mono.just(dataBuffer))
    }

    private fun logResponse(
        status: HttpStatus,
        warningOrErrorResponse: WarningOrErrorResponse,
        exchange: ServerWebExchange
    ) {
        val logString =
            "statusCode=${status.value()}, response=$warningOrErrorResponse, requestPath=${exchange.request.path}, methodValue=${exchange.request.methodValue}"
        if (status.isError)
            logger.error(logString)
        else
            logger.warn(logString)
    }

}

