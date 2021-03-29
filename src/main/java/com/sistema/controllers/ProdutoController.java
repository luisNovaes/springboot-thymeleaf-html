package com.sistema.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.models.Produto;
import com.sistema.repository.ProdutoRepository;
import com.sistema.request.ProdutoRequest;
import com.sistema.response.MessageResponse;
import com.sistema.util.ConverteData;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	ConverteData converteData;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> salvar(@Valid @RequestBody ProdutoRequest produtoRequest) {

		Produto produto = new Produto(produtoRequest.getNome_produto(), produtoRequest.getDesc_produto(),
				produtoRequest.getPreco_produto());

		String dataatual = ConverteData.converter();
		produto.setDt_atualizacao(dataatual);

		produtoRepository.save(produto);

		return ResponseEntity.ok(new MessageResponse("Cadastro realisado com sucesso!"));
	}

	@GetMapping("/produtos")
//	@PreAuthorize("hasRole('ADMIN')")
	public List<?> findAll() {
		return produtoRepository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findByName(@PathVariable String id) {
		return produtoRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody ProdutoRequest produtoRequest) {

		return produtoRepository.findById(id).map(record -> {

			record.setNome_produto(produtoRequest.getNome_produto());
			record.setDesc_produto(produtoRequest.getDesc_produto());
			record.setPreco_produto(produtoRequest.getPreco_produto());
			
			
			String dataatual = ConverteData.converter();
			record.setDt_atualizacao(dataatual);

			Produto updated = produtoRepository.save(record);

			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
		
	}

	@DeleteMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable String id) {

		return produtoRepository.findById(id).map(record -> {
			produtoRepository.deleteById(id);

			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
