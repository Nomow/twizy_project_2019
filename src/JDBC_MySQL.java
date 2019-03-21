import java.io.*;
import java.sql.*;

public class JDBC_MySQL {
	public static void main(String[] args) throws Exception, IOException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager
             .getConnection("jdbc:mysql://localhost/Panneaux", "username", "password");
        String INSERT_PICTURE = "INSERT INTO my_picures(id, name, photo) VALUES (?, ?, ?)";

        conn.setAutoCommit(false);
        File file = new File("myPhoto.png");
        try (FileInputStream fis = new FileInputStream(file);
                    PreparedStatement ps = conn.prepareStatement(INSERT_PICTURE)) {
            ps.setString(1, "001");
            ps.setString(2, "name");
            ps.setBinaryStream(3, fis, (int) file.length());
            ps.executeUpdate();
            conn.commit();
        }
    }
}
