package bookusers;

import javax.swing.*;
import java.util.ArrayList;

public class FormListado extends JFrame {
    private JPanel panelPrincipal;
    private JList<Usuario> lstUsuarios;
    private JButton btnVerDetalle;
    private JButton btnEliminar;
    private JButton btnActualizar;

    private final ArrayList<Usuario> usuarios;

    private final FormMenu menu;

    public FormListado(ArrayList<Usuario> usuarios, FormMenu menu) {
        this.menu = menu;
        this.usuarios = usuarios;
        setTitle("BookUsers - Usuarios registrados - Luis Santos");
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

        // se programa la accion del boton ver detalle
        btnVerDetalle.addActionListener(e -> {

            //obtiene el usuario seleccionado en el Jlist
            Usuario seleccionado = (Usuario) lstUsuarios.getSelectedValue();

            // comprueba que si se haya seleccionado un usuario
           if (seleccionado == null) {
               JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario");
               return;
           }

           //abre la ventana con los datos del usuario seleccionado
           FormDetalle detalle  = new FormDetalle(seleccionado);
           detalle.setVisible(true);
        });

        // Detecta cuando se presiona el botón Eliminar
        btnEliminar.addActionListener(e -> {

            // Obtiene el usuario seleccionado en la lista
            Usuario seleccionado = (Usuario) lstUsuarios.getSelectedValue();

            // Comprueba que haya un usuario seleccionado
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un usuario para eliminar.");
                return;
            }

            // Pregunta si realmente desea eliminar al usuario seleccionado
            int respuesta = JOptionPane.showConfirmDialog(
                    this, "¿Deseas eliminar a " + seleccionado.getPrimerNombre() + " " + seleccionado.getPrimerApellido() + "?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Cancela la operación si el usuario no confirma
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            //elimina el usuario del Array compartido
            usuarios.remove(seleccionado);
            //lo elimina del listado visual
            modelo.removeElement(seleccionado);

            // Confirma que el usuario fue eliminado correctamente
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
        });

        // Detecta cuando se presiona el botón Actualizar
        btnActualizar.addActionListener(e -> {

            // Obtiene el usuario seleccionado en el listado
            Usuario seleccionado = (Usuario) lstUsuarios.getSelectedValue();

            // Comprueba que se haya seleccionado un usuario
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un usuario para actualizar.");
                return;
            }

            // Abre el formulario con los datos del usuario seleccionado
            FormRegistro formulario = new FormRegistro(usuarios, menu, seleccionado);

            // Actualiza el listado cuando se cierre el formulario de edición
            formulario.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    // Vuelve a mostrar los datos actualizados del usuario
                    lstUsuarios.repaint();
                }
            });
            formulario.setVisible(true);        });
    }
}
