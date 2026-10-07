package org.example.carmilaglow.model;

public class Movimiento {

    private int id;
    private int productoId;
    private String tipo;
    private int cantidad;
    private String fecha;
    private String observacion;

    public Movimiento() {
    }

    public Movimiento(
            int productoId,
            String tipo,
            int cantidad,
            String fecha,
            String observacion) {

        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public Movimiento(
            int id,
            int productoId,
            String tipo,
            int cantidad,
            String fecha,
            String observacion) {

        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
