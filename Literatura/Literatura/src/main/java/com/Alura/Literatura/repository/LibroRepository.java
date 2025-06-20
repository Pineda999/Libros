package com.Alura.Literatura.repository;

import com.Alura.Literatura.ConectarAPI.LibroAutorDTO;
import com.Alura.Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<LibroAutorDTO, Long> {
    LibroAutorDTO findByTituloAndAutor(String titulo, Autor autor);
}
