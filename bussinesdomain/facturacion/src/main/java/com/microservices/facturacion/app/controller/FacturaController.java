package com.microservices.facturacion.app.controller;

import com.microservices.facturacion.app.model.entities.Factura;
import com.microservices.facturacion.app.model.payload.MessagePay;
import com.microservices.facturacion.app.services.impl.FacturaServiceImpl;
import com.microservices.facturacion.app.services.transaccion.Transaccion;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class FacturaController {

    private final FacturaServiceImpl facturaService;
    private final Transaccion transaccion;



    @GetMapping("facturas")
    public ResponseEntity<?> getAllFacturas() {
        Iterable<Factura> facturas;
            facturas = facturaService.findAll();
            if (facturas.iterator().hasNext() )  {
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Consulta Exitosa")
                                .data(facturas)
                                .build(),
                                HttpStatus.OK);
            }else {
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No Hay Facturas Registradas")
                                .build(),
                                HttpStatus.NO_CONTENT);
            }

    }

    @GetMapping("factura/{id}")
    public ResponseEntity<?> getFacturaById(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);


            if (facturaService.existsById(id)){
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Consulta Exitosa")
                                .data(factura)
                                .build(),
                                HttpStatus.OK);
            }else {
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No se encontro la factura")
                                .build(),
                                HttpStatus.NOT_FOUND);
            }


    }

    @PostMapping("factura")
    public ResponseEntity<?> createFactura(@Valid @RequestBody Factura factura) {
        Factura facturaCreated;
            facturaCreated = facturaService.save(factura);
            return new ResponseEntity<>
                    (MessagePay.builder()
                            .message("Creado Correctamente")
                            .data(facturaCreated)
                            .build(),
                            HttpStatus.CREATED);
    }

    @PutMapping("factura/{id}")
    public ResponseEntity<?> updateFactura(@RequestBody Factura factura, @PathVariable Long id) {
        Factura facturaUpdated;

            if (!facturaService.existsById(id)) {
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No existe la factura con id: " + id)
                                .build(),
                                HttpStatus.NOT_FOUND);
            } else {
                factura.setId(id);
                factura.getProcedimientos().forEach(x -> x.setFactura(factura));
                facturaUpdated = facturaService.save(factura);
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Actualizado Correctamente")
                                .data(facturaUpdated)
                                .build(),
                                HttpStatus.CREATED);
            }


    }

    @DeleteMapping("factura/{id}")
    public ResponseEntity<?> deleteFactura(@PathVariable Long id) {

            if (!facturaService.existsById(id)) {
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No existe la factura con id: " + id)
                                .build(),
                                HttpStatus.NOT_FOUND);
            } else {
                facturaService.deleteById(id);
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Eliminado Correctamente")
                                .build(),
                                HttpStatus.OK);
            }


    }
    @GetMapping("fullFactura")
    public ResponseEntity<Factura> getFacturaByCode(@RequestParam String code){
        Factura factura = transaccion.getFacturaByCode(code);
        return ResponseEntity.ok().body(factura);
    }






}
