package org.example.carmilaglow.model;

public class Compra {
    private int id;
    private int proveedorId;
    private String fecha;
    private int total;
    private String observacion;

    public Compra() {
    }

    public Compra(int proveedorId, String fecha,
                  int total, String observacion) {

        this.proveedorId = proveedorId;
        this.fecha = fecha;
        this.total = total;
        this.observacion = observacion;
    }

    public Compra(int id, int proveedorId,
                  String fecha, int total,
                  String observacion) {

        this.id = id;
        this.proveedorId = proveedorId;
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

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
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
