package org.example.carmilaglow.controller;

import org.example.carmilaglow.model.Categoria;
import org.example.carmilaglow.model.Producto;
import org.example.carmilaglow.dao.ProductoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.carmilaglow.dao.CategoriaDAO;
import org.example.carmilaglow.util.FormatoPrecio;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductoController {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colDescripcion;

    @FXML
    private TableColumn<Producto, String> colMarca;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private TableColumn<Producto, Integer> colStMin;

    @FXML
    private TableColumn<Producto, Integer> colPrecioCosto;

    @FXML
    private TableColumn<Producto, Integer> colPrecioPublico;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtMarca;

    @FXML
    private ComboBox<Categoria> cmbCategoria;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtStockMinimo;

    @FXML
    private TextField txtPrecioCosto;

    @FXML
    private TextField txtPrecioPublico;

    @FXML
    private Button btnAgregar;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @FXML
    public void initialize() {

        configurarColumnas();
        cargarCategorias();
        cargarProductos();

        tablaProductos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        seleccionarProducto();
                    }
                });
    }

    private void configurarColumnas() {

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );

        colMarca.setCellValueFactory(
                new PropertyValueFactory<>("marca")
        );

        colStock.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );

        colStMin.setCellValueFactory(
                new PropertyValueFactory<>("stockMinimo")
        );

        colPrecioCosto.setCellValueFactory(
                new PropertyValueFactory<>("precioCosto")
        );

        colPrecioCosto.setCellFactory(column -> new TableCell<Producto, Integer>() {

            @Override
            protected void updateItem(Integer precio, boolean empty) {
                super.updateItem(precio, empty);

                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText(FormatoPrecio.formatear(precio));
                }
            }
        });

        colPrecioPublico.setCellValueFactory(
                new PropertyValueFactory<>("precioPublico")
        );

        colPrecioPublico.setCellFactory(column -> new TableCell<Producto, Integer>() {

            @Override
            protected void updateItem(Integer precio, boolean empty) {
                super.updateItem(precio, empty);

                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText(FormatoPrecio.formatear(precio));
                }
            }
        });
    }

    private void cargarCategorias() {

        cmbCategoria.getItems().setAll(
                categoriaDAO.listar()
        );

    }

    private void cargarProductos() {

        tablaProductos.getItems().setAll(
                productoDAO.listar()
        );
    }

    @FXML
    private void agregarProducto() {

        try {

            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String marca = txtMarca.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty()) {

                mostrarAlerta(
                        "Datos incompletos",
                        "Código y nombre son obligatorios."
                );

                return;
            }

            Categoria categoriaSeleccionada = cmbCategoria.getValue();

            if (categoriaSeleccionada == null) {

                mostrarAlerta(
                        "Categoría",
                        "Debes seleccionar una categoría."
                );

                return;
            }

            if (!FormatoPrecio.esPrecioValido(txtPrecioCosto.getText())
                    || !FormatoPrecio.esPrecioValido(txtPrecioPublico.getText())) {

                mostrarAlerta(
                        "Precio inválido",
                        "Los precios deben ser números válidos y no pueden ser negativos."
                );

                return;
            }

            int categoriaId = categoriaSeleccionada.getId();

            int stock = Integer.parseInt(txtStock.getText());
            int stockMinimo = Integer.parseInt(txtStockMinimo.getText());
            int precioCosto =
                    FormatoPrecio.convertirACentavos(txtPrecioCosto.getText());

            int precioPublico =
                    FormatoPrecio.convertirACentavos(txtPrecioPublico.getText());

            Producto producto = new Producto(
                    codigo,
                    nombre,
                    descripcion,
                    marca,
                    categoriaId,
                    stock,
                    stockMinimo,
                    precioCosto,
                    precioPublico
            );

            boolean insertado = productoDAO.insertar(producto);

            if (insertado) {

                mostrarAlerta(
                        "Producto registrado",
                        "El producto se agregó correctamente."
                );

                limpiarFormulario();
                cargarProductos();

            } else {
                mostrarAlerta(
                        "Error",
                        "No se pudo registrar el producto."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    "Stock inválidos",
                    "Stock y Stock mínimo deben ser números."
            );

        }
    }

    private void limpiarFormulario() {

        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtMarca.clear();
        txtStock.clear();
        txtStockMinimo.clear();
        txtPrecioCosto.clear();
        txtPrecioPublico.clear();

        cmbCategoria.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    // METODOS SELECCIONAR PRODUCTO
    private Producto productoSeleccionado;

    private void seleccionarProducto() {

        productoSeleccionado =
                tablaProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            return;
        }

        txtCodigo.setText(productoSeleccionado.getCodigo());
        txtNombre.setText(productoSeleccionado.getNombre());
        txtDescripcion.setText(productoSeleccionado.getDescripcion());
        txtMarca.setText(productoSeleccionado.getMarca());

        // Seleccionar la categoría correspondiente
        for (Categoria categoria : cmbCategoria.getItems()) {

            if (categoria.getId() == productoSeleccionado.getCategoriaId()) {

                cmbCategoria.getSelectionModel().select(categoria);
                break;
            }
        }

        txtStock.setText(
                String.valueOf(productoSeleccionado.getStock())
        );

        txtStockMinimo.setText(
                String.valueOf(productoSeleccionado.getStockMinimo())
        );

        txtPrecioCosto.setText(
                FormatoPrecio.formatear(productoSeleccionado.getPrecioCosto())
        );

        txtPrecioPublico.setText(
                FormatoPrecio.formatear(productoSeleccionado.getPrecioPublico())
        );
    }

    @FXML
    private void modificarProducto() {

        if (productoSeleccionado == null) {

            mostrarAlerta(
                    "Producto",
                    "Debes seleccionar un producto de la tabla."
            );

            return;
        }

        try {

            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String marca = txtMarca.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty()) {

                mostrarAlerta(
                        "Datos incompletos",
                        "Código y nombre son obligatorios."
                );

                return;
            }

            Categoria categoriaSeleccionada = cmbCategoria.getValue();

            if (categoriaSeleccionada == null) {

                mostrarAlerta(
                        "Categoría",
                        "Debes seleccionar una categoría."
                );

                return;
            }

            if (!FormatoPrecio.esPrecioValido(txtPrecioCosto.getText())
                    || !FormatoPrecio.esPrecioValido(txtPrecioPublico.getText())) {

                mostrarAlerta(
                        "Precio inválido",
                        "Los precios deben ser números válidos y no pueden ser negativos."
                );

                return;
            }

            int stock = Integer.parseInt(txtStock.getText());
            int stockMinimo = Integer.parseInt(txtStockMinimo.getText());
            int precioCosto = FormatoPrecio.convertirACentavos(txtPrecioCosto.getText());
            int precioPublico = FormatoPrecio.convertirACentavos(txtPrecioPublico.getText());

            productoSeleccionado.setCodigo(codigo);
            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setDescripcion(descripcion);
            productoSeleccionado.setMarca(marca);
            productoSeleccionado.setCategoriaId(
                    categoriaSeleccionada.getId()
            );
            productoSeleccionado.setStock(stock);
            productoSeleccionado.setStockMinimo(stockMinimo);
            productoSeleccionado.setPrecioCosto(precioCosto);
            productoSeleccionado.setPrecioPublico(precioPublico);

            boolean actualizado =
                    productoDAO.actualizar(productoSeleccionado);

            if (actualizado) {

                mostrarAlerta(
                        "Producto actualizado",
                        "El producto se modificó correctamente."
                );

                cargarProductos();
                limpiarFormulario();
                productoSeleccionado = null;

            } else {

                mostrarAlerta(
                        "Error",
                        "No se pudo actualizar el producto."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    "Stock Inválido",
                    "Stock y Stock mínimo deben ser números."
            );
        }
    }

    // METODO ELIMINAR PRODUCTO
    @FXML
    private void eliminarProducto() {

        if (productoSeleccionado == null) {

            mostrarAlerta(
                    "Producto",
                    "Debes seleccionar un producto de la tabla."
            );

            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Eliminar producto");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Estás seguro de eliminar el producto \""
                        + productoSeleccionado.getNombre()
                        + "\"?"
        );

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            boolean eliminado =
                    productoDAO.eliminar(productoSeleccionado.getId());

            if (eliminado) {

                mostrarAlerta(
                        "Producto eliminado",
                        "El producto se eliminó correctamente."
                );

                cargarProductos();
                limpiarFormulario();
                productoSeleccionado = null;

            } else {

                mostrarAlerta(
                        "Error",
                        "No se pudo eliminar el producto."
                );
            }
        }
    }

    @FXML
    private void volverAlMenu() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/menu.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage =
                    (Stage) tablaProductos
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "Sistema de Inventarios"
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}