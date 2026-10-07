package org.example.carmilaglow;

import org.example.carmilaglow.dao.ClienteDAO;
import org.example.carmilaglow.model.Cliente;

import org.example.carmilaglow.dao.CompraDAO;
import org.example.carmilaglow.dao.DetalleCompraDAO;
import org.example.carmilaglow.model.Compra;
import org.example.carmilaglow.model.DetalleCompra;

import java.util.List;

public class PruebaProducto {

    public static void main(String[] args) {

        /*ProductoDAO dao = new ProductoDAO();

        // CREAR PRODUCTO
        Producto producto = new Producto(

                "CP003",
                "MASCARILLA FACIAL",
                "MASCARILLA PARA EL ROSTRO",
                "VARIOS",
                1,
                24,
                3,
                600,
                1500
        );

        // INSERTAR
        boolean insertado = dao.insertar(producto);

        System.out.println("¿Producto insertado? " + insertado);

        // LISTAR
        System.out.println("\n---PRODUCTOS---");

        for (Producto p : dao.listar()) {
            System.out.println(
                    "ID: " + p.getId()
                    + " | Código: " + p.getCodigo()
                    + " | Nombre: " + p.getNombre()
                    + " | Descripción: " + p.getDescripcion()
                    + " | Marca: " + p.getMarca()
                    + " | Categoría: " + p.getCategoriaId()
                    + " | Stock: " + p.getStock()
                    + " | Stock mínimo: " + p.getStockMinimo()
                    + " | Precio costo: " + p.getPrecioCosto()
                    + " | Precio público: " + p.getPrecioPublico()
            );
        }

         

        /*System.out.println("=== PRODUCTOS INICIALES ===");

        for (Producto p : dao.listar()) {
            mostrar(p);
        }

            // 2. TOMAR EL PRIMER PRODUCTO
            if (dao.listar().isEmpty()) {
                System.out.println("no hay productos para porbar");
                return;
            }

            Producto producto = dao.listar().get(0);

            // 3. MODIFICAR
            producto.setNombre("PRODUCTO PUEBA");
            producto.setDescripcion("PRODUCTO DE ACTUALIZACIÓN");
            producto.setMarca("NUEVA MARCA");
            producto.setStock(20);
            producto.setStockMinimo(5);
            producto.setPrecioCosto(500);
            producto.setPrecioPublico(1200);

            boolean actualizado = dao.actualizar(producto);

            System.out.println("\n¿Producto actualizado " + actualizado);

            // 4. COMPROBAR ACTUALIZACIÓN
            System.out.println("\n ===PRODUCTO DESPUÉS DE ACTUALIZAR ===");

            for (Producto p : dao.listar()) {
                mostrar(p);
            }

            // 5. ELIMINAR
            /*boolean eliminado = dao.eliminar(producto.getId());
            System.out.println("\n¿Producto eliminado? " + eliminado);

            // 6. COMPROBAR ELIMINACIÓN
            System.out.println("\n=== PRODUCTO DESPUÉS DE ELIMINAR ===");

            for (Producto p : dao.listar()) {
                mostrar(p);


        }

        private static void mostrar (Producto p){
            System.out.println(
                    "ID: " + p.getId()
                            + " | Código: " + p.getCodigo()
                            + " | Nombre: " + p.getNombre()
                            + " | Descripción: " + p.getDescripcion()
                            + " | Marca: " + p.getMarca()
                            + " | Stock: " + p.getStock()
                            + " | Stock mínimo: " + p.getStockMinimo()
                            + " | Precio costo: " + p.getPrecioCosto()
                            + " | Precio público: " + p.getPrecioPublico()
            );
        //==================
        // PRUEBA CATEGORIA
        //==================
        CategoriaDAO dao1 = new CategoriaDAO();

        // Crear una categoria de prueba
        Categoria categoria = new Categoria(
                "JUGUETES",
                "JUEGOS PARA LOS NIÑOS "
        );

        boolean insertada = dao1.insertar(categoria);

        System.out.println("¿Categoria insertada? " + insertada);

        System.out.println("\n=== CATEGORÍAS ===");

        for (Categoria c : dao1.listar()) {
            System.out.println(
                    "ID: " + c.getId()
                            + " | Nombre: " + c.getNombre()
                            + " | Descripción: " + c.getDescripcion()
            );
        }
        //=======================
        //  PRUEBAS DE PROVEEDOR
        //=======================
        ProveedorDAO dao = new ProveedorDAO();

        // INSERTAR
        Proveedor proveedor = new Proveedor(
                "SOFI BOUTIQUE",
                "+541166084699",
                "@tiendaSofia.com.ar",
                "LARREA 269 CABA"
        );
        boolean insertado =dao.insertar(proveedor);

        System.out.println(
                "Proveeedor insertado: " + insertado
        );
        // LISTAR
        System.out.println("\n--- PROVEEDORES ---");

        Proveedor proveedorEncontrado = null;

        for (Proveedor p : dao.listar()) {
            System.out.println(
                    p.getId() + "|" +
                    p.getNombre() + "|" +
                    p.getTelefono() + "|" +
                    p.getEmail() + "|" +
                    p.getDireccion()
            );

            proveedorEncontrado = p;
        }

        // ACTUALIZAR
        if (proveedorEncontrado != null) {

            proveedorEncontrado.setTelefono("011-2222-3333");
            proveedorEncontrado.setEmail("ventas@belleza.com");

            boolean actualizado =
                    dao.actualizar(proveedorEncontrado);

            System.out.println(
                    "\nProveedor actualizado: " + actualizado
            );
        }

        // LISTAR DESPUÉS DE ACTUALIZAR
        System.out.println("\n--- DESPUÉS DE ACTUALIZAR ---");

        for (Proveedor p : dao.listar()) {

            System.out.println(
                    p.getId() + " | " +
                            p.getNombre() + " | " +
                            p.getTelefono() + " | " +
                            p.getEmail() + " | " +
                            p.getDireccion()
            );
        }

        // ELIMINAR
        if (proveedorEncontrado != null) {

            boolean eliminado =
                    dao.eliminar(proveedorEncontrado.getId());

            System.out.println(
                    "\nProveedor eliminado: " + eliminado
            );
        }

        // LISTAR DEPUÉS DE ELIMINAR
        System.out.println("\n--- DESPUÉS DE ELIMINAR ---");

        for (Proveedor p : dao.listar()) {

            System.out.println(
                    p.getId() + " | " +
                            p.getNombre() + " | " +
                            p.getTelefono() + " | " +
                            p.getEmail() + " | " +
                            p.getDireccion()
            );
        }

        //========================
        // PRUEBA DE CRUD CLIENTE
        //========================

        ClienteDAO dao = new ClienteDAO();

        // INSERTAR
        Cliente cliente = new Cliente(
                "Juan Pérez",
                "30123456",
                "011-4567-8900",
                "juan@email.com",
                "Av. Rivadavia 1234"
        );

        boolean insertado = dao.insertar(cliente);
        System.out.println(
                "Cliente insertado: " + insertado
        );

        // LISTAR
        System.out.println("\n--- CLIENTE ---");

        Cliente clienteEncontrado = null;

        for (Cliente c : dao.listar()) {
            System.out.println(
                    c.getId() + " | " +
                            c.getNombre() + " | " +
                            c.getDocumento() + " | " +
                            c.getTelefono() + " | " +
                            c.getEmail() + " | " +
                            c.getDireccion()
            );
            clienteEncontrado = c;
        }

        // ACTUALIZAR
        if (clienteEncontrado != null) {

            clienteEncontrado.setTelefono("" +
                    "011-999-8888"
            );
            clienteEncontrado.setEmail("" +
                    "nuevo@email.com"
            );
            boolean actuializado =
                    dao.actualizar(clienteEncontrado);
            System.out.println("\nCliente actualizado: " + actuializado);

            // LISTAR DESPUÉS DE ACTUALIZAR
            System.out.println(
                    "\n--- DESPUÉS DE ACTUALIZAR ---"
            );
            for (Cliente c : dao.listar()) {
                System.out.println(
                        c.getId() + " | " +
                                c.getNombre() + " | " +
                                c.getDocumento() + " | " +
                                c.getTelefono() + " | " +
                                c.getEmail() + " | " +
                                c.getDireccion()
                );
            }

            // ELIMINAR
            if (clienteEncontrado != null) {
                boolean eliminado =
                        dao.eliminar(
                                clienteEncontrado.getId()
                        );

                System.out.println(
                        "\nCliente eliminado: " + eliminado
                );
            }

            // LISTAR DESPUÉS DE ELIMINAR
            System.out.println(
                    "\n--- DESPUÉS DE ELIMINAR ---"
            );
            for (Cliente c : dao.listar()) {

                System.out.println(
                        c.getId() + " | " +
                                c.getNombre() + " | " +
                                c.getDocumento() + " | " +
                                c.getTelefono() + " | " +
                                c.getEmail() + " | " +
                                c.getDireccion()
                );
            }
        }*/

        //===================================
        // PRUEBA DE COMPRA Y COMPRA DETALLE
        //===================================
       /* CompraDAO compraDAO = new CompraDAO();
        DetalleCompraDAO detalleDAO = new DetalleCompraDAO();*/

        /*
         * IMPORTANTE:
         * Debe existir un proveedor con ID 1
         * y un producto con ID 1 en la base de datos.
         */

        /*Compra compra = new Compra(
                1,
                "2026-09-25",
                500000,
                "Compra de prueba"
        );

        boolean compraInsertada =
                compraDAO.insertar(compra);

        System.out.println(
                "Compra insertada: " + compraInsertada
        );

        if (!compraInsertada) {
            return;
        }

        System.out.println("\n--- COMPRAS ---");

        Compra compraEncontrada = null;

        List<Compra> compras = compraDAO.listar();

        for (Compra c : compras) {

            System.out.println(
                    c.getId() + " | " +
                            c.getProveedorId() + " | " +
                            c.getFecha() + " | " +
                            c.getTotal() + " | " +
                            c.getObservacion()
            );

            if (c.getId() > 0 && compraEncontrada == null) {
                compraEncontrada = c;
            }
        }

        if (compraEncontrada == null) {
            System.out.println(
                    "No se encontró la compra."
            );
            return;
        }*/

        /*
         * Crear detalle de prueba.
         *
         * Producto ID 1
         * Cantidad: 10
         * Precio unitario: $20,00
         * En centavos: 2000
         * Subtotal: $200,00
         * En centavos: 20000
         */

        /*DetalleCompra detalle = new DetalleCompra(
                compraEncontrada.getId(),
                1,
                10,
                2000,
                20000
        );

        boolean detalleInsertado =
                detalleDAO.insertar(detalle);

        System.out.println(
                "\nDetalle insertado: " +
                        detalleInsertado
        );

        System.out.println(
                "\n--- DETALLES DE LA COMPRA ---"
        );

        for (DetalleCompra d :
                detalleDAO.listarPorCompra(
                        compraEncontrada.getId())) {

            System.out.println(
                    d.getId() + " | " +
                            "Compra: " + d.getCompraId() + " | " +
                            "Producto: " + d.getProductoId() + " | " +
                            "Cantidad: " + d.getCantidad() + " | " +
                            "Precio: " + d.getPrecioUnitario() + " | " +
                            "Subtotal: " + d.getSubtotal()
            );
        }*/

    }
}

