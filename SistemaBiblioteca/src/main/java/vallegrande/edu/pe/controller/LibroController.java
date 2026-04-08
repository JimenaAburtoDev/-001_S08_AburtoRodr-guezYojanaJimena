package vallegrande.edu.pe.controller;


import vallegrande.edu.pe.model.InMemoryLibroRepository;
import vallegrande.edu.pe.model.Libro;

import java.util.List;

public class LibroController {

    private InMemoryLibroRepository repo = new InMemoryLibroRepository();

    public void agregar(String titulo, String autor, String categoria, String estado) {
        repo.agregar(new Libro(titulo, autor, categoria, estado));
    }

    public List<Libro> listar() {
        return repo.listar();
    }

    public void eliminar(int index) {
        repo.eliminar(index);
    }

    public void editar(int index, String titulo, String autor, String categoria, String estado) {
        repo.actualizar(index, new Libro(titulo, autor, categoria, estado));
    }
}