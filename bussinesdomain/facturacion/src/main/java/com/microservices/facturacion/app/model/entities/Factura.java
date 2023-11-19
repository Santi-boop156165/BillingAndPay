package com.microservices.facturacion.app.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Valid
    @NotBlank(message = "El campo clienteId no puede estar vacio")
    private long clienteId;
    @Valid
    @NotBlank(message = "El campo codigo no puede estar vacio")
    private String codigo;
    @NotBlank(message = "El campo fecha_emision no puede estar vacio")
    private LocalDateTime fecha_emision;
    @NotBlank(message = "El campo fecha_vencimiento no puede estar vacio")
    private LocalDateTime fecha_vencimiento;
    @NotBlank(message = "El campo estado no puede estar vacio")
    private String estado;
    @NotBlank(message = "El campo total no puede estar vacio")
    private BigDecimal total;
    @NotBlank(message = "El campo subtotal no puede estar vacio")
    private BigDecimal subtotal;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteProcedimiento> procedimientos;
    @Transient
    private Object Cliente;
    @Transient
    List<?> transacciones;
}
