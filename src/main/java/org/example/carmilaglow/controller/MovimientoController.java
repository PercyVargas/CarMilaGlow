package org.example.carmilaglow.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.dao.MovimientoDAO;
import org.example.carmilaglow.dao.ProductoDAO;
import org.example.carmilaglow.model.Movimiento;
import org.example.carmilaglow.model.Producto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.ComboBox;

import java.util.HashMap;
import java.util.Map;

public class MovimientoController {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TableView<Movimiento> tablaMovimientos;

    @FXML
    private TableColumn<Movimiento, String> colProducto;

    @FXML
    private TableColumn<Movimiento, String> colTipo;

    @FXML
    private TableColumn<Movimiento, Integer> colCantidad;

    @FXML
    private TableColumn<Movimiento, String> colFecha;

    @FXML
    private TableColumn<Movimiento, String> colObservacion;

    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    private final ProductoDAO productoDAO =
            new ProductoDAO();

    private final ObservableList<Movimiento> movimientos =
            FXCollections.observableArrayList();

    private final Map<Integer, String> nombresProductos =
            new HashMap<>();

    @FXML
    public void initialize() {

        configurarColumnas();

        cargarProductos();

        cargarMovimientos();

        cmbTipo.setOnAction(event ->
                filtrarMovimientos()
        );
    }

    private void configurarColumnas() {

        colProducto.setCellValueFactory(
                movimiento -> {

                    String nombre =
                            nombresProductos.get(
                                    movimiento.getValue().getProductoId()
                            );

                    return new javafx.beans.property.SimpleStringProperty(
                            nombre != null
                                    ? nombre
                                    : "Producto #" +
                                    movimiento.getValue().getProductoId()
                    );
                }
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );
        colTipo.setCellFactory(column ->
                new TableCell<Movimiento, String>() {

                    @Override
                    protected void updateItem(
                            String tipo,
                            boolean empty) {

                        super.updateItem(tipo, empty);

                        if (empty || tipo == null) {

                            setText(null);
                            setStyle("");

                        } else {

                            setText(tipo);

                            if ("ENTRADA".equals(tipo)) {

                                setStyle(
                                        "-fx-text-fill: green;" +
                                                "-fx-font-weight: bold;"
                                );

                            } else if ("SALIDA".equals(tipo)) {

                                setStyle(
                                        "-fx-text-fill: red;" +
                                                "-fx-font-weight: bold;"
                                );

                            } else {

                                setStyle("");
                            }
                        }
                    }
                }
        );

        colCantidad.setCellValueFactory(
                new PropertyValueFactory<>("cantidad")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        colObservacion.setCellValueFactory(
                new PropertyValueFactory<>("observacion")
        );

        tablaMovimientos.setItems(movimientos);
    }

    private void cargarProductos() {

        for (Producto producto : productoDAO.listar()) {

            nombresProductos.put(
                    producto.getId(),
                    producto.getNombre()
            );
        }
    }

    private void cargarMovimientos() {

        movimientos.setAll(
                movimientoDAO.listar()
        );

    }

    private void filtrarMovimientos() {

        String tipoSeleccionado =
                cmbTipo.getValue();

        if (tipoSeleccionado == null ||
                tipoSeleccionado.equals("Todos")) {

            movimientos.setAll(
                    movimientoDAO.listar()
            );

            return;
        }

        ObservableList<Movimiento> filtrados =
                FXCollections.observableArrayList();

        for (Movimiento movimiento :
                movimientoDAO.listar()) {

            if (movimiento.getTipo()
                    .equals(tipoSeleccionado)) {

                filtrados.add(movimiento);
            }
        }

        movimientos.setAll(filtrados);
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
                    (Stage) tablaMovimientos
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
