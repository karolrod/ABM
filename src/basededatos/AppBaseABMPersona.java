package basededatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class AppBaseABMPersona {

	public static void main(String[] args) {

		Connection conexion = null;
		try {
			conexion = AdminBD.obtenerConexion();
			Scanner sc = new Scanner(System.in);

			int opcion = mostrarMenu(sc);
			while (opcion != 0) {

				switch (opcion) {
				case 1:
					alta(conexion, sc);
					break;
				case 2:
					modificacion(conexion, sc);
					break;
				case 3:
					baja(conexion, sc);
					break;
				case 4:
					listado(conexion);
					break;
				case 0:

					break;

				default:
					break;
				}
				opcion = mostrarMenu(sc);
			}

			conexion.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void listado(Connection conexion) {
		System.out.println();
		System.out.println("LISTADO--------------------");
		System.out.println("ID-NOMBRE-----EDAD-----F.NACIM---------");
		Statement stmt;
		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA");
			while (rs.next()) {
				Date fNac = rs.getDate(4);
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + fNac);
			}

			System.out.println("FIN LISTADO------------");
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void baja(Connection conexion, Scanner sc) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("LISTADO--------------------");
		System.out.println("ID-NOMBRE-----EDAD-----F.NACIM---------");
		Statement stmt = null;
		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA");
			while (rs.next()) {
				Date fNac = rs.getDate(4);
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + fNac);
			}

			System.out.println("FIN LISTADO------------");
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Ingrese el nro Id que desea eliminar ");
		String ideli = sc.next();

		stmt.execute("DELETE FROM PERSONA WHERE ID= " + ideli + ";");

		System.out.println("Registro eliminado");

	}

	private static void modificacion(Connection conexion, Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("LISTADO--------------------");
		System.out.println("ID-NOMBRE-----EDAD-----F.NACIM---------");
		Statement stmt = null;
		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA");
			while (rs.next()) {
				Date fNac = rs.getDate(4);
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + fNac);
			}

			System.out.println("FIN LISTADO------------");
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Ingrese el nro Id que desea modificar ");
			String ideli = sc.next();
			System.out.println("Ingrese nuevo nombre:");
			String nombremod = sc.next();
			System.out.println("Ingrese nueva fecha nacimiento (YYYY-MM-DD):");
			String fNacmod = sc.next();
			stmt.executeUpdate("update persona set nombre='" + nombremod + "'," + "fecha_nacimiento='" + fNacmod
					+ "' WHERE ID='" + ideli + "';");
			System.out.println("Registro modificado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void alta(Connection conexion, Scanner sc) {
		System.out.println("ALTA DE PERSONA");
		System.out.println();
		System.out.println("Ingrese nombre:");
		String nombre = sc.next();
		System.out.println("Ingrese fecha nacimiento (YYYY-MM-DD):");
		String fNac = sc.next();

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

		int edad = 0;
		try {
			Date fechaNac = sdf.parse(fNac);
			edad = calcularEdad(fechaNac);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Statement stmt;

		try {

			stmt = conexion.createStatement();
			

			stmt.executeUpdate("INSERT INTO PERSONA (nombre, edad, fecha_nacimiento) VALUES ('" + nombre + "', " + edad
					+ ",'" + fNac + "')");
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private static int calcularEdad(Date fechaNac) {
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(fechaNac);
		int anioActual = hoy.get(Calendar.YEAR);
		int anioNacim = gc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacim = gc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNacim = gc.get(Calendar.DATE);

		int dif = anioActual - anioNacim;

		if (mesActual < mesNacim) {
			dif = dif - 1;
		} else {
			if (mesActual == mesNacim && diaActual < diaNacim) {
				dif = dif - 1;
			}
		}

		return dif;
	}

	private static int mostrarMenu(Scanner sc) {

		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");

		System.out.println("");
		System.out.println("MENU OPCIONES: ");
		System.out.println("");
		System.out.println("1: ALTA ");
		System.out.println("2: MODIFICACION ");
		System.out.println("3: BAJA");
		System.out.println("4: LISTADO");
		System.out.println("0: SALIR");
		int opcion = 0;
		opcion = sc.nextInt();
		return opcion;
	}
}
