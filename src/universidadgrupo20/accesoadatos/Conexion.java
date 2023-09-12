package universidadgrupo20.accesoadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static final String URL = "jbdc:mariadb://localhost/";
    private static final String DB = "universidadgrupo20";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private Conexion() {
    }

    public static Connection getConnection() {
        if (connection == null) {

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(URL + DB, USUARIO, PASSWORD);

            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar el Driver");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion ");
            }

        }

        // retorno  del get coneecion 
        return connection;
    }

}
