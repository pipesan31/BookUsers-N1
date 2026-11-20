package bookusers;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

public class FormMenu {
    private JPanel panelPrincipal;
    private JButton btnCreditos;
    private JButton btnRegistrar;
    private JButton btnListado;
    private JLabel lblTitulo;

    //lista de los usuarios registrados
    private final ArrayList<Usuario> usuarios = new ArrayList<>();


    // Contador para asignar un ID único a cada usuario, empezando en 1
    private int siguienteId = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Amplía los mensajes y botones de las alertas y confirmaciones.
            UIManager.put("OptionPane.minimumSize", new Dimension(800, 300));
            UIManager.put("OptionPane.messageFont", new Font(Font.SANS_SERIF, Font.PLAIN, 28));
            UIManager.put("OptionPane.buttonFont", new Font(Font.SANS_SERIF, Font.BOLD, 24));

            FormMenu menu = new FormMenu();

            JFrame ventana = new JFrame("BookUsers");
            ventana.setContentPane(menu.panelPrincipal);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(1100, 800);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }

    // acciones de los botones del menú
    public FormMenu() {
        btnCreditos.addActionListener(e -> {
            FormCreditos creditos = new FormCreditos();
            creditos.setVisible(true);
        });
        btnRegistrar.addActionListener(e -> {
            FormRegistro registro = new FormRegistro(usuarios, this);
            registro.setVisible(true);
        });
        btnListado.addActionListener(e -> {
           FormListado listado = new FormListado(usuarios);
           listado.setVisible(true);
        });
    }
    // Devuelve el siguiente ID disponible y aumenta el contador
    public int generarId() {
        return siguienteId++;
    }
}
