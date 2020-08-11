package com.example.upcourierv1.Modelos;
public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contra;
    private String tipoUsuario;

    public Usuario(){}

    public Usuario(String correo, String contra, String tipoUsuario) {
        this.correo = correo;
        this.contra = contra;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String nombre, String apellido, String correo, String contra, String tipoUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contra = contra;
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isNull(){
        if(nombre.isEmpty() && apellido.isEmpty() && correo.isEmpty() && contra.isEmpty() && tipoUsuario.isEmpty())
            return true;
        else
            return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
