package bookusers;

import javax.swing.*;
//libreria para mostrar imagenes
import com.sha2cs.inputfile.InputFile;

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
}
