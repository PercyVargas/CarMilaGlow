package org.example.carmilaglow.model;

public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private String marca;
    private String descripcion;
    private int categoriaId;
    private int stock;
    private int stockMinimo;
    private int precioCosto;
    private int precioPublico;

    public Producto() {
    }

    public Producto(String codigo, String nombre, String descripcion, String marca,
                    int categoriaId, int stock, int stockMinimo,
                    int precioCosto, int precioPublico) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.categoriaId = categoriaId;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.precioCosto = precioCosto;
        this.precioPublico = precioPublico;
    }

    public Producto(int id, String codigo, String nombre, String descripcion, String marca,
                    int categoriaId, int stock, int stockMinimo, int precioCosto, int precioPublico) {

        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = marca;
        this.marca = descripcion;
        this.categoriaId = categoriaId;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.precioCosto = precioCosto;
        this.precioPublico = precioPublico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(int precioCosto) {
        this.precioCosto = precioCosto;
    }

    public int getPrecioPublico() {
        return precioPublico;
    }

    public void setPrecioPublico(int precioPublico) {
        this.precioPublico = precioPublico;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
