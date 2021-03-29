package com.sistema.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sistema.models.Cadastro;
import com.sistema.models.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {


	Optional<Produto> findById(String string);

}
