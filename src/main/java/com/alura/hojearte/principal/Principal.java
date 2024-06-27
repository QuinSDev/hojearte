package com.alura.hojearte.principal;

import com.alura.hojearte.model.*;
import com.alura.hojearte.service.ConsumoAPI;
import com.alura.hojearte.service.ConvierteDatos;
import com.alura.hojearte.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    @Autowired
    private LibroService libroService;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    public Principal() {
    }

    public void mostrarElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            String menuMensaje = """
                    -----------------------------------------------------
                    |               Bienvenido a Hojearte               |
                    -----------------------------------------------------
                    |                   Menú de opciones                |
                    -----------------------------------------------------
                    |   1. Buscar libro por titulo                      |
                    |   2. Listar libros registrados                    |
                    |   3. Listar libros por idioma                     |
                    |   4. Listar autores registrados                   |
                    |   5. Listar autores vivos en un determinado año   |
                    |   0. Salir                                        |
                    -----------------------------------------------------  
                    """;
            System.out.println("\n"+menuMensaje);
            System.out.print("Elija una opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarLibrosPorIdioma();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    listarAutoresPorAñoDeterminador();
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

            try {
                DatosLibro libroEncontrado = libroBuscado.get();
                Optional<Libro> libroExistente = libroService.verificarLibroExistenteEnBD(libroEncontrado.titulo());

                if (libroExistente.isPresent()) {
                    System.out.println("El libro '" + libroEncontrado.titulo() + "' ya se encuentra registrado en" +
                            " la base de datos.");
                } else {
                    Optional<Autor> autorExistente = libroService.verificarAutorExistenteEnBD(libroEncontrado.autor()
                            .stream().map(a -> a.nombre())
                            .collect(Collectors.joining()));

                    Autor autor = null;
                    if (autorExistente.isPresent()) {
                        System.out.println("El autor '" + autorExistente.get().getNombre() + "' ya esta registrado en" +
                                " la base de datos.");
                    } else {
                        autor = libroService.guardarAutor(libroEncontrado);
                    }

                    Libro libro = new Libro(libroEncontrado);
                    libro.setAutor(autor);
                    libro = libroService.guardarLibroConElAutor(libroEncontrado);

                    System.out.println("Se guardo el libro: " + libro.getTitulo());
                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el libro... Intenta con otro");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> listaDeLibros = libroService.listarLibrosRegistrados();
        listaDeLibros.forEach(l -> System.out.println("""
                     \n-------------------------------------------------
                                           Libro                                      
                     -------------------------------------------------""" +
                "\n    Titulo: " + l.getTitulo() +
                "\n    Autor: " + l.getAutor().getNombre() +
                "\n    Idioma: " + l.getIdioma().getIdiomaOmdb() +
                "\n    Número de descargas: " + l.getNumeroDeDescargas() +
                "\n-------------------------------------------------\n"));
    }

    private void listarLibrosPorIdioma() {
        String menuIdiomas = """
                    -------------------------------
                    |       Menú de Idiomas       |
                    -------------------------------
                    |       en - Inglés           |
                    |       es - Español          |
                    |       fr - Francés          |
                    |       pt - Portugués        |
                    -------------------------------
                """;
        System.out.println("\n" + menuIdiomas);
        System.out.print("Escribe el idioma que deseas: ");
        String idiomaIngresado = teclado.nextLine();
        if (idiomaIngresado.equalsIgnoreCase("es") || idiomaIngresado.equalsIgnoreCase("en")
                || idiomaIngresado.equalsIgnoreCase("fr") || idiomaIngresado.equalsIgnoreCase("pt")) {
            Idioma idiomaSeleccionado = Idioma.fromString(idiomaIngresado);
            List<Libro> librosPorIdioma = libroService.listarLibrosPorIdiomas(idiomaSeleccionado);
            if (librosPorIdioma.isEmpty()) {
                System.out.println("No se encuentra libros por ese idioma registrados en la base de datos.");
            } else {
                librosPorIdioma.forEach(l -> System.out.println("""
                     \n-------------------------------------------------
                                           Libro                                      
                     -------------------------------------------------""" +
                        "\n    Titulo: " + l.getTitulo() +
                        "\n    Autor: " + l.getAutor().getNombre() +
                        "\n    Idioma: " + l.getIdioma().getIdiomaOmdb() +
                        "\n    Número de descargas: " + l.getNumeroDeDescargas() +
                        "\n-------------------------------------------------\n"));
            }
        } else {
            System.out.println("Selecciona un idioma válido.");
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autoresRegistrados = libroService.listarAutoresRegistrados();
        if (autoresRegistrados.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
        } else {
            autoresRegistrados.forEach(a -> System.out.println("""
                     \n-------------------------------------------------
                                           Autor                                      
                     -------------------------------------------------""" +
                    "\n    Nombre: " + a.getNombre() +
                    "\n    Fecha de nacimiento: " + a.getFechaDeNacimiento() +
                    "\n    Fecha de fallecimiento: " + a.getFechaDeFallecimiento() +
                    "\n    Libros: " + a.getLibros() +
                    "\n-------------------------------------------------\n"));
        }
    }

    private void listarAutoresPorAñoDeterminador() {
        System.out.print("Ingrese el año para buscar autor(es) vivos en ese año: ");
        try {
            Integer anioIngresado = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autoresPorAnio = libroService.listarAutoresPorAnio(anioIngresado);
            if (!autoresPorAnio.isEmpty()) {
                autoresPorAnio.forEach(a -> System.out.println("""
                     \n-------------------------------------------------
                                           Autor                                      
                     -------------------------------------------------""" +
                        "\n    Nombre: " + a.getNombre() +
                        "\n    Fecha de nacimiento: " + a.getFechaDeNacimiento() +
                        "\n    Fecha de fallecimiento: " + a.getFechaDeFallecimiento() +
                        "\n    Libros: " + a.getLibros() +
                        "\n-------------------------------------------------\n"));
            } else {
                System.out.println("No se encontraron autores vivos en la base de datos en ese año.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Introduce un año válido.");
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un año de 4 cifras.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

}
