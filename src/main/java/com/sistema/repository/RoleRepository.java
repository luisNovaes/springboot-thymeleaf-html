package com.sistema.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sistema.models.ERole;
import com.sistema.models.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

	Optional<Role> findByName(ERole name);
}
