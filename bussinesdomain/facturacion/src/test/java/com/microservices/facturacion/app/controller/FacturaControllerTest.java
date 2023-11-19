package com.microservices.facturacion.app.controller;

import com.microservices.facturacion.app.model.entities.ClienteProcedimiento;
import com.microservices.facturacion.app.model.entities.Factura;
import com.microservices.facturacion.app.model.payload.MessagePay;
import com.microservices.facturacion.app.services.impl.FacturaServiceImpl;

import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FacturaControllerTest {
    @Mock
    private FacturaServiceImpl facturaService;
    @InjectMocks
    private FacturaController facturaController;




    private Factura facturaMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ClienteProcedimiento procedimientoMock = ClienteProcedimiento.builder()
                .id(1L)
                .procedimientoId(200L)
                .procedimientoNombre("Nombre del Procedimiento")
                .build();

        List<ClienteProcedimiento> procedimientos = new ArrayList<>();
        procedimientos.add(procedimientoMock);

        facturaMock = Factura.builder()
                .id(1L)
                .clienteId(100L)
                .codigo("FAC-001")
                .fecha_emision(LocalDateTime.now())
                .fecha_vencimiento(LocalDateTime.now().plusDays(30))
                .estado("PENDIENTE")
                .total(new BigDecimal("100.00"))
                .subtotal(new BigDecimal("80.00"))
                .procedimientos(procedimientos)
                .build();
    }




    //The test uses methods Given, When, Then (GWT)

    @Test
    void getAllFacturas_ShouldReturnFacturas_WhenFacturasExist() {

        given(facturaService.findAll()).willReturn(Collections.singletonList(facturaMock));
        ResponseEntity<?> response = facturaController.getAllFacturas();
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        MessagePay message = (MessagePay) response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(message.getMessage()).isEqualTo("Consulta Exitosa");
        assertThat(message.getData()).isInstanceOf(Iterable.class);
        verify(facturaService).findAll();
    }

    @Test
    void getAllFacturas_ShouldReturnNoContent_WhenNoFacturasExist() {
        // Dado (Given)
        given(facturaService.findAll()).willReturn(Collections.emptyList());

        // Cuando (When)
        ResponseEntity<?> response = facturaController.getAllFacturas();

        // Entonces (Then)
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
        assertThat(response.getBody()).isNotNull();
        MessagePay message = (MessagePay) response.getBody();
        assertThat(message.getMessage()).isEqualTo("No Hay Facturas Registradas");
        verify(facturaService).findAll();
    }

    @Test
    void createFactura_ShouldCreateFactura_WhenFacturaDataIsValid() {

        given(facturaService.save(facturaMock)).willReturn(facturaMock);

        ResponseEntity<?> response = facturaController.createFactura(facturaMock);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        MessagePay message = (MessagePay) response.getBody();
        assertThat(message.getMessage()).isEqualTo("Creado Correctamente");
        assertThat(message.getData()).isEqualTo(facturaMock);
        verify(facturaService).save(facturaMock);
    }

    @Test
    void updateFactura_ShouldUpdateFactura_WhenFacturaExists() {
        Long facturaId = facturaMock.getId();
        given(facturaService.existsById(facturaId)).willReturn(true);
        given(facturaService.save(any(Factura.class))).willReturn(facturaMock);
        ResponseEntity<?> response = facturaController.updateFactura(facturaMock, facturaId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        MessagePay message = (MessagePay) response.getBody();
        assertThat(message.getMessage()).isEqualTo("Actualizado Correctamente");
        assertThat(message.getData()).isEqualTo(facturaMock);

        verify(facturaService).existsById(facturaId);
        verify(facturaService).save(any(Factura.class));
    }

    @Test
    void updateFactura_ShouldReturnNotFound_WhenFacturaDoesNotExist() {
        Long facturaId = facturaMock.getId();
        given(facturaService.existsById(facturaId)).willReturn(false);
        ResponseEntity<?> response = facturaController.updateFactura(facturaMock, facturaId);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();

        MessagePay message = (MessagePay) response.getBody();
        assertThat(message.getMessage()).isEqualTo("No existe la factura con id: " + facturaId);

        verify(facturaService).existsById(facturaId);
    }
    @Test
    void deleteFactura_ShouldDeleteFactura_WhenFacturaExists() {
        Long facturaId = 1L;
        given(facturaService.existsById(facturaId)).willReturn(true);

        ResponseEntity<?> response = facturaController.deleteFactura(facturaId);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        MessagePay message = (MessagePay) response.getBody();
        assertThat(message.getMessage()).isEqualTo("Eliminado Correctamente");

        verify(facturaService).existsById(facturaId);
        verify(facturaService).deleteById(facturaId);
    }
    @Test
    void deleteFactura_ShouldReturnNotFound_WhenFacturaDoesNotExist() {

        Long facturaId = 1L;
        given(facturaService.existsById(facturaId)).willReturn(false);


        ResponseEntity<?> response = facturaController.deleteFactura(facturaId);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();

        MessagePay message = (MessagePay) response.getBody();
        assertThat(message.getMessage()).isEqualTo("No existe la factura con id: " + facturaId);

        verify(facturaService).existsById(facturaId);
    }
}

