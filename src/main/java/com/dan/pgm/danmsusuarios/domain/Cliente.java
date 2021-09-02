package com.dan.pgm.danmsusuarios.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String razonSocial;
    private String cuit;
    private String mail;
    private Double maxCuentaCorriente;
    private Boolean habilitadoOnline;
    @OneToOne
	@JoinColumn(name = "USUARIO_ID")
    private Usuario user;
	@Column(name = "OBRA_ID")
	@OneToMany(mappedBy = "cliente", targetEntity = Obra.class, cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Obra> obras;
    private Date fechaBaja;

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Double getMaxCuentaCorriente() {
		return maxCuentaCorriente;
	}
	public void setMaxCuentaCorriente(Double maxCuentaCorriente) {
		this.maxCuentaCorriente = maxCuentaCorriente;
	}
	public Boolean getHabilitadoOnline() {
		return habilitadoOnline;
	}
	public void setHabilitadoOnline(Boolean habilitadoOnline) {
		this.habilitadoOnline = habilitadoOnline;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public List<Obra> getObras() {
		return obras;
	}
	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}
	public Date getFechaBaja() { return fechaBaja; }
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Cliente(Integer id, String razonSocial, String cuit, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline, Usuario user, List<Obra> obras, Date fechaBaja) {
		this.id = id;
		this.razonSocial = razonSocial;
		this.cuit = cuit;
		this.mail = mail;
		this.maxCuentaCorriente = maxCuentaCorriente;
		this.habilitadoOnline = habilitadoOnline;
		this.user = user;
		this.obras = obras;
		this.fechaBaja = fechaBaja;
	}

	public Cliente() {

	}
}
