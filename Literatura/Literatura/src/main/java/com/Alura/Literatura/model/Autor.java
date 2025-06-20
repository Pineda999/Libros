package com.Alura.Literatura.model;

import com.Alura.Literatura.ConectarAPI.LibroAutorDTO;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int nacimiento;
    private int fallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibroAutorDTO> libros;

    public Autor() {}

    public Autor(String nombre, int nacimiento, int fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public List<LibroAutorDTO> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroAutorDTO> libros) {
        this.libros = libros;
        for (LibroAutorDTO libro : libros) {
            libro.setAutor(this);
        }
    }
}