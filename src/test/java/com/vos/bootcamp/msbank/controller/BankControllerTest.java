package com.vos.bootcamp.msbank.controller;

import com.vos.bootcamp.msbank.controllers.BankController;
import com.vos.bootcamp.msbank.models.Bank;
import com.vos.bootcamp.msbank.services.BankService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class BankControllerTest {

  @Mock
  private BankService bankService;
  private WebTestClient client;
  private List<Bank> expectedBanks;

  @BeforeEach
  void setUp() {
    client = WebTestClient
            .bindToController(new BankController(bankService))
            .configureClient()
            .baseUrl("/api/banks")
            .build();

    expectedBanks = Arrays.asList(
            Bank.builder().id("1").name("INTERBANK").codeBank(100).build(),
            Bank.builder().id("2").name("BBVA").codeBank(200).build(),
            Bank.builder().id("3").name("BCP").codeBank(300).build());

  }

  @Test
  void getAllProducts() {
    when(bankService.findAll()).thenReturn(Flux.fromIterable(expectedBanks));

    client.get()
            .uri("/")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Bank.class)
            .isEqualTo(expectedBanks);
  }
  @Test
  void getBankById_whenBankExists_returnCorrectBank() {
    Bank expectedBank = expectedBanks.get(0);
    when(bankService.findById(expectedBank.getId())).thenReturn(Mono.just(expectedBank));

    client.get()
            .uri("/{id}", expectedBank.getId())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Bank.class)
            .isEqualTo(expectedBank);
  }

  @Test
  void getBankById_whenBankNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    when(bankService.findById(id)).thenReturn(Mono.empty());

    client.get()
            .uri("/{id}", id)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void addBank() {
    Bank expectedBank = expectedBanks.get(1);
    when(bankService.save(expectedBank)).thenReturn(Mono.just(expectedBank));

    client.post()
            .uri("/")
            .body(Mono.just(expectedBank), Bank.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Bank.class)
            .isEqualTo(expectedBank);
  }

  @Test
  void updateBank_whenBankExists_performUpdate() {
    Bank expectedBank = expectedBanks.get(0);
    when(bankService.update(expectedBank.getId(), expectedBank)).thenReturn(Mono.just(expectedBank));

    client.put()
            .uri("/{id}", expectedBank.getId())
            .body(Mono.just(expectedBank), Bank.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Bank.class)
            .isEqualTo(expectedBank);
  }

  @Test
  void updateBank_whenBankNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    Bank expectedBank = expectedBanks.get(0);
    when(bankService.update(id, expectedBank)).thenReturn(Mono.empty());

    client.put()
            .uri("/{id}", id)
            .body(Mono.just(expectedBank), Bank.class)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void deleteBank_whenBankExists_performDeletion() {
    Bank bankToDelete = expectedBanks.get(0);
    when(bankService.deleteById(bankToDelete.getId())).thenReturn(Mono.just(bankToDelete));

    client.delete()
            .uri("/{id}", bankToDelete.getId())
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  void deleteBank_whenIdNotExist_returnNotFound() {
    Bank bankToDelete = expectedBanks.get(0);
    when(bankService.deleteById(bankToDelete.getId())).thenReturn(Mono.empty());

    client.delete()
            .uri("/{id}", bankToDelete.getId())
            .exchange()
            .expectStatus()
            .isNotFound();
  }



}
