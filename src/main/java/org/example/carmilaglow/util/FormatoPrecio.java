package org.example.carmilaglow.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatoPrecio {

    private static final DecimalFormat FORMATO;

    static {
        DecimalFormatSymbols simbolos =
                new DecimalFormatSymbols(new Locale("es", "AR"));

        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');

        FORMATO = new DecimalFormat("$ #,##0.00", simbolos);
    }

    public static String formatear(int centavos) {
        double precio = centavos / 100.0;
        return FORMATO.format(precio);
    }


    public static int convertirACentavos(String texto) {

        if (texto == null || texto.isBlank()) {
            return 0;
        }

        String valor = texto.trim();

        // Quitar símbolo de moneda y espacios
        valor = valor.replace("$", "")
                .replace(" ", "");

        // Quitar separador de miles
        valor = valor.replace(".", "");

        // Convertir coma decimal a punto
        valor = valor.replace(",", ".");

        double precio = Double.parseDouble(valor);

        return (int) Math.round(precio * 100);
    }

    public static boolean esPrecioValido(String texto) {

        if (texto == null || texto.isBlank()) {
            return false;
        }

        try {
            int centavos = convertirACentavos(texto);

            return centavos >= 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
