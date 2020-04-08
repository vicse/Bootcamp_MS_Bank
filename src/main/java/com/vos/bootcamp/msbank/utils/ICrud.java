package com.vos.bootcamp.msbank.utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrud<T> {

  public Flux<T> findAll();

  public Mono<T> findById(String id);

  public Mono<T> save(T t);

  public Mono<T> update(String id, T t);

  public Mono<T> deleteById(String id);

}
