package bookusers;

import javax.swing.*;
//importa Arraylist para crear listas que almacenan varios objetos
import java.util.ArrayList;
//permite eliminar tildes y que quede en minuscula los caracteres
import java.text.Normalizer;
// Permiten detectar cambios en los campos de texto
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.sha2cs.inputfile.InputFile;
// Permite trabajar con archivos seleccionados del computador
import java.io.File;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.filechooser.FileNameExtensionFilter;


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
    private static File ultimaCarpetaFoto;

    //variable para despues comprobar si es nuevo o ya existe el usuario
    private Usuario usuarioEditar;

    // Recibe la lista de usuarios y la referencia al menú principal
    public FormRegistro(ArrayList<Usuario> usuarios, FormMenu menu) {

        this.usuarios = usuarios;

        // Guarda la referencia al menú principal
        this.menu = menu;

        setTitle("BookUsers - Registro - Luis Santos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        // Activa la actualización automática del username
        configurarUsernameAutomatico();

        // Permite seleccionar una fotografía y mostrarla en el formulario
        btnCargarFoto.addActionListener(e -> {

            // Abre un selector amplio y conserva el filtro de imágenes.
            JFileChooser selector = new JFileChooser(ultimaCarpetaFoto);
            selector.setDialogTitle("BookUsers - Seleccionar fotografía");
            selector.setAcceptAllFileFilterUsed(false);
            selector.setFileFilter(new FileNameExtensionFilter(
                    "Imágenes PNG y JPG", "png", "jpg", "jpeg"));
            ampliarFuenteSelector(selector);
            selector.setPreferredSize(new Dimension(1200, 800));

            if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File foto = selector.getSelectedFile();
            ultimaCarpetaFoto = foto.getParentFile();

            // Conserva la carga y el escalado de la imagen de la librería.
            if (InputFile.mostrar(lblFoto, foto)) {
                rutaFoto = foto.getAbsolutePath();
            }
        });

        // Valida los datos antes de guardarlos
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {

                // Comprueba si estamos actualizando un usuario existente
                if (usuarioEditar != null) {

                    // Modifica los datos del mismo usuario sin cambiar su ID
                    actualizarUsuario();

                    // Confirma que los cambios se guardaron correctamente
                    JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");

                    // Cierra el formulario de actualización
                    dispose();
                    return;
                }

                // Guarda normalmente cuando es un registro nuevo
                guardarUsuario();
            }
        });
    }

    // Prepara el formulario para actualizar un usuario existente
    public FormRegistro(ArrayList<Usuario> usuarios, FormMenu menu, Usuario usuarioEditar) {

        // Reutiliza la configuración del constructor original
        this(usuarios, menu);

        // Guarda el usuario que vamos a modificar
        this.usuarioEditar = usuarioEditar;

        // Carga los datos existentes del usuario en el formulario
        cargarDatosUsuario();
    }

    // Carga los datos del usuario existente en el formulario
    private void cargarDatosUsuario() {

        cmbTipoIdentificacion.setSelectedItem(usuarioEditar.getTipoIdentificacion());
        txtNumeroIdentificacion.setText(usuarioEditar.getNumeroIdentificacion());
        txtPrimerNombre.setText(usuarioEditar.getPrimerNombre());
        txtSegundoNombre.setText(usuarioEditar.getSegundoNombre());
        txtPrimerApellido.setText(usuarioEditar.getPrimerApellido());
        txtSegundoApellido.setText(usuarioEditar.getSegundoApellido());
        txtPassword.setText(usuarioEditar.getPassword());
        txtTelefono.setText(usuarioEditar.getTelefono());
        txtCorreo.setText(usuarioEditar.getCorreo());
        rutaFoto = usuarioEditar.getRutaFoto();

        // Muestra la fotografía guardada en el formulario
        if (!InputFile.mostrar(lblFoto, rutaFoto)) {

            // Muestra un texto si la fotografía no está disponible
            InputFile.limpiar(lblFoto);
            lblFoto.setText("Sin foto");
        }
    }

    // Amplía las letras de los controles internos del selector de archivos.
    private void ampliarFuenteSelector(Component componente) {
        componente.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        if (componente instanceof Container contenedor) {
            for (Component hijo : contenedor.getComponents()) {
                ampliarFuenteSelector(hijo);
            }
        }
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
        String username = generarUsername();
        int id = menu.generarId();

        // Crea un objeto Usuario con los datos del formulario
        Usuario nuevoUsuario = new Usuario(id, tipoIdentificacion, numeroIdentificacion, primerNombre, segundoNombre, primerApellido, segundoApellido, username, password, telefono, correo, rutaFoto);

        usuarios.add(nuevoUsuario);

        // Confirma que el usuario fue registrado correctamente
        JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.\nID asignado: " + id, "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
    }

    // Limpia los campos después de registrar un usuario
    private void limpiarFormulario() {

        // Borra los datos de identificación
        txtNumeroIdentificacion.setText("");

        // Regresa el tipo de identificación a la primera opción
        cmbTipoIdentificacion.setSelectedIndex(0);

        // Borra los nombres y apellidos
        txtPrimerNombre.setText("");
        txtSegundoNombre.setText("");
        txtPrimerApellido.setText("");
        txtSegundoApellido.setText("");

        // Borra la contraseña y los datos de contacto
        txtPassword.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");

        // Limpia el username mostrado
        lblUsername.setText("");

        // Elimina la fotografía mostrada en el formulario
        InputFile.limpiar(lblFoto);

        // Borra la ruta para que no se asigne al siguiente usuario
        rutaFoto = "";

        // Muestra un texto cuando no hay fotografía seleccionada
        lblFoto.setText("Sin foto");
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

    // Actualiza los datos del usuario existente
    private void actualizarUsuario() {

        usuarioEditar.setTipoIdentificacion((String) cmbTipoIdentificacion.getSelectedItem());
        usuarioEditar.setNumeroIdentificacion(txtNumeroIdentificacion.getText().trim());
        usuarioEditar.setPrimerNombre(txtPrimerNombre.getText().trim());
        usuarioEditar.setSegundoNombre(txtSegundoNombre.getText().trim());
        usuarioEditar.setPrimerApellido(txtPrimerApellido.getText().trim());
        usuarioEditar.setSegundoApellido(txtSegundoApellido.getText().trim());
        usuarioEditar.setPassword(txtPassword.getText().trim());
        usuarioEditar.setTelefono(txtTelefono.getText().trim());
        usuarioEditar.setCorreo(txtCorreo.getText().trim());
        usuarioEditar.setRutaFoto(rutaFoto);
    }
}
