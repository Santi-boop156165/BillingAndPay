package com.microservices.facturacion.app.model.payload;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class MessagePay implements Serializable {
    private String message;
    private Object data;
}
