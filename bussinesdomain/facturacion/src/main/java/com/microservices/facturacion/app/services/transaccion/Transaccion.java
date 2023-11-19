package com.microservices.facturacion.app.services.transaccion;

import com.microservices.facturacion.app.model.entities.Factura;
import com.microservices.facturacion.app.services.impl.FacturaServiceImpl;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class Transaccion {

    private  WebClient.Builder webClientBuilder;
    private final FacturaServiceImpl facturaService;
    private final HttpClient httpClient;





    public Factura getFacturaByCode(@RequestParam String code){
        Factura factura = facturaService.findByCode(code);
        System.out.println(code);
        List<?> transactions = getTransactions(factura.getClienteId());
        factura.setTransacciones(transactions);
        return factura;
    }


    private List<?> getTransactions(Long clienteId){

        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://transaccion-module:8092/api/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return build.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/transaccion/cliente")
                        .queryParam("clienteId", clienteId)
                        .build())
                .retrieve().bodyToFlux(Object.class).collectList().block();

    }
}
