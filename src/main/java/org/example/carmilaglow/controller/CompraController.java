package org.example.carmilaglow.controller;


import org.example.carmilaglow.dao.ProductoDAO;
import org.example.carmilaglow.dao.ProveedorDAO;
import org.example.carmilaglow.model.DetalleCompraView;
import org.example.carmilaglow.model.Producto;
import org.example.carmilaglow.model.Proveedor;
import org.example.carmilaglow.util.FormatoPrecio;

import org.example.carmilaglow.dao.CompraDAO;
import org.example.carmilaglow.dao.DetalleCompraDAO;
import org.example.carmilaglow.dao.MovimientoDAO;
import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Compra;
import org.example.carmilaglow.model.DetalleCompra;

import java.sql.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.cell.PropertyValueFactory;

public class CompraController {
    @FXML
    private ComboBox<Proveedor> cmbProveedor;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtObservacion;

    @FXML
    private ComboBox<Producto> cmbProducto;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TableView<DetalleCompraView> tablaDetalles;

    @FXML
    private TableColumn<DetalleCompraView, Producto> colProducto;

    @FXML
    private TableColumn<DetalleCompraView, Integer> colCantidad;

    @FXML
    private TableColumn<DetalleCompraView, Integer> colPrecioUnitario;

    @FXML
    private TableColumn<DetalleCompraView, Integer> colSubtotal;

    @FXML
    private Label lblTotal;

    private final ProveedorDAO proveedorDAO =
            new ProveedorDAO();

    private final ProductoDAO productoDAO =
            new ProductoDAO();

    private final ObservableList<DetalleCompraView> detalles =
            FXCollections.observableArrayList();

    private final CompraDAO compraDAO =
            new CompraDAO();

    private final DetalleCompraDAO detalleCompraDAO =
            new DetalleCompraDAO();

    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    @FXML
    public void initialize() {

        configurarColumnas();

        cargarProveedores();

        cargarProductos();

        dpFecha.setValue(
                java.time.LocalDate.now()
        );

        tablaDetalles.setItems(detalles);

        cmbProducto.setOnAction(event ->
                cargarPrecioProducto()
        );

        lblTotal.setText(
                FormatoPrecio.formatear(0)
        );
    }

    private void configurarColumnas() {

        colProducto.setCellValueFactory(
                new PropertyValueFactory<>("producto")
        );

        colProducto.setCellFactory(column ->
                new TableCell<DetalleCompraView, Producto>() {

                    @Override
                    protected void updateItem(
                            Producto producto,
                            boolean empty) {

                        super.updateItem(
                                producto,
                                empty
                        );

                        if (empty || producto == null) {

                            setText(null);

                        } else {

                            setText(
                                    producto.getNombre()
                            );
                        }
                    }
                }
        );

        colCantidad.setCellValueFactory(
                new PropertyValueFactory<>("cantidad")
        );

        colPrecioUnitario.setCellValueFactory(
                new PropertyValueFactory<>("precioUnitario")
        );

        colPrecioUnitario.setCellFactory(column ->
                new TableCell<DetalleCompraView, Integer>() {

                    @Override
                    protected void updateItem(
                            Integer precio,
                            boolean empty) {

                        super.updateItem(
                                precio,
                                empty
                        );

                        if (empty || precio == null) {

                            setText(null);

                        } else {

                            setText(
                                    FormatoPrecio.formatear(
                                            precio
                                    )
                            );
                        }
                    }
                }
        );

        colSubtotal.setCellValueFactory(
                new PropertyValueFactory<>("subtotal")
        );

        colSubtotal.setCellFactory(column ->
                new TableCell<DetalleCompraView, Integer>() {

                    @Override
                    protected void updateItem(
                            Integer subtotal,
                            boolean empty) {

                        super.updateItem(
                                subtotal,
                                empty
                        );

                        if (empty || subtotal == null) {

                            setText(null);

                        } else {

                            setText(
                                    FormatoPrecio.formatear(
                                            subtotal
                                    )
                            );
                        }
                    }
                }
        );
    }

    private void cargarProveedores() {

        ObservableList<Proveedor> proveedores =
                FXCollections.observableArrayList(
                        proveedorDAO.listar()
                );

        cmbProveedor.setItems(proveedores);
    }

    private void cargarProductos() {

        ObservableList<Producto> productos =
                FXCollections.observableArrayList(
                        productoDAO.listar()
                );

        cmbProducto.setItems(productos);
    }

    private void cargarPrecioProducto() {

        Producto producto =
                cmbProducto.getValue();

        if (producto == null) {
            return;
        }

        txtPrecioUnitario.setText(
                FormatoPrecio.formatear(
                        producto.getPrecioCosto()
                )
        );
    }

    @FXML
    private void agregarProducto() {

        Producto producto =
                cmbProducto.getValue();

        if (producto == null) {

            mostrarAlerta(
                    "Producto requerido",
                    "Selecciona un producto."
            );

            return;
        }

        if (txtCantidad.getText().isBlank()) {

            mostrarAlerta(
                    "Cantidad requerida",
                    "Ingresa la cantidad."
            );

            return;
        }

        int cantidad;

        try {

            cantidad =
                    Integer.parseInt(
                            txtCantidad.getText().trim()
                    );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    "Cantidad inválida",
                    "La cantidad debe ser un número entero."
            );

            return;
        }

        if (cantidad <= 0) {

            mostrarAlerta(
                    "Cantidad inválida",
                    "La cantidad debe ser mayor que cero."
            );

            return;
        }

        if (!FormatoPrecio.esPrecioValido(
                txtPrecioUnitario.getText())) {

            mostrarAlerta(
                    "Precio inválido",
                    "Ingresa un precio válido."
            );

            return;
        }

        int precioUnitario;

        try {

            precioUnitario =
                    FormatoPrecio.convertirACentavos(
                            txtPrecioUnitario.getText()
                    );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    "Precio inválido",
                    "Ingresa un precio válido."
            );

            return;
        }

        DetalleCompraView detalle =
                new DetalleCompraView(
                        producto,
                        cantidad,
                        precioUnitario
                );

        detalles.add(detalle);

        actualizarTotal();

        cmbProducto.getSelectionModel()
                .clearSelection();

        txtCantidad.clear();

        txtPrecioUnitario.clear();
    }

    private void actualizarTotal() {

        int total = 0;

        for (DetalleCompraView detalle : detalles) {

            total += detalle.getSubtotal();
        }

        lblTotal.setText(
                FormatoPrecio.formatear(total)
        );
    }

    @FXML
    private void registrarCompra() {

        // 1. Validar proveedor
        Proveedor proveedor =
                cmbProveedor.getValue();

        if (proveedor == null) {

            mostrarAlerta(
                    "Proveedor requerido",
                    "Selecciona un proveedor."
            );

            return;
        }

        // 2. Validar fecha
        if (dpFecha.getValue() == null) {

            mostrarAlerta(
                    "Fecha requerida",
                    "Selecciona una fecha."
            );

            return;
        }

        // 3. Validar que exista al menos un producto
        if (detalles.isEmpty()) {

            mostrarAlerta(
                    "Compra vacía",
                    "Agrega al menos un producto."
            );

            return;
        }

        // 4. Calcular total
        int total = 0;

        for (DetalleCompraView detalle : detalles) {

            total += detalle.getSubtotal();
        }

        String fecha =
                dpFecha.getValue().toString();

        String observacion =
                txtObservacion.getText().trim();

        Connection conexion = null;

        try {

            // 5. Abrir conexión
            conexion = Conexion.conectar();

            if (conexion == null) {

                mostrarAlerta(
                        "Error",
                        "No se pudo conectar con la base de datos."
                );

                return;
            }

            // 6. Iniciar transacción
            conexion.setAutoCommit(false);

            // 7. Crear compra
            Compra compra =
                    new Compra(
                            proveedor.getId(),
                            fecha,
                            total,
                            observacion
                    );

            int compraId =
                    compraDAO.insertar(
                            conexion,
                            compra
                    );

            if (compraId == -1) {

                throw new Exception(
                        "No se pudo registrar la compra."
                );
            }

            // 8. Registrar detalles
            for (DetalleCompraView detalleView : detalles) {

                DetalleCompra detalle =
                        new DetalleCompra(
                                compraId,
                                detalleView.getProducto().getId(),
                                detalleView.getCantidad(),
                                detalleView.getPrecioUnitario(),
                                detalleView.getSubtotal()
                        );

                boolean detalleInsertado =
                        detalleCompraDAO.insertar(
                                conexion,
                                detalle
                        );

                if (!detalleInsertado) {

                    throw new Exception(
                            "No se pudo registrar un detalle de compra."
                    );
                }

                // 9. Aumentar stock
                boolean stockActualizado =
                        productoDAO.aumentarStock(
                                conexion,
                                detalleView.getProducto().getId(),
                                detalleView.getCantidad()
                        );

                if (!stockActualizado) {

                    throw new Exception(
                            "No se pudo actualizar el stock."
                    );
                }

                // 10. Registrar movimiento
                boolean movimientoInsertado =
                        movimientoDAO.insertar(
                                conexion,
                                detalleView.getProducto().getId(),
                                "ENTRADA",
                                detalleView.getCantidad(),
                                fecha,
                                "Compra #" + compraId
                        );

                if (!movimientoInsertado) {

                    throw new Exception(
                            "No se pudo registrar el movimiento."
                    );
                }
            }

            // 11. Confirmar toda la operación
            conexion.commit();

            mostrarAlerta(
                    "Compra registrada",
                    "La compra #" + compraId +
                            " se registró correctamente."
            );

            // 12. Limpiar formulario
            detalles.clear();

            cmbProveedor.getSelectionModel()
                    .clearSelection();

            cmbProducto.getSelectionModel()
                    .clearSelection();

            txtCantidad.clear();

            txtPrecioUnitario.clear();

            txtObservacion.clear();

            dpFecha.setValue(
                    java.time.LocalDate.now()
            );

            actualizarTotal();

        } catch (Exception e) {

            // 13. Si ocurre cualquier error,
            // deshacer toda la operación
            try {

                if (conexion != null) {
                    conexion.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }

            mostrarAlerta(
                    "Error al registrar compra",
                    "La compra no pudo registrarse.\n\n"
                            + e.getMessage()
            );

            e.printStackTrace();

        } finally {

            // 14. Cerrar conexión
            try {

                if (conexion != null) {

                    conexion.setAutoCommit(true);
                    conexion.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private void mostrarAlerta(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
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
                    (Stage) tablaDetalles
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "CarMila Glow"
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}