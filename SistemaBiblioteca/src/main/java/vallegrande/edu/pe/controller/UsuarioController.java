package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.*;
import java.util.List;

public class UsuarioController {

    private InMemoryUsuarioRepository repo;

    public UsuarioController() {
        repo = new InMemoryUsuarioRepository();
    }

    public void agregarUsuario(String nombre, String dni) {
        if (nombre.trim().isEmpty() || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("Complete todos los campos");
        }

        if (!dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }

        Usuario usuario = new Usuario(nombre.trim(), dni.trim());
        repo.agregar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repo.listar();
    }

    public void editarUsuario(int index, String nombre, String dni) {
        if (nombre == null || dni == null || nombre.trim().isEmpty() || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("Datos inválidos");
        }

        if (!dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }

        Usuario usuario = new Usuario(nombre.trim(), dni.trim());
        repo.actualizar(index, usuario);
    }

    public void eliminarUsuario(int index) {
        repo.eliminar(index);
    }
}