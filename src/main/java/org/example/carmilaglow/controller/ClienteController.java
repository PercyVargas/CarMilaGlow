package org.example.carmilaglow.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carmilaglow.dao.ClienteDAO;
import org.example.carmilaglow.model.Cliente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colDocumento;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    private final ClienteDAO clienteDAO = new ClienteDAO();

    private Cliente clienteSeleccionado;

    @FXML
    public void initialize() {

        configurarColumnas();

        cargarClientes();

        tablaClientes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        seleccionarCliente();
                    }
                });
    }

    private void configurarColumnas() {

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colDocumento.setCellValueFactory(
                new PropertyValueFactory<>("documento")
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

    private void cargarClientes() {

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList(
                        clienteDAO.listar()
                );

        tablaClientes.setItems(clientes);
    }

    private void seleccionarCliente() {

        clienteSeleccionado =
                tablaClientes.getSelectionModel()
                        .getSelectedItem();

        if (clienteSeleccionado == null) {
            return;
        }

        txtNombre.setText(
                clienteSeleccionado.getNombre()
        );

        txtDocumento.setText(
                clienteSeleccionado.getDocumento()
        );

        txtTelefono.setText(
                clienteSeleccionado.getTelefono()
        );

        txtEmail.setText(
                clienteSeleccionado.getEmail()
        );

        txtDireccion.setText(
                clienteSeleccionado.getDireccion()
        );
    }

    @FXML
    private void agregarCliente() {

        String nombre = txtNombre.getText().trim();
        String documento = txtDocumento.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "El nombre del cliente es obligatorio."
            );

            return;
        }

        if (documento.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "El documento del cliente es obligatorio."
            );

            return;
        }

        Cliente cliente = new Cliente(
                nombre,
                documento,
                telefono,
                email,
                direccion
        );

        if (clienteDAO.insertar(cliente)) {

            mostrarAlerta(
                    "Cliente agregado",
                    "El cliente se agregó correctamente."
            );

            cargarClientes();
            limpiarFormulario();

        } else {

            mostrarAlerta(
                    "Error",
                    "No se pudo agregar el cliente."
            );
        }
    }

    @FXML
    private void modificarCliente() {

        if (clienteSeleccionado == null) {

            mostrarAlerta(
                    "Selección requerida",
                    "Selecciona un cliente para modificar."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();
        String documento = txtDocumento.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "El nombre del cliente es obligatorio."
            );

            return;
        }

        if (documento.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "El documento del cliente es obligatorio."
            );

            return;
        }

        clienteSeleccionado.setNombre(nombre);
        clienteSeleccionado.setDocumento(documento);
        clienteSeleccionado.setTelefono(telefono);
        clienteSeleccionado.setEmail(email);
        clienteSeleccionado.setDireccion(direccion);

        if (clienteDAO.actualizar(clienteSeleccionado)) {

            mostrarAlerta(
                    "Cliente modificado",
                    "El cliente se modificó correctamente."
            );

            cargarClientes();
            limpiarFormulario();

        } else {

            mostrarAlerta(
                    "Error",
                    "No se pudo modificar el cliente."
            );
        }
    }

    @FXML
    private void eliminarCliente() {

        if (clienteSeleccionado == null) {

            mostrarAlerta(
                    "Selección requerida",
                    "Selecciona un cliente para eliminar."
            );

            return;
        }

        Alert confirmacion =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Eliminar cliente");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Seguro que deseas eliminar este cliente?"
        );

        confirmacion.showAndWait()
                .ifPresent(respuesta -> {

                    if (respuesta ==
                            javafx.scene.control.ButtonType.OK) {

                        if (clienteDAO.eliminar(
                                clienteSeleccionado.getId())) {

                            mostrarAlerta(
                                    "Cliente eliminado",
                                    "El cliente se eliminó correctamente."
                            );

                            cargarClientes();
                            limpiarFormulario();

                        } else {

                            mostrarAlerta(
                                    "Error",
                                    "No se pudo eliminar el cliente."
                            );
                        }
                    }
                });
    }

    private void limpiarFormulario() {

        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();

        clienteSeleccionado = null;

        tablaClientes.getSelectionModel()
                .clearSelection();
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
                    (Stage) tablaClientes
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
