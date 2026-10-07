package org.example.carmilaglow.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.dao.ClienteDAO;
import org.example.carmilaglow.dao.ProductoDAO;
import org.example.carmilaglow.model.Cliente;
import org.example.carmilaglow.model.DetalleVentaView;
import org.example.carmilaglow.model.Producto;
import org.example.carmilaglow.util.FormatoPrecio;

import org.example.carmilaglow.dao.VentaDAO;
import org.example.carmilaglow.dao.DetalleVentaDAO;
import org.example.carmilaglow.dao.MovimientoDAO;
import org.example.carmilaglow.database.Conexion;
import org.example.carmilaglow.model.Venta;
import org.example.carmilaglow.model.DetalleVenta;

import java.sql.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

public class VentaController {

    @FXML
    private ComboBox<Cliente> cmbCliente;

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
    private TableView<DetalleVentaView> tablaDetalles;

    @FXML
    private TableColumn<DetalleVentaView, Producto> colProducto;

    @FXML
    private TableColumn<DetalleVentaView, Integer> colCantidad;

    @FXML
    private TableColumn<DetalleVentaView, Integer> colPrecioUnitario;

    @FXML
    private TableColumn<DetalleVentaView, Integer> colSubtotal;

    @FXML
    private Label lblTotal;

    private final ClienteDAO clienteDAO =
            new ClienteDAO();

    private final ProductoDAO productoDAO =
            new ProductoDAO();

    private final ObservableList<DetalleVentaView> detalles =
            FXCollections.observableArrayList();

    private final VentaDAO ventaDAO =
            new VentaDAO();

    private final DetalleVentaDAO detalleVentaDAO =
            new DetalleVentaDAO();

    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    @FXML
    public void initialize() {

        configurarColumnas();
        cargarClientes();
        cargarProductos();

        dpFecha.setValue(java.time.LocalDate.now());

        tablaDetalles.setItems(detalles);

        cmbProducto.setOnAction(event ->
                cargarPrecioProducto());

        lblTotal.setText(
                FormatoPrecio.formatear(0));
    }

    private void configurarColumnas() {

        colProducto.setCellValueFactory(
                new PropertyValueFactory<>("producto")
        );

        colProducto.setCellFactory(column ->
                new TableCell<DetalleVentaView, Producto>() {

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
                new TableCell<DetalleVentaView, Integer>() {

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
                new TableCell<DetalleVentaView, Integer>() {

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

    private void cargarClientes() {

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList(
                        clienteDAO.listar()
                );

        cmbCliente.setItems(clientes);
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
                        producto.getPrecioPublico()
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

        if (cantidad > producto.getStock()) {

            mostrarAlerta(
                    "Stock insuficiente",
                    "El producto no tiene suficiente stock."
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

        DetalleVentaView detalle =
                new DetalleVentaView(
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

        for (DetalleVentaView detalle : detalles) {

            total += detalle.getSubtotal();
        }

        lblTotal.setText(
                FormatoPrecio.formatear(total)
        );
    }

    @FXML
    private void registrarVenta() {

        Cliente cliente = cmbCliente.getValue();

        if (cliente == null) {

            mostrarAlerta(
                    "Cliente requerido",
                    "Selecciona un cliente."
            );

            return;
        }

        if (dpFecha.getValue() == null) {

            mostrarAlerta(
                    "Fecha requerida",
                    "Selecciona una fecha."
            );

            return;
        }

        if (detalles.isEmpty()) {

            mostrarAlerta(
                    "Venta vacía",
                    "Agrega al menos un producto."
            );

            return;
        }

        int total = 0;

        for (DetalleVentaView detalle : detalles) {
            total += detalle.getSubtotal();
        }

        String fecha =
                dpFecha.getValue().toString();

        String observacion =
                txtObservacion.getText().trim();

        Connection conexion = null;

        try {

            conexion = Conexion.conectar();

            if (conexion == null) {

                mostrarAlerta(
                        "Error",
                        "No se pudo conectar con la base de datos."
                );

                return;
            }

            conexion.setAutoCommit(false);

            Venta venta =
                    new Venta(
                            cliente.getId(),
                            fecha,
                            total,
                            observacion
                    );

            int ventaId =
                    ventaDAO.insertar(
                            conexion,
                            venta
                    );

            if (ventaId == -1) {

                throw new Exception(
                        "No se pudo registrar la venta."
                );
            }

            for (DetalleVentaView detalle : detalles) {

                Producto producto =
                        detalle.getProducto();

                DetalleVenta detalleVenta =
                        new DetalleVenta(
                                ventaId,
                                producto.getId(),
                                detalle.getCantidad(),
                                detalle.getPrecioUnitario(),
                                detalle.getSubtotal()
                        );

                boolean detalleInsertado =
                        detalleVentaDAO.insertar(
                                conexion,
                                detalleVenta
                        );

                if (!detalleInsertado) {

                    throw new Exception(
                            "No se pudo registrar el detalle de la venta."
                    );
                }

                boolean stockActualizado =
                        productoDAO.disminuirStock(
                                conexion,
                                producto.getId(),
                                detalle.getCantidad()
                        );

                if (!stockActualizado) {

                    throw new Exception(
                            "Stock insuficiente para el producto: "
                                    + producto.getNombre()
                    );
                }

                boolean movimientoRegistrado =
                        movimientoDAO.insertar(
                                conexion,
                                producto.getId(),
                                "SALIDA",
                                detalle.getCantidad(),
                                fecha,
                                "Venta #" + ventaId
                        );

                if (!movimientoRegistrado) {

                    throw new Exception(
                            "No se pudo registrar el movimiento."
                    );
                }
            }

            conexion.commit();

            mostrarAlerta(
                    "Venta registrada",
                    "La venta #" + ventaId +
                            " se registró correctamente."
            );

            detalles.clear();
            actualizarTotal();

            cmbCliente.getSelectionModel()
                    .clearSelection();

            cmbProducto.getSelectionModel()
                    .clearSelection();

            txtCantidad.clear();
            txtPrecioUnitario.clear();
            txtObservacion.clear();

        } catch (Exception e) {

            try {

                if (conexion != null) {
                    conexion.rollback();
                }

            } catch (Exception rollbackException) {

                rollbackException.printStackTrace();
            }

            mostrarAlerta(
                    "Error",
                    "No se pudo registrar la venta.\n"
                            + e.getMessage()
            );

            e.printStackTrace();

        } finally {

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
