package vallegrande.edu.pe.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryLibroRepository {

    private List<Libro> lista = new ArrayList<>();
    private final String FILE = "libros.csv";

    public InMemoryLibroRepository() {
        cargarDesdeArchivo();
    }

    public void agregar(Libro libro) {
        lista.add(libro);
        guardarEnArchivo();
    }

    public List<Libro> listar() {
        return lista;
    }

    public void eliminar(int index) {
        lista.remove(index);
        guardarEnArchivo();
    }

    public void actualizar(int index, Libro libro) {
        lista.set(index, libro);
        guardarEnArchivo();
    }

    // 💾 GUARDAR
    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Libro l : lista) {
                pw.println(l.getTitulo() + "," +
                        l.getAutor() + "," +
                        l.getCategoria() + "," +
                        l.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Error guardando archivo");
        }
    }

    // 📂 CARGAR
    private void cargarDesdeArchivo() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    lista.add(new Libro(datos[0], datos[1], datos[2], datos[3]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando archivo");
        }
    }
}