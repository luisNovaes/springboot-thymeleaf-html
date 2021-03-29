package com.sistema.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sistema.models.Cadastro;

@Repository
public interface CadastroRepository extends MongoRepository<Cadastro, String> {


	Boolean existsByEmail(String email);

	Boolean existsByCpf(String cpf);

	Optional<Cadastro> findByCpf(String string);


}
