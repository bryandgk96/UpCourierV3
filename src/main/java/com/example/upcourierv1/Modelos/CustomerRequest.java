package com.example.upcourierv1.Modelos;

public class CustomerRequest {

    private int id;
    private String correo;
    private String latitud;
    private String longitud;
    private String estado;

    public CustomerRequest(){}

    public CustomerRequest(String correo, String latitud, String longitud, String estado) {
        this.correo = correo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    public CustomerRequest(int id, String correo, String latitud, String longitud, String estado) {
        this.id = id;
        this.correo = correo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
