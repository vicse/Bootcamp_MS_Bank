package com.vos.bootcamp.msbank.controllers;

import com.vos.bootcamp.msbank.models.Bank;
import com.vos.bootcamp.msbank.services.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/banks")
@Api(value = "Bank Microservice")
public class BankController {

  private final BankService bankService;

  public BankController(BankService bankService) {
    this.bankService = bankService;
  }
  
  /* =====================================
      Function to List all Banks
     ===================================== */

  @GetMapping
  @ApiOperation(value = "List all bank Types", notes = "List all bank types of Collections")
  public Flux<Bank> getBanks() {
    return bankService.findAll();
  }

  /* ===============================================
      Function to obtain a Bank by id Bank
     ============================================ */

  @GetMapping("/{id}")
  @ApiOperation(value = "Get a bank Type", notes = "Get a bank type by Id")
  public Mono<ResponseEntity<Bank>> getByIdBank(@PathVariable String id) {
    return bankService.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );
  }

  /* ===============================================
          Function to create a Bank
  =============================================== */

  @PostMapping
  @ApiOperation(value = "Create bank Type", notes = "Create bank type, check the model please")
  public Mono<ResponseEntity<Bank>> createBank(@Valid @RequestBody Bank Bank) {
    return bankService.save(Bank)
            .map(bankDB -> ResponseEntity
                    .created(URI.create("/api/banks/".concat(bankDB.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bankDB)
            );
  }

  /* ===============================================
          Function to update a Bank by id
  =============================================== */

  @PutMapping("/{id}")
  @ApiOperation(value = "Update Bank", notes = "Update bank type by ID")
  public Mono<ResponseEntity<Bank>> updateBank(@PathVariable String id, @RequestBody Bank Bank) {
    return bankService.update(id, Bank)
            .map(bankDB -> ResponseEntity
                    .created(URI.create("/api/banks/".concat(bankDB.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bankDB))
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /* ===============================================
          Function to delete a Bank by id
  =============================================== */

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete Bank", notes = "Delete bank type by ID")
  public Mono<ResponseEntity<Void>> deleteByIdBank(@PathVariable String id) {
    return bankService.deleteById(id)
            .map(res -> ResponseEntity.ok().<Void>build())
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );

  }


}
