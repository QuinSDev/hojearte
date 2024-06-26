package com.alura.hojearte.principal;

import com.alura.hojearte.model.Datos;
import com.alura.hojearte.model.DatosLibro;
import com.alura.hojearte.service.ConsumoAPI;
import com.alura.hojearte.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();

    public void mostrarElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            String menuMensaje = """
                    -----------------------------------------------------
                    |               Bienvenido a Hojearte               |
                    -----------------------------------------------------
                    |                   Menú de opciones                |
                    ----------------------------------------------------
                    |   1. Buscar libro por titulo                      |
                    |   2. Listar libros registrados                    |
                    |   3. Listar libros por idioma                     |
                    |   4. Listar autores registrados                   |
                    |   5. Listar autores vivos en un determinado año   |
                    |   0. Salir                                        |
                    -----------------------------------------------------  
                    """;
            System.out.println(menuMensaje);
            System.out.print("Elija una opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
                    System.out.println("Saliendo de Hojearte...");
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        }
    }

    private Datos getDatosLibro() {
        System.out.print("Escriba el nombre del libro que deseas buscar: ");
        String tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatosAPI(URL_BASE+"?search="+tituloLibro.replace(" ", "+"));
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        return datos;
    }

    private void buscarLibroPorTitulo() {
        Datos datosEncontrados = getDatosLibro();
        Optional<DatosLibro> libroBuscado = datosEncontrados.resultados().stream()
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("""
                     \n-------------------------------------------------
                                           Libro                                      
                     -------------------------------------------------""" +
                    "\n    Titulo: " + libroBuscado.get().titulo() +
                    "\n    Autor: " + libroBuscado.get().autor().stream()
                            .map(a -> a.nombre()).limit(1).collect(Collectors.joining()) +
                    "\n    Idioma: " + libroBuscado.get().idiomas() +
                    "\n    Número de descargas: " + libroBuscado.get().numeroDeDescargas() +
                    "\n-------------------------------------------------\n");
        }
    }
}
