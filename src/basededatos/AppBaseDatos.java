package basededatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppBaseDatos {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conexion = AdminBD.obtenerConexion();

		Statement stmt;
		try {
			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from persona");
			while (rs.next()) {
				System.out.println(
						rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4));
			}
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
