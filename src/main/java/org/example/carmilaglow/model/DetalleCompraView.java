package org.example.carmilaglow.model;

public class DetalleCompraView {
    private Producto producto;
    private int cantidad;
    private int precioUnitario;
    private int subtotal;

    public DetalleCompraView(
            Producto producto,
            int cantidad,
            int precioUnitario) {

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;

        this.subtotal = cantidad * precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public int getSubtotal() {
        return subtotal;
    }

    private void calcularSubtotal() {
        this.subtotal = cantidad * precioUnitario;
    }
}
