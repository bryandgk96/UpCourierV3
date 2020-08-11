package com.example.upcourierv1.Modelos;

public class ServiciosRealizados {
    private int id;
    private String driver;
    private String origen;
    private String Destino;
    private String customer;
    private String estado;

    public ServiciosRealizados(){}

    public ServiciosRealizados(String driver, String origen, String destino, String customer, String estado) {
        this.driver = driver;
        this.origen = origen;
        Destino = destino;
        this.customer = customer;
        this.estado = estado;
    }

    public ServiciosRealizados(String driver, String origen, String destino, String customer) {
        this.driver = driver;
        this.origen = origen;
        Destino = destino;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
