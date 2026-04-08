package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.PrestamoController;
import vallegrande.edu.pe.model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrestamoCrudView extends JFrame {

    private PrestamoController controller = new PrestamoController();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtLibro, txtUsuario, txtFecha;

    public PrestamoCrudView() {
        setTitle("Gestión de Préstamos");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔵 HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(21, 67, 96));
        JLabel titulo = new JLabel("📖 Gestión de Préstamos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // ⚪ FORMULARIO
        JPanel form = new JPanel(new GridLayout(2,3,15,15));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Préstamo"));
        form.setBackground(Color.WHITE);

        txtLibro = new JTextField();
        txtUsuario = new JTextField();
        txtFecha = new JTextField();

        form.add(new JLabel("Libro:"));
        form.add(txtLibro);
        form.add(new JLabel("Usuario:"));
        form.add(txtUsuario);
        form.add(new JLabel("Fecha:"));
        form.add(txtFecha);

        add(form, BorderLayout.BEFORE_FIRST_LINE);

        // 📋 TABLA
        modelo = new DefaultTableModel(new String[]{"Libro","Usuario","Fecha"},0);
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

        // 🎨 COLORES
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
                        txtLibro.getText(),
                        txtUsuario.getText(),
                        txtFecha.getText()
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

        btnEditar.addActionListener(e -> editar());

        // cargar datos
        cargarTabla();
    }

    // ✏️ EDITAR
    private void editar(){
        int fila = tabla.getSelectedRow();

        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un registro");
            return;
        }

        String libro = JOptionPane.showInputDialog("Libro:", tabla.getValueAt(fila,0));
        String usuario = JOptionPane.showInputDialog("Usuario:", tabla.getValueAt(fila,1));
        String fecha = JOptionPane.showInputDialog("Fecha:", tabla.getValueAt(fila,2));

        if(libro == null || usuario == null || fecha == null ||
                libro.trim().isEmpty() || usuario.trim().isEmpty() || fecha.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        controller.editar(fila, libro, usuario, fecha);
        cargarTabla();
    }

    // 🔍 VALIDAR
    private boolean validar(){
        if(txtLibro.getText().isEmpty() ||
                txtUsuario.getText().isEmpty() ||
                txtFecha.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return false;
        }
        return true;
    }

    // 🧹 LIMPIAR
    private void limpiar(){
        txtLibro.setText("");
        txtUsuario.setText("");
        txtFecha.setText("");
    }

    // 🔄 TABLA
    private void cargarTabla(){
        modelo.setRowCount(0);

        for(Prestamo p : controller.listar()){
            modelo.addRow(new Object[]{
                    p.getLibro(),
                    p.getUsuario(),
                    p.getFecha()
            });
        }
    }
}