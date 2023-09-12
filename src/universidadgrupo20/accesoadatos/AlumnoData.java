package universidadgrupo20.accesoadatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Statement;
import universidadgrupo20.entidades.Alumnos;

public class AlumnoData {

    private Connection com = null;

    public AlumnoData() {

        com = Conexion.getConnection();

    }
    //Hcaer un insert en la tabla alumno.

    public void guardarALumno(Alumnos alumno) {
        // no pongo ID en el INSERT  por que es autoincremental.
        // Como los datos que voy a enviar desde el INSERT van a ser los que recibo por parametro solo escribo el signo de preguntas como caracter comodines.
        String sql = "INSERT INTO alumno (dni,apellido,nombre,fechaNac,estado) VALUES (?,?,?,?,? )";
        // Genero el objeto PS para en enviar la sentencia armada sql ademas le pido que devuelda la lista de las claves generadas de los alumnos que voy a agregas , en este caso es un solo alumno.Un solo ID.    

        try {
            // Reemplazar los signos de preguntas por los datos  que recibo a travez del parametro alumno que quiero enviar.

            PreparedStatement ps;
            ps = com.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Tengo un metodo SET por cada  tipo de dato que quiero setear (enviar) El primer parametro entero que corresponde al DNI . va del alumno el dni
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            // este DATE no es el del JAVA UTIL , es de JAVA SQL.
            ps.setDate(4, Date.valueOf(alumno.getFecha()));
            // el metodo GET de atributos booleanos en una clase se generan como IS
            ps.setBoolean(5, alumno.isEstado());
            // Una vez que armamos el PS , lo vamos a ejecutar.
            ps.executeUpdate();
            // devuelve la lista generada con la clave que le generaste al alumno .Devuelve un RS comosi hicieramos una consulta.
            ResultSet rs = ps.getGeneratedKeys();
            // RS  Devuelve una especia de tabla con una sola columna de tantas filas como alumno haya insertado Por eso no recorro con un while por que tengo una sola fila-
            if (rs.next()) {  // si ejecuto esta linea es por que lo pudo agragar y le seteanos un id
                alumno.setIdAlumno(rs.getInt("idAlumno"));

                JOptionPane.showMessageDialog(null, "Alumno añadido con exito");

            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos" + ex.getMessage());
        }

    }
    // importante :el alumno ya esxiste en nuestra BS. Ya tiene un ID. Tomar los datos y enviar con una sentencia UPDATE.

    public void modificarAlumnos(Alumnos alumno) {

        String sql = "UPDATE alumno  SET dni= ?, apellido=?,nombre=?; fechaNac=?  WHERE idAlumno = ?";
        try {

            PreparedStatement ps = com.prepareStatement(sql);
            // Hay que setear los parametros dinamicos.

            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFecha()));
            ps.setBoolean(5, alumno.isEstado());

            int exito = ps.executeUpdate();

            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "MOdificado exitosamente !!");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe ");
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");

        }

    }

    public Alumnos buscarAlumno(int id) {

        Alumnos alumno = null;
        String sql = "SELECT dni,apellido,nombre,fechaNac FROM alumno WHERE =? AND estado =1 ";
        PreparedStatement ps = null;
        try {
            ps = com.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alumno = new Alumnos();
                alumno.setIdAlumno(id);
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha(rs.getDate("fechaNac").toLocalDate());
                alumno.setEstado(true);

            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");

        }
        return alumno;
    }

    public Alumnos buscarAlumnoPorDni(int dni) {

        Alumnos alumno = null;
        String sql = "SELECT dni,apellido,nombre,fechaNac FROM alumno WHERE  dni=? AND estado =1 ";
        PreparedStatement ps = null;
        try {
            ps = com.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = new Alumnos();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha(rs.getDate("fechaNac").toLocalDate());
                alumno.setEstado(true);

            } else {

                JOptionPane.showMessageDialog(null, "El alumno no existe");

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");
        }

        return alumno;

    }

    public List<Alumnos> listaAlumnos() {
        List<Alumnos> alumno = new ArrayList<>();

        try {
            String sql = "SELECT * FROM alumno WHERE estado=1 ";
            PreparedStatement ps = com.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Alumnos alu = new Alumnos();

                alu.setIdAlumno(rs.getInt("idAlumno"));
                alu.setDni(rs.getInt("dni"));
                alu.setApellido(rs.getString("apellido"));
                alu.setNombre(rs.getString("nombre"));
                alu.setFecha(rs.getDate("fechaNac").toLocalDate());
                alu.setEstado(rs.getBoolean("estado"));

                alumno.add(alu);

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");
        }
        return alumno;
    }

    public void eliminarAlumno(int id) {

        try {
            String sql = "UPDATE alumno SET estado=0 WHERE idAlumno = ? ";
            PreparedStatement ps = com.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                JOptionPane.showMessageDialog(null, "Se elimino el alumno ");

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");
        }

    }

}
