package bookusers;

import javax.swing.*;
//importa Arraylist para crear listas que almacenan varios objetos
import java.util.ArrayList;
//permite eliminar tildes y que quede en minuscula los caracteres
import java.text.Normalizer;
// Permiten detectar cambios en los campos de texto
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


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

    // Guarda la referencia al menú principal para generar los ID
    private final FormMenu menu;

    // Guarda la ubicación de la fotografía seleccionada
    private String rutaFoto = "";

    // Recibe la lista de usuarios y la referencia al menú principal
    public FormRegistro(ArrayList<Usuario> usuarios, FormMenu menu) {

        this.usuarios = usuarios;

        // Guarda la referencia al menú principal
        this.menu = menu;

        setTitle("BookUsers - Registro");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        // Activa la actualización automática del username
        configurarUsernameAutomatico();

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

    // Guarda los datos de un nuevo usuario en la lista compartida
    private void guardarUsuario() {

        String tipoIdentificacion = (String) cmbTipoIdentificacion.getSelectedItem();
        String numeroIdentificacion = txtNumeroIdentificacion.getText().trim();
        String primerNombre = txtPrimerNombre.getText().trim();
        String segundoNombre = txtSegundoNombre.getText().trim();
        String primerApellido = txtPrimerApellido.getText().trim();
        String segundoApellido = txtSegundoApellido.getText().trim();
        String password = new String(txtPassword.getPassword());
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
    }

    // elimina tildes, convierte la ñ en en y quita espacios
    private String limpiarTexto(String texto) {

        String resultado = Normalizer.normalize(texto.trim().toLowerCase(), Normalizer.Form.NFD);

        return resultado.replaceAll("\\p{M}", "").replaceAll("\\s+", "");
    }

    // Genera el username a partir del nombre y los apellidos
    private String generarUsername() {

        // Obtiene y limpia los datos del formulario
        String nombre = limpiarTexto(txtPrimerNombre.getText());
        String apellido1 = limpiarTexto(txtPrimerApellido.getText());
        String apellido2 = limpiarTexto(txtSegundoApellido.getText());

        // Evita generar el username si faltan datos obligatorios
        if (nombre.isEmpty() || apellido1.isEmpty()) {
            return "";
        }

        // Combina las iniciales de los apellidos con el nombre
        String inicial1 = apellido1.substring(0, 1);
        String inicial2 = apellido2.isEmpty() ? "" : apellido2.substring(0, 1);

        return inicial1 + inicial2 + nombre;
    }
    // Actualiza el username que se muestra en el formulario
    private void actualizarUsername() {
        lblUsername.setText(generarUsername());
    }

    // Detecta cambios en los nombres y apellidos para actualizar el username
    private void configurarUsernameAutomatico() {

        DocumentListener listener = new DocumentListener() {

            @Override
            //detecta cuando escribimos texto
            public void insertUpdate(DocumentEvent e) {
                actualizarUsername();
            }

            @Override
            //detecta cuadno borramos texto
            public void removeUpdate(DocumentEvent e) {
                actualizarUsername();
            }

            @Override
            //detecta cambios de atributos del documento
            public void changedUpdate(DocumentEvent e) {
                actualizarUsername();
            }
        };

        // Conecta el listener con los campos del formulario
        txtPrimerNombre.getDocument().addDocumentListener(listener);
        txtPrimerApellido.getDocument().addDocumentListener(listener);
        txtSegundoApellido.getDocument().addDocumentListener(listener);
    }
}
