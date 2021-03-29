package com.sistema.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produto")
public class Produto {
	@Id
	private String id;

	@NotBlank
	@Size(max = 100)
	private String nome_produto;

	@NotBlank
	private String desc_produto;

	@NotBlank
	private double preco_produto;

	private String dt_atualizacao;

	public Produto() {
	}

	public Produto(@NotBlank @Size(max = 100) String nome_produto, @NotBlank String desc_produto,
			@NotBlank double preco_produto) {
		super();
		this.nome_produto = nome_produto;
		this.desc_produto = desc_produto;
		this.preco_produto = preco_produto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome_produto() {
		return nome_produto;
	}

	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}

	public String getDesc_produto() {
		return desc_produto;
	}

	public void setDesc_produto(String desc_produto) {
		this.desc_produto = desc_produto;
	}

	public double getPreco_produto() {
		return preco_produto;
	}

	public void setPreco_produto(double preco_produto) {
		this.preco_produto = preco_produto;
	}

	public String getDt_atualizacao() {
		return dt_atualizacao;
	}

	public void setDt_atualizacao(String dt_atualizacao) {
		this.dt_atualizacao = dt_atualizacao;
	}

}