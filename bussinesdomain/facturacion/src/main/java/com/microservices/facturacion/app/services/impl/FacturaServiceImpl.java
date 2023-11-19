package com.microservices.facturacion.app.services.impl;

import com.microservices.facturacion.app.model.dao.FacturaDao;
import com.microservices.facturacion.app.model.entities.Factura;
import com.microservices.facturacion.app.services.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private FacturaDao facturaDao;

    @Transactional
    @Override
    public Factura save(Factura factura) {
        factura.getProcedimientos().forEach(procedimiento -> procedimiento.setFactura(factura));
        return facturaDao.save(factura);

    }


    @Transactional(readOnly = true)
    @Override
    public Factura findById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Factura> findAll() {
        return facturaDao.findAll();
    }



    @Transactional
    @Override
    public void deleteById(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return facturaDao.existsById(id);
    }

    @Override
    public Factura findByCode(String code) {
        return facturaDao.findByCode(code);
    }
}
