package com.Alura.Literatura.ConectarAPI;

import com.Alura.Literatura.model.Autor;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class LibroAutorDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private int descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public LibroAutorDTO() {}

    public LibroAutorDTO(String titulo, String idioma, int descargas) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public int getDescargas() {
        return descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Idioma: " + idioma + "\n" +
                "Descargas: " + descargas + "\n" +
                "Autor: " + (autor != null ? autor.getNombre() : "Desconocido");
    }
}