package com.alura.hojearte.service;

import com.alura.hojearte.model.*;
import com.alura.hojearte.repository.AutorRepository;
import com.alura.hojearte.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public Optional<Libro> verificarLibroExistenteEnBD(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    public Optional<Autor> verificarAutorExistenteEnBD(String nombre) {
        return autorRepository.buscarAutorPorNombre(nombre);
    }

    public Autor guardarAutor(DatosLibro datosLibro) {
        //Asumiremos que el libro solo tiene un autor
        DatosAutor datosAutor = datosLibro.autor().get(0);

        Optional<Autor> autorExistente = verificarAutorExistenteEnBD(datosAutor.nombre());
        if (autorExistente.isPresent()) {
            return autorExistente.get();
        } else {
            Autor autor = new Autor(datosAutor);
            return autorRepository.save(autor);
        }
    }

    public Libro guardarLibroConElAutor(DatosLibro datosLibro) {
        Autor autor = guardarAutor(datosLibro);

        Libro libro = new Libro(datosLibro);
        libro.setAutor(autor);

        return libroRepository.save(libro);
    }

    public List<Libro> listarLibrosRegistrados() {
        return libroRepository.findAll();
    }

    public List<Libro> listarLibrosPorIdiomas(Idioma idiomaSeleccionado) {
        return libroRepository.listarLibrosPorIdioma(idiomaSeleccionado);
    }
}
