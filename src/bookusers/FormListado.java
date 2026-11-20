package bookusers;

import javax.swing.*;
import java.util.ArrayList;

public class FormListado extends JFrame {
    private JPanel panelPrincipal;
    private JList<Usuario> lstUsuarios;
    private JButton btnVerDetalle;

    private final ArrayList<Usuario> usuarios;

    public FormListado(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
        setTitle("BookUsers - Usuarios registrados");
        setContentPane(panelPrincipal);

        // crea el modelo que almacenará los usuarios
        DefaultListModel<Usuario> modelo = new DefaultListModel<>();

        //recorre todos los usuarios registrados
        for (Usuario usuario : usuarios) {
            // los añade al modelo
            modelo.addElement(usuario);
        }

        //conecta los datos con la parte visual
        lstUsuarios.setModel(modelo);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 850);
        setLocationRelativeTo(null);
    }
}
