package com.sistema.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.models.Cadastro;
import com.sistema.repository.CadastroRepository;
import com.sistema.repository.RoleRepository;
import com.sistema.request.CadastroRequest;
import com.sistema.response.MessageResponse;
import com.sistema.security.services.jwt.JwtUtils;
import com.sistema.util.ConverteData;
import com.sistema.util.ValidaCPF;
import com.sistema.util.ValidacaoEmail;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cadastro")
public class CadastroController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CadastroRepository cadastroRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	ValidaCPF validaCPF;

	@Autowired
	ValidacaoEmail validacaoEmail;

	@Autowired
	ConverteData converteData;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> salvar(@Valid @RequestBody CadastroRequest cadastroRequest) {

		if (ConverteData.isDateValid(cadastroRequest.getDataNascimento()) == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: digite uma data válida!"));

		}

		if (cadastroRepository.existsByCpf(cadastroRequest.getCpf())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: este cpf já existe na base de dados!"));

		}

		if (ValidaCPF.isCPF(cadastroRequest.getCpf()) == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: CPF inválido!"));

		}

		Cadastro cadastro = new Cadastro(cadastroRequest.getEmail(), cadastroRequest.getNome(),
				cadastroRequest.getSexo(), cadastroRequest.getDataNascimento(), cadastroRequest.getNaturalidade(),
				cadastroRequest.getNacionalidade(), cadastroRequest.getCpf());

		String dataatual = ConverteData.converter();
		cadastro.setDataCadastro(dataatual);

		cadastroRepository.save(cadastro);

		return ResponseEntity.ok(new MessageResponse("Cadastro realisado com sucesso!"));
	}

	@GetMapping("/contatos")
	@PreAuthorize("hasRole('ADMIN')")
	public List<?> findAll() {
		return cadastroRepository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findByName(@PathVariable String id) {
		return cadastroRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") String id,
			@RequestBody CadastroRequest cadastroRequest) {

		if (ConverteData.isDateValid(cadastroRequest.getDataNascimento()) == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: data de nascimento inválida"));

		}

		return cadastroRepository.findById(id).map(record -> {

			record.setNome(cadastroRequest.getNome());
			record.setSexo(cadastroRequest.getSexo());
			record.setEmail(cadastroRequest.getEmail());
			record.setDataNascimento(cadastroRequest.getDataNascimento());
			record.setNaturalidade(cadastroRequest.getNaturalidade());
			record.setNacionalidade(cadastroRequest.getNacionalidade());
			record.setCpf(cadastroRequest.getCpf());
			record.setDataCadastro(record.getDataCadastro());

			Cadastro updated = cadastroRepository.save(record);

			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable String id) {

		return cadastroRepository.findById(id).map(record -> {
			cadastroRepository.deleteById(id);

			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
