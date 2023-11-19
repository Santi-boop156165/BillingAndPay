package com.mircroservices.transaction.app.controller;


import com.mircroservices.transaction.app.model.entities.TransactionEntity;
import com.mircroservices.transaction.app.payload.MessagePay;
import com.mircroservices.transaction.app.services.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;


    @GetMapping("transacciones")
    public ResponseEntity<?> getAllTransaccions(){
        Iterable<TransactionEntity> transaciones;


            transaciones = transactionService.findAll();
            if(transaciones != null){
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Consulta Exitosa")
                                .data(transaciones)
                                .build(),
                                HttpStatus.OK);
            }
            return new ResponseEntity<>
                    (MessagePay.builder()
                            .message("No se encontraron transacciones")
                            .build(),
                            HttpStatus.NOT_FOUND);


    }

    @GetMapping("transaccion/{id}")
    public ResponseEntity<?> getTransactionsById(@PathVariable Long id){
        TransactionEntity transactionEntity = transactionService.findById(id);
        if(transactionService.existsById(id)){
            return new ResponseEntity<>
                    (MessagePay.builder()
                            .message("Consulta Exitosa")
                            .data(transactionEntity)
                            .build(),
                            HttpStatus.OK);
        }else {
            return new ResponseEntity<>
                    (MessagePay.builder()
                            .message("No se encontro la transaccion")
                            .build(),
                            HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("transaccion/cliente")
    public ResponseEntity<?> getTransactionsByClienteId(@RequestParam Long clienteId){
        Iterable<TransactionEntity> transactionEntities;

            transactionEntities = transactionService.findByClienteId(clienteId);
            if (transactionEntities != null){
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Consulta Exitosa")
                                .data(transactionEntities)
                                .build(),
                                HttpStatus.OK);
            }else {
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No se encontraron transacciones")
                                .build(),
                                HttpStatus.NOT_FOUND);
            }

    }

    @PostMapping("transaccion")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionEntity transactionEntity){
        TransactionEntity transactionEntityCreated;
            transactionEntityCreated = transactionService.save(transactionEntity);
            return new ResponseEntity<>
                    (MessagePay.builder()
                            .message("Creado Correctamente")
                            .data(transactionEntityCreated)
                            .build(),
                            HttpStatus.CREATED);

    }

    @PutMapping("transaccion/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transactionEntity){
        TransactionEntity transactionEntityUpdated;
            if(!transactionService.existsById(id)){
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No se encontro la transaccion")
                                .build(),
                                HttpStatus.NOT_FOUND);
            } else {
                transactionEntity.setId(id);
                transactionEntityUpdated = transactionService.save(transactionEntity);
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Actualizado Correctamente")
                                .data(transactionEntityUpdated)
                                .build(),
                                HttpStatus.OK);
            }

    }

    @DeleteMapping("transaccion/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id){

            if(!transactionService.existsById(id)){
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("No se encontro la transaccion")
                                .build(),
                                HttpStatus.NOT_FOUND);
            } else {
                transactionService.deleteById(id);
                return new ResponseEntity<>
                        (MessagePay.builder()
                                .message("Eliminado Correctamente")
                                .build(),
                                HttpStatus.OK);
            }

    }


}
