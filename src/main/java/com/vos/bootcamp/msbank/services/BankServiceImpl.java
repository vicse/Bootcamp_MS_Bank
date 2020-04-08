package com.vos.bootcamp.msbank.services;

import com.vos.bootcamp.msbank.models.Bank;
import com.vos.bootcamp.msbank.repositories.BankRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankServiceImpl implements BankService {

  private final BankRepository bankRepository;

  public BankServiceImpl(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Flux<Bank> findAll() {
    return bankRepository.findAll();
  }

  @Override
  public Mono<Bank> findById(String id) {
    return bankRepository.findById(id);
  }

  @Override
  public Mono<Bank> save(Bank bank) {
    return bankRepository.save(bank);
  }

  @Override
  public Mono<Bank> update(String id, Bank bank) {
    return bankRepository.findById(id)
            .flatMap(bankDB -> {

              if (bank.getName() == null) {
                bankDB.setName(bankDB.getName());
              }
              else {
                bankDB.setName(bank.getName());
              }

              return bankRepository.save(bankDB);

            });
  }

  @Override
  public Mono<Bank> deleteById(String id) {
    return bankRepository.findById(id)
            .flatMap(bank -> bankRepository.delete(bank)
            .then(Mono.just(bank)));
  }
}
