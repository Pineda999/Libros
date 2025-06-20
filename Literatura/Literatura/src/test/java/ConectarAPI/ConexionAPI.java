package ConectarAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import net.bytebuddy.asm.Advice;
import org.json.JSONArray;
import org.json.JSONObject;


public class ConexionAPI{

    public static  void ApiLibros(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/"))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            JSONArray resultado = json.getJSONArray("Resultado");

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al conectar la API:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
