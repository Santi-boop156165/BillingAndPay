package com.microservices.facturacion.app.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente_procedimiento")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteProcedimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long procedimientoId;
    @Transient
    private String procedimientoNombre;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Factura.class)
    @JoinColumn(name = "facturaId", nullable = false)
    private Factura factura;
}
