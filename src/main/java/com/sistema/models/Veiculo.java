package com.sistema.models;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "veiculo")
public class Veiculo implements Serializable {
	private static final long serialVersionUID = -8907691475155383682L;

	@Id
	private String id;

	@NotBlank
	@Size(max = 50)
	private String fabricante;

	@NotBlank
	@Size(max = 100)
	private String modelo;

	@NotBlank
	@Size(max = 1)
	private String documento;

	@NotBlank
	private String ano;

	private String placa;

	private String status;

	public Veiculo() {
	}

	public Veiculo(String fabricante, String modelo, String documento, String ano, String placa, String status) {
		super();
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.documento = documento;
		this.ano = ano;
		this.placa = placa;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}