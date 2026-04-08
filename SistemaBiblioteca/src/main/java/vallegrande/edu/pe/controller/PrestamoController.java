package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class PrestamoController {

    private List<Prestamo> lista = new ArrayList<>();
    private final String FILE = "prestamos.csv";

    public void agregar(String libro, String usuario, String fecha){
        lista.add(new Prestamo(libro, usuario, fecha));
    }

    public List<Prestamo> listar(){
        return lista;
    }

    public void eliminar(int index){
        lista.remove(index);
    }

    public void editar(int index, String libro, String usuario, String fecha){
        lista.set(index, new Prestamo(libro, usuario, fecha));
    }

}