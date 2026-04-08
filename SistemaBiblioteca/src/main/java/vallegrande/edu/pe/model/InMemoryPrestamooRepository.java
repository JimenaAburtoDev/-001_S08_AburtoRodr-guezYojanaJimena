package vallegrande.edu.pe.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPrestamooRepository {

    private List<Prestamo> lista = new ArrayList<>();
    private final String FILE = "prestamos.csv";

    public InMemoryPrestamooRepository() {
        cargar();
    }

    public void agregar(Prestamo p){
        lista.add(p);
        guardar();
    }

    public List<Prestamo> listar(){
        return lista;
    }

    public void eliminar(int index){
        lista.remove(index);
        guardar();
    }

    public void actualizar(int index, Prestamo p){
        lista.set(index, p);
        guardar();
    }

    // 💾 GUARDAR
    private void guardar(){
        try(PrintWriter pw = new PrintWriter(new FileWriter(FILE))){
            for(Prestamo p : lista){
                pw.println(p.getLibro() + "," +
                        p.getUsuario() + "," +
                        p.getFecha());
            }
        } catch(Exception e){
            System.out.println("Error guardando prestamos");
        }
    }

    // 📂 CARGAR
    private void cargar(){
        File file = new File(FILE);
        if(!file.exists()) return;

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split(",");
                if(datos.length == 3){
                    lista.add(new Prestamo(datos[0], datos[1], datos[2]));
                }
            }
        } catch(Exception e){
            System.out.println("Error cargando prestamos");
        }
    }
}