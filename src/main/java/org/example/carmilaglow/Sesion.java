package org.example.carmilaglow;

import org.example.carmilaglow.model.Usuario;

public class Sesion {

    private static Usuario usuarioActual;

    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean esAdmin() {

        return usuarioActual != null
                && "ADMIN".equals(
                usuarioActual.getRol()
        );
    }

    public static boolean esEmpleado() {

        return usuarioActual != null
                && "CONSULTAR".equals(
                usuarioActual.getRol()
        );
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
