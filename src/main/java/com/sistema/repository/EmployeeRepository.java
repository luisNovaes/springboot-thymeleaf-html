package com.sistema.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sistema.models.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

//	Boolean existsByEmail(String email);
//
//	Boolean existsByCpf(String cpf);
//
//	Optional<Cadastro> findByCpf(String string);

}
