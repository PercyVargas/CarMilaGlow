package org.example.carmilaglow.model;

public class Venta {

    private int id;
    private int clienteId;
    private String fecha;
    private int total;
    private String observacion;

    public Venta() {
    }

    public Venta(int clienteId, String fecha,
                 int total, String observacion) {

        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.observacion = observacion;
    }

    public Venta(int id, int clienteId,
                 String fecha, int total,
                 String observacion) {

        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.observacion = observacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
