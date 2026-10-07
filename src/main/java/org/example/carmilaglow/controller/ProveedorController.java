package org.example.carmilaglow.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.dao.ProveedorDAO;
import org.example.carmilaglow.model.Proveedor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



public class ProveedorController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TableView<Proveedor> tablaProveedores;

    @FXML
    private TableColumn<Proveedor, String> colNombre;

    @FXML
    private TableColumn<Proveedor, String> colTelefono;

    @FXML
    private TableColumn<Proveedor, String> colEmail;

    @FXML
    private TableColumn<Proveedor, String> colDireccion;


    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    private Proveedor proveedorSeleccionado;


    @FXML
    public void initialize() {

        configurarColumnas();
        cargarProveedores();

        tablaProveedores.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        seleccionarProveedor();
                    }
                });
    }


    private void configurarColumnas() {

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        colDireccion.setCellValueFactory(
                new PropertyValueFactory<>("direccion")
        );
    }


    private void cargarProveedores() {

        ObservableList<Proveedor> proveedores =
                FXCollections.observableArrayList(
                        proveedorDAO.listar()
                );

        tablaProveedores.setItems(proveedores);
    }


    private void seleccionarProveedor() {

        proveedorSeleccionado =
                tablaProveedores.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado == null) {
            return;
        }

        txtNombre.setText(
                proveedorSeleccionado.getNombre()
        );

        txtTelefono.setText(
                proveedorSeleccionado.getTelefono()
        );

        txtEmail.setText(
                proveedorSeleccionado.getEmail()
        );

        txtDireccion.setText(
                proveedorSeleccionado.getDireccion()
        );
    }


    @FXML
    private void agregarProveedor() {

        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "El nombre del proveedor es obligatorio."
            );

            return;
        }

        Proveedor proveedor = new Proveedor(
                nombre,
                telefono,
                email,
                direccion
        );

        boolean insertado =
                proveedorDAO.insertar(proveedor);

        if (insertado) {

            mostrarAlerta(
                    "Proveedor registrado",
                    "El proveedor se agregó correctamente."
            );

            limpiarFormulario();
            cargarProveedores();

        } else {

            mostrarAlerta(
                    "Error",
                    "No se pudo registrar el proveedor."
            );
        }
    }


    @FXML
    private void modificarProveedor() {

        if (proveedorSeleccionado == null) {

            mostrarAlerta(
                    "Proveedor",
                    "Debes seleccionar un proveedor."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "El nombre del proveedor es obligatorio."
            );

            return;
        }

        proveedorSeleccionado.setNombre(nombre);
        proveedorSeleccionado.setTelefono(telefono);
        proveedorSeleccionado.setEmail(email);
        proveedorSeleccionado.setDireccion(direccion);

        boolean actualizado =
                proveedorDAO.actualizar(proveedorSeleccionado);

        if (actualizado) {

            mostrarAlerta(
                    "Proveedor actualizado",
                    "Los datos se actualizaron correctamente."
            );

            limpiarFormulario();
            cargarProveedores();

        } else {

            mostrarAlerta(
                    "Error",
                    "No se pudo actualizar el proveedor."
            );
        }
    }


    @FXML
    private void eliminarProveedor() {

        if (proveedorSeleccionado == null) {

            mostrarAlerta(
                    "Proveedor",
                    "Debes seleccionar un proveedor."
            );

            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Eliminar proveedor");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Estás seguro de eliminar el proveedor \""
                        + proveedorSeleccionado.getNombre()
                        + "\"?"
        );

        if (confirmacion.showAndWait().orElse(null)
                == javafx.scene.control.ButtonType.OK) {

            boolean eliminado =
                    proveedorDAO.eliminar(
                            proveedorSeleccionado.getId()
                    );

            if (eliminado) {

                mostrarAlerta(
                        "Proveedor eliminado",
                        "El proveedor se eliminó correctamente."
                );

                limpiarFormulario();
                cargarProveedores();

            } else {

                mostrarAlerta(
                        "Error",
                        "No se pudo eliminar el proveedor."
                );
            }
        }
    }


    private void limpiarFormulario() {

        txtNombre.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();

        proveedorSeleccionado = null;

        tablaProveedores.getSelectionModel()
                .clearSelection();
    }


    private void mostrarAlerta(
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(
                Alert.AlertType.INFORMATION
        );

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
                    (Stage) tablaProveedores
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
