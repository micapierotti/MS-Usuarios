package com.dan.pgm.danmsusuarios.domain;

import javax.persistence.*;

@Entity
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String mail;
	@OneToOne
	@JoinColumn(name = "USUARIO_ID")
	private Usuario user;

	public Empleado(Integer id, String nombre, String mail, Usuario user) {
		this.id = id;
		this.nombre = nombre;
		this.mail = mail;
		this.user = user;
	}
	public Empleado () {

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
