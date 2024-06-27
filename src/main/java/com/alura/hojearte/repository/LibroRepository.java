package com.alura.hojearte.repository;

import com.alura.hojearte.model.Idioma;
import com.alura.hojearte.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idiomaSeleccionado")
    List<Libro> listarLibrosPorIdioma(Idioma idiomaSeleccionado);
}
