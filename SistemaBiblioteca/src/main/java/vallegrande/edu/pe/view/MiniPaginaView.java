package vallegrande.edu.pe.view;

import javax.swing.*;
import java.awt.*;

public class MiniPaginaView extends JFrame {

    public MiniPaginaView() {
        setTitle("Sistema de Biblioteca - Portal Principal");
        setSize(750, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color primary = new Color(21, 67, 96);
        Color light = Color.WHITE;

        JPanel panel = new JPanel(new BorderLayout());

        // 🔵 HEADER
        JPanel header = new JPanel();
        header.setBackground(primary);
        header.setPreferredSize(new Dimension(750, 80));

        JLabel titulo = new JLabel("📚 Sistema de Biblioteca");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        header.add(titulo);

        // ⚪ CENTRO
        JPanel centro = new JPanel();
        centro.setBackground(light);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        centro.add(Box.createVerticalStrut(15));

        // ⭐ LOGO
        ImageIcon logo = new ImageIcon("src/img/logo.png");
        JLabel lblLogo = new JLabel(logo);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(lblLogo);

        JLabel subtitulo = new JLabel("Panel Principal");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(Box.createVerticalStrut(10));
        centro.add(subtitulo);
        centro.add(Box.createVerticalStrut(20));

        // 🔘 BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(light);

        JButton btnLibros = new JButton("📚 Libros");
        JButton btnPrestamos = new JButton("📖 Préstamos");
        JButton btnUsuarios = new JButton("👤 Usuarios");

        Dimension size = new Dimension(160, 40);

        btnLibros.setPreferredSize(size);
        btnPrestamos.setPreferredSize(size);
        btnUsuarios.setPreferredSize(size);

        btnLibros.setBackground(new Color(40, 116, 166));
        btnPrestamos.setBackground(new Color(52, 152, 219));
        btnUsuarios.setBackground(new Color(39, 174, 96));

        btnLibros.setForeground(Color.WHITE);
        btnPrestamos.setForeground(Color.WHITE);
        btnUsuarios.setForeground(Color.WHITE);

        btnLibros.setFocusPainted(false);
        btnPrestamos.setFocusPainted(false);
        btnUsuarios.setFocusPainted(false);

        btnLibros.addActionListener(e -> {
            new LibroCrudView().setVisible(true);
            dispose();
        });

        btnPrestamos.addActionListener(e -> {
            new PrestamoCrudView().setVisible(true);
            dispose();
        });

        btnUsuarios.addActionListener(e -> {
            new UsuarioCrudView().setVisible(true);
            dispose();
        });

        panelBotones.add(btnLibros);
        panelBotones.add(btnPrestamos);
        panelBotones.add(btnUsuarios);

        centro.add(panelBotones);

        panel.add(header, BorderLayout.NORTH);
        panel.add(centro, BorderLayout.CENTER);

        add(panel);
    }
}