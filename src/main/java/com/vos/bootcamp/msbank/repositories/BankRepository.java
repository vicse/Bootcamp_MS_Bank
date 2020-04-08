package com.vos.bootcamp.msbank.repositories;

import com.vos.bootcamp.msbank.models.Bank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BankRepository extends ReactiveMongoRepository<Bank, String> {
}
