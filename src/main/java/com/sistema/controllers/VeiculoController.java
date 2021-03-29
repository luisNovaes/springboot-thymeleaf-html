package com.sistema.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.models.Veiculo;
import com.sistema.repository.VeiculoRepository;
import com.sistema.request.VeiculoRequest;
import com.sistema.response.MessageResponse;
import com.sistema.util.ConverteData;
import com.sistema.util.ValidaCPF;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	VeiculoRepository veiculoRepository;

	@Autowired
	ValidaCPF documento;

	@Autowired
	ConverteData converteData;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> salvar(@Valid @RequestBody VeiculoRequest veiculoRequest) {

		Veiculo veiculo = new Veiculo(veiculoRequest.getFabricante(), veiculoRequest.getModelo(),
				veiculoRequest.getDocumento(), veiculoRequest.getAno(), veiculoRequest.getPlaca(),
				veiculoRequest.getStatus());

		veiculoRepository.save(veiculo);

		return ResponseEntity.ok(new MessageResponse("Cadastro realisado com sucesso!"));
	}

	@GetMapping("/veiculos")
//	@PreAuthorize("hasRole('ADMIN')")
	public List<?> findAll() {
		return veiculoRepository.findAll();
	}

	@GetMapping(path = { "/id/{id}" })
	public ResponseEntity<?> findById(@PathVariable String id) {
		return veiculoRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}
	
	@GetMapping(path = { "/placa/{placa}" })
	public ResponseEntity<?> findByPlaca(@PathVariable String placa) {
		return veiculoRepository.findByPlaca(placa).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}
	
	@GetMapping(path = { "/modelos/{modelo}" })
	public ResponseEntity<?> findByModelo(@PathVariable String modelo) {
		return veiculoRepository.findByModelo(modelo).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody VeiculoRequest veiculoRequest) {

		return veiculoRepository.findById(id).map(record -> {

			record.setFabricante(veiculoRequest.getFabricante());
			record.setModelo(veiculoRequest.getModelo());
			record.setDocumento(veiculoRequest.getDocumento());
			record.setAno(veiculoRequest.getAno());
			record.setPlaca(veiculoRequest.getPlaca());
			record.setStatus(veiculoRequest.getStatus());

			Veiculo updated = veiculoRepository.save(record);

			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable String id) {

		return veiculoRepository.findById(id).map(record -> {
			veiculoRepository.deleteById(id);

			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
