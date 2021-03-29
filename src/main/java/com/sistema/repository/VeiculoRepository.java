package com.sistema.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sistema.models.Veiculo;

@Repository
public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

	Boolean existsByPlaca(String placa);

	Boolean existsByModelo(String modelo);

	Optional<Veiculo> findById(String id);

	Optional<Veiculo> findByPlaca(String placa);

	Optional<Veiculo> findByModelo(String modelo);

}
