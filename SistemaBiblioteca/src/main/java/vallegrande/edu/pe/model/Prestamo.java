package vallegrande.edu.pe.model;

public class Prestamo {
    private String libro;
    private String usuario;
    private String fecha;

    public Prestamo(String libro, String usuario, String fecha) {
        this.libro = libro;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public String getLibro() { return libro; }
    public String getUsuario() { return usuario; }
    public String getFecha() { return fecha; }
}