package com.microservices.facturacion.app.model.dao;


import com.microservices.facturacion.app.model.entities.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FacturaDao extends CrudRepository<Factura, Long> {
    @Query("select f from Factura f where f.codigo = ?1")
    public Factura findByCode(String code);
}
