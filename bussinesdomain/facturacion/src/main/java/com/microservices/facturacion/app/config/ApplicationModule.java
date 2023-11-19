package com.microservices.facturacion.app.config;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class ApplicationModule {
@Bean
    public HttpClient buildHtppClient(){
       return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(EpollChannelOption.TCP_KEEPIDLE, 300)
                .option(EpollChannelOption.TCP_KEEPINTVL, 60)
                .responseTimeout(Duration.ofSeconds(5))
                .doOnConnected(conn -> {
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    conn.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });

    }

}
