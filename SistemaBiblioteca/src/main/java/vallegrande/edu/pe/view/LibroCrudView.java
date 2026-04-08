package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.LibroController;
import vallegrande.edu.pe.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibroCrudView extends JFrame {

    private LibroController controller = new LibroController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtTitulo, txtAutor, txtCategoria, txtEstado;
    private JTextField txtBuscar;

    public LibroCrudView() {
        setTitle("Gestión de Libros");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔵 HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(21, 67, 96));
        JLabel titulo = new JLabel("📚 Gestión de Libros");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // 🔍 BUSCADOR
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);

        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        txtBuscar = new JTextField(20);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);

        // ⚪ FORMULARIO
        JPanel form = new JPanel(new GridLayout(2,4,15,15));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));
        form.setBackground(Color.WHITE);

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtCategoria = new JTextField();
        txtEstado = new JTextField();

        form.add(new JLabel("Título:"));
        form.add(txtTitulo);
        form.add(new JLabel("Autor:"));
        form.add(txtAutor);
        form.add(new JLabel("Categoría:"));
        form.add(txtCategoria);
        form.add(new JLabel("Estado:"));
        form.add(txtEstado);

        // 🔗 PANEL SUPERIOR (BUSCADOR + FORM)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);

        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        panelSuperior.add(form, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.BEFORE_FIRST_LINE);

        // 📋 TABLA
        modelo = new DefaultTableModel(new String[]{"Título","Autor","Categoría","Estado"},0);
        tabla = new JTable(modelo);

        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 🔘 BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(Color.WHITE);

        JButton btnVolver = new JButton("⬅ Menú");
        JButton btnAgregar = new JButton("➕ Agregar");
        JButton btnEditar = new JButton("✏️ Editar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");

        btnAgregar.setBackground(new Color(40, 167, 69));
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEliminar.setBackground(new Color(192, 57, 43));
        btnVolver.setBackground(Color.GRAY);

        btnAgregar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnVolver.setForeground(Color.WHITE);

        btnAgregar.setFocusPainted(false);
        btnEditar.setFocusPainted(false);
        btnEliminar.setFocusPainted(false);
        btnVolver.setFocusPainted(false);

        botones.add(btnVolver);
        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        // EVENTOS

        btnVolver.addActionListener(e -> {
            new MiniPaginaView().setVisible(true);
            dispose();
        });

        btnAgregar.addActionListener(e -> {
            if(validar()){
                controller.agregar(
                        txtTitulo.getText(),
                        txtAutor.getText(),
                        txtCategoria.getText(),
                        txtEstado.getText()
                );
                limpiar();
                cargarTabla();
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if(fila >= 0){
                controller.eliminar(fila);
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro");
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if(fila >= 0){
                Libro l = controller.listar().get(fila);

                String t = JOptionPane.showInputDialog("Título:", l.getTitulo());
                String a = JOptionPane.showInputDialog("Autor:", l.getAutor());
                String c = JOptionPane.showInputDialog("Categoría:", l.getCategoria());
                String es = JOptionPane.showInputDialog("Estado:", l.getEstado());

                if(t == null || a == null || c == null || es == null ||
                        t.trim().isEmpty() || a.trim().isEmpty() ||
                        c.trim().isEmpty() || es.trim().isEmpty()){

                    JOptionPane.showMessageDialog(this, "Complete todos los campos");
                    return;
                }

                controller.editar(fila, t, a, c, es);
                cargarTabla();

            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro");
            }
        });

        // 🔍 BUSQUEDA EN TIEMPO REAL
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                buscar(txtBuscar.getText());
            }
        });

        cargarTabla();
    }

    // 🔍 VALIDAR
    private boolean validar(){
        if(txtTitulo.getText().isEmpty() ||
                txtAutor.getText().isEmpty() ||
                txtCategoria.getText().isEmpty() ||
                txtEstado.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return false;
        }
        return true;
    }

    // 🧹 LIMPIAR
    private void limpiar(){
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtEstado.setText("");
    }

    // 🔄 CARGAR TABLA
    private void cargarTabla(){
        modelo.setRowCount(0);

        for(Libro l : controller.listar()){
            modelo.addRow(new Object[]{
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.getEstado()
            });
        }
    }

    // 🔎 BUSCAR
    private void buscar(String texto){
        modelo.setRowCount(0);

        for(Libro l : controller.listar()){
            if(l.getTitulo().toLowerCase().contains(texto.toLowerCase()) ||
                    l.getAutor().toLowerCase().contains(texto.toLowerCase())){

                modelo.addRow(new Object[]{
                        l.getTitulo(),
                        l.getAutor(),
                        l.getCategoria(),
                        l.getEstado()
                });
            }
        }
    }
}