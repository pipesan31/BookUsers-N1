package bookusers;

import javax.swing.*;
//importa Arraylist para crear listas que almacenan varios objetos
import java.util.ArrayList;

public class FormRegistro extends JFrame {
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JComboBox<String> cmbTipoIdentificacion;
    private javax.swing.JTextField txtNumeroIdentificacion;
    private javax.swing.JTextField txtPrimerNombre;
    private javax.swing.JTextField txtSegundoNombre;
    private javax.swing.JTextField txtPrimerApellido;
    private javax.swing.JTextField txtSegundoApellido;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JButton btnCargarFoto;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JButton btnGuardar;

    private final ArrayList<Usuario> usuarios;

    // Recibe la lista de usuarios y configura la ventana de registro
    public FormRegistro(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;

        setTitle("BookUsers - Registro");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);

        // Ejecuta las validaciones cuando se presiona Guardar
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                JOptionPane.showMessageDialog(this,
                        "Validaciones correctas");
            }
        });
    }

    // Verifica que los campos obligatorios estén completos
    private boolean validarCampos() {

        if (txtNumeroIdentificacion.getText().trim().isEmpty()
                || txtPrimerNombre.getText().trim().isEmpty()
                || txtPrimerApellido.getText().trim().isEmpty()
                || txtPassword.getPassword().length == 0
                || txtTelefono.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Completa todos los campos obligatorios");
            return false;
        }
        // Obtiene el tipo y número de identificación ingresados
        String tipo = (String) cmbTipoIdentificacion.getSelectedItem();
        String numero = txtNumeroIdentificacion.getText().trim();

        // Comprueba si el documento permite letras y números
        boolean permiteLetras = tipo != null &&
                (tipo.startsWith("41 -") || tipo.startsWith("42 -"));

        // revisa que los demás documentos contengan únicamente dígitos
        if (!permiteLetras && !numero.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "El número de identificación solo debe contener números");
            return false;
        }

        // Comprueba que el correo contenga @
        if (!txtCorreo.getText().contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "El correo debe contener @");
            return false;
        }

        return true;
    }
}
