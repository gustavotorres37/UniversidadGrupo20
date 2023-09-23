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
        // inicializar nuestra variable que es la coneccion.
        com = Conexion.getConnection();

    }
    //Hacer un insert en la tabla alumno. Guardar un alumno en la base de datos!!
    // Recibo un alumno que lo creo en el main y lo paso por parametro. Mas adelante lo creo por una vista.

    public void guardarALumno(Alumnos alumno) {  //  mi variable alumno viene incompleta por que no trae el id . Tdos los datos menos el id.No tengo el id a esta altura.
        // no pongo ID en el INSERT  por que es autoincremental.
        // Como los datos que voy a enviar desde el INSERT van a ser los que recibo por parametro solo escribo el signo de preguntas como caracter comodines.
        // Se usa para update ,insert y select.
        String sql = "INSERT INTO alumno (dni,apellido,nombre,fechaNac,estado) VALUES (?,?,?,?,? )";
        // Genero el objeto PS para en enviar la sentencia armada sql ademas le pido que devuelda la lista de las claves generadas de los alumnos que voy a agregas , en este caso es un solo alumno.Un solo ID.    

        try {
            // Reemplazar los signos de preguntas por los datos  que recibo a travez del parametro alumno que quiero enviar.

            PreparedStatement ps;
            ps = com.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  //  guado el alumno en sql , se genera el id y pido que me la retorne desde sql
            // comienzo a guardar datos en la base de datos
            // Tengo un metodo SET por cada  tipo de dato que quiero setear (enviar) El primer parametro entero que corresponde al DNI . va del alumno el dni
            ps.setInt(1, alumno.getDni()); // saco el dato desde el parametro alumno y lo guardo en la posicion 1.
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            // este DATE no es el del JAVA UTIL , es de JAVA SQL.
            ps.setDate(4, Date.valueOf(alumno.getFecha()));
            // el metodo GET de atributos booleanos en una clase se generan como IS
            ps.setBoolean(5, alumno.isEstado()); // en automatico lo genera con el metodo isEstado.
            // Una vez que armamos el PS , lo vamos a ejecutar.

            //  YA TENGO TODAS LAS SENTECIAS TERMINADAS !!
            ps.executeUpdate();  // RECIEN ACA SE GENRO EL AUTONUMERICO , ACA CONFIRME LA SENTENCIA QUE ANTES CONFIRME.
            // devuelve la lista generada con la clave que le generaste al alumno . Devuelve un RS como si hicieramos una consulta.

            // RESULTSET ES UN COMANDO QUE ME DEJA RECUPERAR COSAS DE LA BASE DE DATOS!!
            ResultSet rs = ps.getGeneratedKeys();  //OBTENGO LA CLAVE GENERADDA EN LA LINE 40.
            // SI RS NO ES NULO ES POR QUE SE GENERO EL ID , QUE EN ESTE CASO ES UN O SOLO.
            // TIENE QUE HABERSE GENREADO NI ID PARA QUE EXISTA UN ALTA SI ESO NO PASO ES POR QUE HICE ALGO MAL 
            // RS  Devuelve una especia de tabla con una sola columna de tantas filas como alumno haya insertado Por eso no recorro con un while por que tengo una sola fila-
            if (rs.next()) {  // si ejecuto esta linea es porque lo puedo agregar y le seteanos un id.NEXT ES BOOLEANO. (SI LA CONDICON ES NULA ES FALSO)
                alumno.setIdAlumno(rs.getInt("idAlumno"));  // LLENO EL ATRIBUTO QUE FALTA QUE LO GENERO PERO NO LO HABIA LLENADO.

                JOptionPane.showMessageDialog(null, "Alumno añadido con exito");

            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos" + ex.getMessage());
        }

    }
    // importante :el alumno ya esxiste en nuestra BS. Ya tiene un ID. Tomar los datos y enviar con una sentencia UPDATE.

    public void modificarAlumnos(Alumnos alumno) { // actualizar !!  El alumno ya existe en la base de datos.   
         // el ID del alumno es el que me va a indicar cual voy a modificar.
        String sql = "UPDATE alumno  SET dni= ?, apellido=?,nombre=?; fechaNac=?  WHERE idAlumno = ?";
        try {

            PreparedStatement ps = com.prepareStatement(sql);   // no me va a retorna nuguna clave por que no es un INSERT. El alumno ya esxiste por lo tanto tiene generado su id !!
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
        String sql = "SELECT dni,apellido,nombre,fechaNac FROM alumno WHERE idAlumno=? AND estado =1 ";
        PreparedStatement ps = null;
        try {
            ps = com.prepareStatement(sql);
            // Reemplaza el signo d epregunta x el ID que recibo por parametro.
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery(); // nos devuelve un resultset de unico Alumno por que yo eleji el ID y lo guardo en la variable rs. Tengo que guardarlo en mi variable que quiero RETORNAR.

            if (rs.next()) {  // SI EN EL RESULT SET HAY UN ELEMENTO , ENTONCES SE PARA EN ESE ELEMENTO Y VAMOS A OBTENER LOS DATOS
                alumno = new Alumnos();   // CREO UN ALUMNO VACIO QUE LE QUIERO ETEAR EL ID , APELLIDO ETC. lO LLENO CON LO QUE TRAJE DE LA BS.
                alumno.setIdAlumno(id);  // saco el id del parametro. lo puedo sacar del result set tbn. // Error en el video de Cristina.
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido")); //al alumuno de vos a setear el apellido que esta en el resultset que es un String y esta en una columna llamada apellido.
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha(rs.getDate("fechaNac").toLocalDate()); // me trae como un "date" de sql , lo convierto en un LocalDate y lo seteo a la fecha de nacimiento.
                alumno.setEstado(true);
                //Si entre al IF es porque existe un alumno con el ID .
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");

        }
        return alumno; // Retorno el alumno..
    }

    public Alumnos buscarAlumnoPorDni(int dni) {

        Alumnos alumno = null; // Creo un alumno vacio por que elmetodo me va a retorna un alumno.
        String sql = "SELECT idAlumno , dni,apellido,nombre,fechaNac FROM alumno WHERE  dni=? AND estado =1 ";
        PreparedStatement ps = null;
        try {
            ps = com.prepareStatement(sql);
            ps.setInt(1, dni);  // voy a setear el primer parametro dinamico .
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = new Alumnos(); // En el resultSet obtengo el alumno al cual le seteo los valores pero se lo tengo que pedir al rs.
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

    public List<Alumnos> listaAlumnos() {   //  Nos devuelve una lista de alumnos.
        List<Alumnos> alumno = new ArrayList<>(); // lista vacia de alumnos

        try {
            String sql = "SELECT * FROM alumno WHERE estado=1 ";
            PreparedStatement ps = com.prepareStatement(sql); // sin parametos dinamicos.
            ResultSet rs = ps.executeQuery(); // Ejecuto la consulta y lo recorro con un While.
            while (rs.next()) {  // como el ResultSet nos devuelve  mas de una fila lo recorro con un while.

                Alumnos alu = new Alumnos(); // MieNtraS haya alumnos en la lista creo un alumno .A ese alumno setea el id,dni,nombre,apellito etc 

                alu.setIdAlumno(rs.getInt("idAlumno"));
                alu.setDni(rs.getInt("dni"));
                alu.setApellido(rs.getString("apellido"));
                alu.setNombre(rs.getString("nombre"));
                alu.setFecha(rs.getDate("fechaNac").toLocalDate());
                alu.setEstado(rs.getBoolean("estado"));

                alumno.add(alu);  // en cada vuelta genero un alumno y lo guardo.

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla de alumnos");
        }
        return alumno;  // Retorno la lista todos los alumnos que hay en la base de datos.
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
