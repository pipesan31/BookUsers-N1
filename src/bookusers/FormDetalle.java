package bookusers;

import javax.swing.*;
//libreria para mostrar imagenes
import com.sha2cs.inputfile.InputFile;
//comprueba si existe el archivo de la fotografía
import java.io.File;
//permite manejar errores al guardar archivos
import java.io.IOException;

import com.sha2cs.generatepdf.DocumentoPDF;

public class FormDetalle extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblId;
    private JLabel lblTipoIdentificacion;
    private JLabel lblNumeroIdentificacion;
    private JLabel lblPrimerNombre;
    private JLabel lblSegundoNombre;
    private JLabel lblPrimerApellido;
    private JLabel lblSegundoApellido;
    private JLabel lblUsername;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblFoto;
    private JButton btnGenerarPDF;
    private final Usuario usuario;

    //selecciona el usuario seleccionado en el listado
    public FormDetalle(Usuario usuario) {
        this.usuario = usuario;
        setTitle("BookUsers - Detalle del usuario - Luis Santos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1500, 1050);
        setLocationRelativeTo(null);
        //muestra los datos y fotografía del usuario
        mostrarDatosUsuario();
        //accion de generar pdf
        btnGenerarPDF.addActionListener(e -> generarPDF());
    }

    // Muestra los datos del usuario recibido
    private void mostrarDatosUsuario(){
        lblId.setText(String.valueOf(usuario.getId()));
        lblTipoIdentificacion.setText(usuario.getTipoIdentificacion());
        lblNumeroIdentificacion.setText(usuario.getNumeroIdentificacion());
        lblPrimerNombre.setText(usuario.getPrimerNombre());
        lblSegundoNombre.setText(usuario.getSegundoNombre());
        lblPrimerApellido.setText(usuario.getPrimerApellido());
        lblSegundoApellido.setText(usuario.getSegundoApellido());
        lblUsername.setText(usuario.getUsername());
        lblTelefono.setText(usuario.getTelefono());
        lblCorreo.setText(usuario.getCorreo());

        //muestra la foto guardad del usuario
        if (!InputFile.mostrar(lblFoto, usuario.getRutaFoto())){
            //si el usuario no tiene foto muestra la foto
            InputFile.limpiar(lblFoto);
            lblFoto.setText("Sin foto");
        }

    }

    //Prepara el dpcumento PDF del usuario seleccionado
    private void generarPDF(){
        DocumentoPDF documento = new DocumentoPDF("Ficha de usuario");

        //obtiene la ruta de la foto del usuario
        String ruta = usuario.getRutaFoto();

        //agrega la foto al PDF solamente si hay
        if(ruta != null && !ruta.isBlank() && new File(ruta).isFile()) {
            documento.imagen(ruta);
        }
        documento.campo("ID", usuario.getId());
        documento.campo("Tipo de identificación", usuario.getTipoIdentificacion());
        documento.campo("Número de identificación", usuario.getNumeroIdentificacion());
        documento.campo("Primer nombre", usuario.getPrimerNombre());
        documento.campo("Segundo nombre", usuario.getSegundoNombre());
        documento.campo("Primer apellido", usuario.getPrimerApellido());
        documento.campo("Segundo apellido", usuario.getSegundoApellido());
        documento.campo("Username", usuario.getUsername());
        documento.campo("Telefono", usuario.getTelefono());
        documento.campo("Correo", usuario.getCorreo());
        // Intenta guardar el documento PDF en el computador
        try {
            File pdf = documento.guardar("usuario-" + usuario.getId() + ".pdf");

            // Informa dónde se guardó el archivo
            JOptionPane.showMessageDialog(this,
                    "PDF generado correctamente:\n" + pdf.getAbsolutePath());

        } catch (IOException e) {

            // Muestra un mensaje si ocurre un error al guardar
            JOptionPane.showMessageDialog(this,
                    "Error al generar el PDF: " + e.getMessage());
        }
    }
}
