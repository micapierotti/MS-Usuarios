package com.dan.pgm.danmsusuarios.dtos;

import com.dan.pgm.danmsusuarios.domain.Empleado;

public class EmpleadoDTO {

    private String nombre;
    private String mail;
    private String user;
    private String password;

    public EmpleadoDTO(String nombre, String mail, String user, String password) {
        this.nombre = nombre;
        this.mail = mail;
        this.user = user;
        this.password = password;
    }

    public EmpleadoDTO() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
