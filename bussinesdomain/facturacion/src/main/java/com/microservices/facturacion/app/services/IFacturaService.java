package com.microservices.facturacion.app.services;

import com.microservices.facturacion.app.model.entities.Factura;


public interface IFacturaService {
    Factura save (Factura factura);
    Factura findById (Long id);

    Iterable<Factura> findAll();

    void deleteById(Long id);

    Boolean existsById(Long id);


    public Factura findByCode(String code);



}
