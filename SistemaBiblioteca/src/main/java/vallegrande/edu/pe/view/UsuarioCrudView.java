package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioCrudView extends JFrame {

    private UsuarioController controller;
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtDni;

    public UsuarioCrudView() {
        controller = new UsuarioController();

        setTitle("Gestión de Usuarios");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔵 HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(21, 67, 96));

        JLabel titulo = new JLabel("👤 Gestión de Usuarios");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // ⚪ FORMULARIO
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);

        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        JTextField txtBuscar = new JTextField(20);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);

        JPanel form = new JPanel(new GridLayout(2, 2, 15, 15));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));
        form.setBackground(Color.WHITE);

        txtNombre = new JTextField();
        txtDni = new JTextField();

        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("DNI:"));
        form.add(txtDni);

        add(form, BorderLayout.BEFORE_FIRST_LINE);

        // 📋 TABLA
        modelo = new DefaultTableModel(new String[]{"Nombre", "DNI"}, 0);
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

        // 🎨 COLORES CONSISTENTES
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

        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());

        // 🧠 CLICK TABLA → LLENAR FORM
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                txtNombre.setText(tabla.getValueAt(fila, 0).toString());
                txtDni.setText(tabla.getValueAt(fila, 1).toString());
            }
        });

        cargarTabla();
    }

    // ➕ AGREGAR
    private void agregar() {
        if(validar()){
            controller.agregarUsuario(txtNombre.getText(), txtDni.getText());
            limpiar();
            cargarTabla();
        }
    }

    // ✏️ EDITAR
    private void editar() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }

        String nombre = JOptionPane.showInputDialog("Nombre:", tabla.getValueAt(fila, 0));
        String dni = JOptionPane.showInputDialog("DNI:", tabla.getValueAt(fila, 1));

        if (nombre == null || dni == null ||
                nombre.trim().isEmpty() || dni.trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        controller.editarUsuario(fila, nombre, dni);
        cargarTabla();
    }

    // 🗑 ELIMINAR
    private void eliminar() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar usuario?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            controller.eliminarUsuario(fila);
            cargarTabla();
        }
    }

    // 🔍 VALIDAR
    private boolean validar(){
        if(txtNombre.getText().isEmpty() || txtDni.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return false;
        }
        return true;
    }

    // 🔄 TABLA
    private void cargarTabla(){
        modelo.setRowCount(0);

        List<Usuario> lista = controller.listarUsuarios();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{u.getNombre(), u.getDni()});
        }
    }

    // 🧹 LIMPIAR
    private void limpiar(){
        txtNombre.setText("");
        txtDni.setText("");
    }
}