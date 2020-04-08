package com.vos.bootcamp.msbank.service;

import com.vos.bootcamp.msbank.models.Bank;
import com.vos.bootcamp.msbank.repositories.BankRepository;
import com.vos.bootcamp.msbank.services.BankService;
import com.vos.bootcamp.msbank.services.BankServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankServiceTest {

  private final Bank bank1 = Bank.builder().name("INTERBANK").codeBank(100).build();
  private final Bank bank2 = Bank.builder().name("BBVA").codeBank(200).build();
  private final Bank bank3 = Bank.builder().name("BCP").codeBank(300).build();

  @Mock
  private BankRepository bankRepository;

  private BankService bankService;

  @BeforeEach
  void SetUp(){
    bankService = new BankServiceImpl(bankRepository) {
    };
  }

  @Test
  void getAll() {
    when(bankRepository.findAll()).thenReturn(Flux.just(bank1, bank2, bank3));

    Flux<Bank> actual = bankService.findAll();

    assertResults(actual, bank1, bank2, bank3);
  }

  @Test
  void getById_whenIdExists_returnCorrectBank() {
    when(bankRepository.findById(bank1.getId())).thenReturn(Mono.just(bank1));

    Mono<Bank> actual = bankService.findById(bank1.getId());

    assertResults(actual, bank1);
  }

  @Test
  void getById_whenIdNotExist_returnEmptyMono() {
    when(bankRepository.findById(bank1.getId())).thenReturn(Mono.empty());

    Mono<Bank> actual = bankService.findById(bank1.getId());

    assertResults(actual);
  }

  @Test
  void create() {
    when(bankRepository.save(bank1)).thenReturn(Mono.just(bank1));

    Mono<Bank> actual = bankService.save(bank1);

    assertResults(actual, bank1);
  }

  @Test
  void update_whenIdExists_returnUpdatedBank() {
    when(bankRepository.findById(bank1.getId())).thenReturn(Mono.just(bank1));
    when(bankRepository.save(bank1)).thenReturn(Mono.just(bank1));

    Mono<Bank> actual = bankService.update(bank1.getId(), bank1);

    assertResults(actual, bank1);
  }

  @Test
  void update_whenIdNotExist_returnEmptyMono() {
    when(bankRepository.findById(bank1.getId())).thenReturn(Mono.empty());

    Mono<Bank> actual = bankService.update(bank1.getId(), bank1);

    assertResults(actual);
  }

  @Test
  void delete_whenBankExists_performDeletion() {
    when(bankRepository.findById(bank1.getId())).thenReturn(Mono.just(bank1));
    when(bankRepository.delete(bank1)).thenReturn(Mono.empty());

    Mono<Bank> actual = bankService.deleteById(bank1.getId());

    assertResults(actual, bank1);
  }

  @Test
  void delete_whenIdNotExist_returnEmptyMono() {
    when(bankRepository.findById(bank1.getId())).thenReturn(Mono.empty());

    Mono<Bank> actual = bankService.deleteById(bank1.getId());

    assertResults(actual);
  }



  private void assertResults(Publisher<Bank> publisher, Bank... expectedBank) {
    StepVerifier
            .create(publisher)
            .expectNext(expectedBank)
            .verifyComplete();
  }
  

}
