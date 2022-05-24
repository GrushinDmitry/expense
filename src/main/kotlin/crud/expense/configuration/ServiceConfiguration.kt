package crud.expense.configuration

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit


@Configuration
@ConfigurationProperties
class ServiceConfiguration() {

    @Bean
    fun client(clientConfig: ClientConfig): HttpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.connectTimeoutInSeconds.toMillis().toInt())
        .doOnConnected {
            it.addHandlerFirst(ReadTimeoutHandler(clientConfig.readTimeoutInSeconds.seconds, TimeUnit.SECONDS))
        }

    @Bean
    fun webClient(httpClient: HttpClient): WebClient =
        WebClient.builder().clientConnector(ReactorClientHttpConnector(httpClient)).build()

}

