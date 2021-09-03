package com.dan.pgm.danmsusuarios.domain;
import javax.persistence.*;

@Entity
public class Obra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String descripcion;
	private Float latitud;
	private Float longitud;
	private String direccion;
	private Integer superficie;
	@Enumerated(EnumType.STRING)
	private TipoObra tipo;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CLIENTE_ID")
	private Cliente cliente;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Float getLatitud() {
		return latitud;
	}
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}
	public Float getLongitud() {
		return longitud;
	}
	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Integer superficie) {
		this.superficie = superficie;
	}
	public TipoObra getTipo() {
		return tipo;
	}
	public void setTipo(TipoObra tipo) {
		this.tipo = tipo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Obra(Integer id, String descripcion, Float latitud, Float longitud, String direccion, Integer superficie, TipoObra tipo, Cliente cliente) {
		this.id = id;
		this.descripcion = descripcion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.superficie = superficie;
		this.tipo = tipo;
		this.cliente = cliente;
	}

	public Obra() {

	}
}
