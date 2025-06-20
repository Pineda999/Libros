package com.Alura.Literatura.ConectarAPI;

import com.Alura.Literatura.model.Autor;
import com.Alura.Literatura.repository.AutorRepository;
import com.Alura.Literatura.repository.LibroRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ConectarAPI {

    private static LibroRepository libroRepo;

    public static List<LibroAutorDTO> obtenerLibrosConAutores(String nombreLibro, AutorRepository autorRepo, LibroRepository libroRepo) {
        List<LibroAutorDTO> lista = new ArrayList<>();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gutendex.com/books/?search=" + nombreLibro.replace(" ", "%20")))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            JSONArray resultados = json.getJSONArray("results");

            for (int i = 0; i < resultados.length(); i++) {
                JSONObject libroJson = resultados.getJSONObject(i);
                String titulo = libroJson.getString("title");

                if (!titulo.equalsIgnoreCase(nombreLibro)) {
                    continue;
                }

                int descargas = libroJson.getInt("download_count");
                JSONArray idiomas = libroJson.getJSONArray("languages");
                JSONArray autores = libroJson.getJSONArray("authors");

                for (int j = 0; j < autores.length(); j++) {
                    JSONObject autorJson = autores.getJSONObject(j);
                    String nombreAutor = autorJson.getString("name");
                    int nacimiento = autorJson.optInt("birth_year", 0);
                    int fallecimiento = autorJson.optInt("death_year", 0);

                    Autor autor = autorRepo.findByNombre(nombreAutor);
                    if (autor == null) {
                        autor = new Autor(nombreAutor, nacimiento, fallecimiento);
                        autorRepo.save(autor);
                    }

                    for (int k = 0; k < idiomas.length(); k++) {
                        String idioma = idiomas.getString(k);
                        LibroAutorDTO libroExistente = libroRepo.findByTituloAndAutor(titulo, autor);
                        if (libroExistente == null) {
                            LibroAutorDTO libro = new LibroAutorDTO(titulo, idioma, descargas);
                            libro.setAutor(autor);
                            libroRepo.save(libro);
                            lista.add(libro);  // <-- Aquí va lista, no List
                        }
                    }
                }

                break;
            }
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }

        return lista;  // <-- Devuelve la lista declarada
    }
}
