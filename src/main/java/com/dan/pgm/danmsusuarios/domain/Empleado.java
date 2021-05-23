package com.dan.pgm.danmsusuarios.domain;

import javax.persistence.*;

@Entity
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String mail;
	@OneToOne
	@JoinColumn(name = "USUARIO_ID")
	private Usuario user;
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
	
	
}
