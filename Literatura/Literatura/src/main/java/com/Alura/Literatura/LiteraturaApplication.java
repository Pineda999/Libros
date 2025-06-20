package com.Alura.Literatura;

import com.Alura.Literatura.ConectarAPI.ConectarAPI;
import com.Alura.Literatura.ConectarAPI.LibroAutorDTO;
import com.Alura.Literatura.repository.AutorRepository;
import com.Alura.Literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepo;

	@Autowired
	private LibroRepository libroRepo;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("------ MENU ---------" +
					"\n" +
					"1- Buscar libro por título\n" +
					"2- Listar libros registrados\n" +
					"3- Listar autores registrados\n" +
					"4- Listar autores vivos en un determinado año\n" +
					"5- Listar libros por idioma\n" +
					"0- salir\n" +
					"Seleccione una opción: ");

			int opcion = scanner.nextInt();
			scanner.nextLine(); // limpiar salto

			switch (opcion) {
				case 1 -> buscarLibro(scanner);
				case 2 -> listarLibros();
				case 3 -> listarAutores();
				case 4 -> buscarAutoresPorAnio(scanner);
				case 5 -> buscarLibrosPorIdioma(scanner);
				case 0 -> {
					System.out.println("¡Hasta pronto!");
					return;
				}
				default -> System.out.println("Opción inválida.");
			}
		}
	}

	public void buscarLibro(Scanner scanner) {
		System.out.println("Ingrese el título del libro:");
		String titulo = scanner.nextLine();

		List<LibroAutorDTO> libros = ConectarAPI.obtenerLibrosConAutores(titulo, autorRepo, libroRepo);
		for (LibroAutorDTO libro : libros) {
			System.out.println(libro);
		}
	}

	public void listarLibros() {
		libroRepo.findAll().forEach(System.out::println);
	}

	public void listarAutores() {
		autorRepo.findAll().forEach(autor -> {
			System.out.println("Nombre: " + autor.getNombre());
		});
	}

	public void buscarAutoresPorAnio(Scanner scanner) {
		System.out.println("Ingrese el año:");
		int anio = scanner.nextInt();
		scanner.nextLine();  // <-- importante para consumir el salto de línea pendiente

		autorRepo.findAll().stream()
				.filter(a -> a.getNacimiento() <= anio && (a.getFallecimiento() == 0 || a.getFallecimiento() > anio))
				.forEach(a -> System.out.println("Autor vivo en " + anio + ": " + a.getNombre()));
	}

	public void buscarLibrosPorIdioma(Scanner scanner) {
		System.out.println("Ingrese el idioma (ej: 'es'):");
		String idioma = scanner.nextLine();

		libroRepo.findAll().stream()
				.filter(libro -> idioma.equals(libro.getIdioma()))
				.forEach(System.out::println);
	}
}
