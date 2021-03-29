package com.cadastro.pessoas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sistema.models.Cadastro;
import com.sistema.repository.CadastroRepository;
import com.sistema.repository.RoleRepository;
import com.sistema.util.ConverteData;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CadastroControllerTest {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	CadastroRepository cadastroRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Test
	@Order(1)
	public void testRegisterUser() {
		Cadastro cadastro = new Cadastro();

		cadastro.setNome("Antonio dos Santos");
		cadastro.setEmail("santos@gmail.com");
		cadastro.setSexo("M");
		cadastro.setDataNascimento("29/10/1999");
		cadastro.setNaturalidade("Recife");
		cadastro.setNacionalidade("Brasil");
		cadastro.setCpf("85656233600");

		String dataatual = ConverteData.converter();
		cadastro.setDataCadastro(dataatual);

		Cadastro cadastrados = cadastroRepository.save(cadastro);

		assertThat(cadastrados.getCpf()).isEqualTo("85656233600");

	}

	@Test
	@Order(2)
	public void testFindAll() {

		List<Cadastro> cadastro = cadastroRepository.findAll();

		assertThat(cadastro.size()).isNotEqualTo(0);
	}

	@Test
	@Order(3)
	public void testUpdate() {

		Optional<Cadastro> result = cadastroRepository.findByCpf("85656233600");
		Cadastro cadastro = result.get();

		cadastro.setNome("Antonio Souza dos Santos");
		cadastro.setEmail("antonio_santos@gmail.com");
		cadastro.setSexo("M");
		cadastro.setDataNascimento("29/10/1999");
		cadastro.setNaturalidade("São Paulo");
		cadastro.setNacionalidade("Brasil");
		cadastro.setCpf("85656233600");

		String dataatual = ConverteData.converter();
		cadastro.setDataCadastro(dataatual);
		;

		Cadastro cadastrado = cadastroRepository.save(cadastro);

		assertThat(cadastrado.getNome()).isEqualTo("Antonio Souza dos Santos");

	}

	@Test
	@Order(4)
	public void testfindByName() {

		Optional<Cadastro> result = cadastroRepository.findByCpf("85656233600");
		Cadastro cadastro = result.get();

		assertThat(cadastro.getNome()).isEqualTo("Antonio Souza dos Santos");

	}

}
