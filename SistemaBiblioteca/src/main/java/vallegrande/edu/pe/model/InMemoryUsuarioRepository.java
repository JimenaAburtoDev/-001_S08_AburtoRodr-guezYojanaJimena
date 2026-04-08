package vallegrande.edu.pe.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();
    private final String FILE = "usuarios.csv";

    public InMemoryUsuarioRepository() {
        cargar();
    }

    public void agregar(Usuario usuario) {
        usuarios.add(usuario);
        guardar();
    }

    public List<Usuario> listar() {
        return usuarios;
    }

    public void actualizar(int index, Usuario usuario) {
        usuarios.set(index, usuario);
        guardar();
    }

    public void eliminar(int index) {
        usuarios.remove(index);
        guardar();
    }

    // 💾 GUARDAR
    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Usuario u : usuarios) {
                pw.println(u.getNombre() + "," + u.getDni());
            }
        } catch (Exception e) {
            System.out.println("Error guardando usuarios");
        }
    }

    // 📂 CARGAR
    private void cargar() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    usuarios.add(new Usuario(datos[0], datos[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando usuarios");
        }
    }
}