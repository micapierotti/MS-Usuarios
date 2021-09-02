package com.dan.pgm.danmsusuarios.dtos;

import com.dan.pgm.danmsusuarios.domain.Obra;

import java.util.List;

public class ClienteDTO {
    private String razonSocial;
    private String cuit;
    private String mail;
    private Double maxCuentaCorriente;
    private String username;
    private String password;
    private List<Obra> obras;

    public ClienteDTO(String razonSocial, String cuit, String mail, Double maxCuentaCorriente, String username, String password, List<Obra> obras) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.mail = mail;
        this.maxCuentaCorriente = maxCuentaCorriente;
        this.username = username;
        this.password = password;
        this.obras = obras;
    }

    public ClienteDTO () {

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

    public List<Obra> getObras() {
        return obras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }
}
