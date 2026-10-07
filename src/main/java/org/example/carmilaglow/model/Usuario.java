package org.example.carmilaglow.model;

public class Usuario {

    private int id;
    private String nombreUsuario;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(
            String nombreUsuario,
            String password,
            String rol) {

        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(
            int id,
            String nombreUsuario,
            String password,
            String rol) {

        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
