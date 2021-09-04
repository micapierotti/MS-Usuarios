package com.dan.pgm.danmsusuarios.dtos;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Obra;

import java.util.ArrayList;
import java.util.List;

public class MostrarClienteDTO {

    private Integer id;
    private String razonSocial;
    private String cuit;
    private String mail;
    private Double maxCuentaCorriente;
    private String username;
    private String password;
    private List<ObraDTO> obras;

    public MostrarClienteDTO(Integer id, String razonSocial, String cuit, String mail, Double maxCuentaCorriente, String username, String password, List<ObraDTO> obras) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.mail = mail;
        this.maxCuentaCorriente = maxCuentaCorriente;
        this.username = username;
        this.password = password;
        this.obras = obras;
    }

    public MostrarClienteDTO () {

    }

    public MostrarClienteDTO (Cliente c) {
        this.id = c.getId();
        this.razonSocial = c.getRazonSocial();
        this.cuit = c.getCuit();
        this.mail = c.getMail();
        this.maxCuentaCorriente = c.getMaxCuentaCorriente();
        this.username = c.getUser().getUser();
        this.password = c.getUser().getPassword();
        this.obras = new ArrayList<ObraDTO>();
        c.getObras().stream().forEach(obra -> this.obras.add(new ObraDTO(obra)));
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ObraDTO> getObras() {
        return obras;
    }

    public void setObras(List<ObraDTO> obras) {
        this.obras = obras;
    }
}
