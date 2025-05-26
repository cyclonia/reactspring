package com.sunil.com.wisdompet.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sunil.com.wisdompet.data.entities.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

}
