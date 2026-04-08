package vallegrande.edu.pe.model;

public class Libro {
    private String titulo;
    private String autor;
    private String categoria;
    private String estado;

    public Libro(String titulo, String autor, String categoria, String estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estado = estado;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}