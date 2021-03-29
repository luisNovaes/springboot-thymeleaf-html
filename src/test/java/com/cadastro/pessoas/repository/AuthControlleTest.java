package com.cadastro.pessoas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sistema.models.Cadastro;
import com.sistema.models.User;
import com.sistema.repository.RoleRepository;
import com.sistema.repository.UserRepository;
import com.sistema.response.JwtResponse;
import com.sistema.security.UserDetailsImpl;
import com.sistema.security.services.jwt.JwtUtils;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AuthControlleTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	PasswordEncoder encoder;

	@Test
	@Order(1)
	public void testRegisterUser() {
		User user = new User();

		user.setUsername("teste");
		user.setEmail("teste@gmail.com");

		String senha = encoder.encode("12345678");
		user.setPassword(senha);

		User cadastrados = userRepository.save(user);

		assertThat(cadastrados.getEmail()).isEqualTo("teste@gmail.com");

	}

	@Test
	@Order(2)
	public void testauthenticateUser() {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("teste", "12345678"));

		boolean loggedUser = authentication.isAuthenticated();

		assertThat(loggedUser).isTrue();
	}

}
