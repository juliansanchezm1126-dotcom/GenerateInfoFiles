import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Genera archivos de texto con información pseudoaleatoria
 * que serán utilizados como archivos de entrada para el proyecto
 * de generación y clasificación de datos.
 *
 * <p>
 * Esta clase corresponde a la primera entrega del proyecto.
 * Su función principal es generar:
 * </p>
 *
 * <ul>
 *     <li>Información de vendedores.</li>
 *     <li>Información de productos.</li>
 *     <li>Información de ventas por vendedor.</li>
 * </ul>
 *
 * @author Estudiante
 * @version 1.0
 */
public class GenerateInfoFiles {

    /**
     * Lista de nombres utilizada para generar vendedores
     * de manera pseudoaleatoria.
     */
    private static final String[] NOMBRES = {
        "Julian",
        "Esteban",
        "Maria",
        "Carlos",
        "Ana",
        "Pedro",
        "Sofia",
        "Luis",
        "Laura",
        "Diego"
    };

    /**
     * Lista de apellidos utilizada para generar vendedores
     * de manera pseudoaleatoria.
     */
    private static final String[] APELLIDOS = {
        "Sanchez",
        "Mesa",
        "Gomez",
        "Perez",
        "Rodriguez",
        "Martinez",
        "Lopez",
        "Garcia",
        "Torres",
        "Vargas"
    };

    /**
     * Tipos de documento permitidos para los vendedores.
     */
    private static final String[] TIPOS_DOCUMENTO = {
        "CC",
        "CE",
        "TI"
    };

    /**
     * Lista base de productos para generar
     * la información de productos.
     */
    private static final String[] PRODUCTOS_BASE = {
        "Cuaderno",
        "Esfero",
        "Lapiz",
        "Borrador",
        "Regla",
        "Carpeta",
        "Marcador",
        "Tijeras",
        "Resaltador",
        "Pincel"
    };

    /**
     * Cantidad de productos que se utilizará para
     * generar los archivos de ventas.
     */
    private static final int CANTIDAD_PRODUCTOS = 10;

    /**
     * Método principal del programa.
     *
     * <p>
     * Genera los archivos necesarios para utilizar como
     * entrada del proyecto.
     * </p>
     *
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {

        try {

            System.out.println(
                "Iniciando generacion de archivos de prueba..."
            );

            /*
             * Genera la información general de los vendedores.
             */
            createSalesManInfoFile(4);

            /*
             * Genera la información de los productos.
             */
            createProductsFile(CANTIDAD_PRODUCTOS);

            /*
             * Genera los archivos de ventas de cada vendedor.
             */
            createSalesMenFile(
                10,
                "Julian_Sanchez",
                1018234567L
            );

            createSalesMenFile(
                8,
                "Maria_Gomez",
                1020123456L
            );

            createSalesMenFile(
                12,
                "Carlos_Lopez",
                1030987654L
            );

            createSalesMenFile(
                5,
                "Ana_Perez",
                1040555444L
            );

            System.out.println(
                "FINALIZACION EXITOSA: "
                + "Todos los archivos fueron creados correctamente."
            );

        } catch (IllegalArgumentException e) {

            System.err.println(
                "ERROR DE VALIDACION: " + e.getMessage()
            );

        } catch (IOException e) {

            System.err.println(
                "ERROR DE E/S: "
                + "Ocurrio un problema al escribir los archivos."
            );
            System.err.println(
                "Detalle: " + e.getMessage()
            );

        } catch (Exception e) {

            System.err.println(
                "ERROR INESPERADO: " + e.getMessage()
            );
        }
    }

    /**
     * Crea un archivo de ventas para un vendedor específico.
     *
     * <p>
     * La primera línea contiene el tipo de documento y
     * número de documento del vendedor. Las siguientes líneas
     * contienen el ID del producto y la cantidad vendida.
     * </p>
     *
     * <p>
     * Formato:
     * </p>
     *
     * <pre>
     * CC;1018234567
     * 1;3;
     * 5;2;
     * 8;4;
     * </pre>
     *
     * @param randomSalesCount cantidad de ventas a generar.
     * @param name nombre utilizado para identificar al vendedor.
     * @param id número de documento del vendedor.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public static void createSalesMenFile(
            int randomSalesCount,
            String name,
            long id) throws IOException {

        /*
         * Validación de la cantidad de ventas.
         */
        if (randomSalesCount <= 0) {
            throw new IllegalArgumentException(
                "La cantidad de ventas debe ser mayor que cero."
            );
        }

        /*
         * Validación del nombre.
         */
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre del vendedor no puede estar vacio."
            );
        }

        /*
         * Validación del documento.
         */
        if (id <= 0) {
            throw new IllegalArgumentException(
                "El ID del vendedor debe ser un numero positivo."
            );
        }

        String fileName = name + "_" + id + ".txt";

        Random random = new Random();

        /*
         * try-with-resources:
         * permite cerrar automáticamente el archivo.
         */
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName))) {

            /*
             * Primera línea:
             * TipoDocumento;NumeroDocumento
             */
            writer.write("CC;" + id);
            writer.newLine();

            /*
             * Generación de las ventas.
             */
            for (int i = 0; i < randomSalesCount; i++) {

                /*
                 * ID de producto entre 1 y 10.
                 */
                int idProducto =
                    random.nextInt(CANTIDAD_PRODUCTOS) + 1;

                /*
                 * Cantidad vendida entre 1 y 5.
                 */
                int cantidadVendida =
                    random.nextInt(5) + 1;

                /*
                 * Formato:
                 * IDProducto;CantidadVendida;
                 */
                writer.write(
                    idProducto
                    + ";"
                    + cantidadVendida
                    + ";"
                );

                writer.newLine();
            }
        }
    }

    /**
     * Crea el archivo productos.txt.
     *
     * <p>
     * Cada línea contiene el ID del producto,
     * el nombre del producto y su precio por unidad.
     * </p>
     *
     * <p>
     * Formato:
     * </p>
     *
     * <pre>
     * ID;Nombre;Precio
     * </pre>
     *
     * @param productsCount cantidad de productos a generar.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public static void createProductsFile(
            int productsCount) throws IOException {

        /*
         * Validación de la cantidad de productos.
         */
        if (productsCount <= 0) {
            throw new IllegalArgumentException(
                "La cantidad de productos debe ser mayor que cero."
            );
        }

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("productos.txt"))) {

            /*
             * Generación de productos.
             */
            for (int i = 1; i <= productsCount; i++) {

                /*
                 * Se obtiene un nombre de la lista de productos.
                 */
                String nombreProducto =
                    PRODUCTOS_BASE[
                        (i - 1) % PRODUCTOS_BASE.length
                    ];

                /*
                 * Se agrega el número al nombre para
                 * garantizar que sea fácilmente identificable.
                 */
                nombreProducto =
                    nombreProducto + "_" + i;

                /*
                 * Precio entre $1.000 y $50.000,
                 * en incrementos de $500.
                 */
                double precioPorUnidad =
                    (random.nextInt(100) + 2) * 500.0;

                /*
                 * Formato:
                 * ID;Nombre;Precio
                 */
                writer.write(
                    i
                    + ";"
                    + nombreProducto
                    + ";"
                    + String.format(
                        java.util.Locale.US,
                        "%.2f",
                        precioPorUnidad
                    )
                );

                writer.newLine();
            }
        }
    }

    /**
     * Crea el archivo vendedores.txt.
     *
     * <p>
     * La información de cada vendedor es generada
     * de manera pseudoaleatoria utilizando listas de
     * nombres, apellidos y tipos de documento.
     * </p>
     *
     * <p>
     * Formato:
     * </p>
     *
     * <pre>
     * TipoDocumento;NumeroDocumento;Nombres;Apellidos
     * </pre>
     *
     * @param salesmanCount cantidad de vendedores a generar.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public static void createSalesManInfoFile(
            int salesmanCount) throws IOException {

        /*
         * Validación de la cantidad de vendedores.
         */
        if (salesmanCount <= 0) {
            throw new IllegalArgumentException(
                "La cantidad de vendedores debe ser mayor que cero."
            );
        }

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("vendedores.txt"))) {

            /*
             * Generación de los vendedores.
             */
            for (int i = 0; i < salesmanCount; i++) {

                /*
                 * Selección pseudoaleatoria del tipo
                 * de documento.
                 */
                String tipoDocumento =
                    TIPOS_DOCUMENTO[
                        random.nextInt(TIPOS_DOCUMENTO.length)
                    ];

                /*
                 * Generación de un número de documento
                 * de nueve o diez dígitos.
                 */
                long numeroDocumento =
                    1000000000L
                    + (long) (
                        random.nextDouble()
                        * 900000000L
                    );

                /*
                 * Selección pseudoaleatoria del nombre.
                 */
                String nombre =
                    NOMBRES[
                        random.nextInt(NOMBRES.length)
                    ];

                /*
                 * Selección pseudoaleatoria del apellido.
                 */
                String apellido =
                    APELLIDOS[
                        random.nextInt(APELLIDOS.length)
                    ];

                /*
                 * Formato:
                 * TipoDocumento;NumeroDocumento;Nombre;Apellido
                 */
                writer.write(
                    tipoDocumento
                    + ";"
                    + numeroDocumento
                    + ";"
                    + nombre
                    + ";"
                    + apellido
                );

                writer.newLine();
            }
        }
    }
}
