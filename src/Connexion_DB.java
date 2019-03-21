
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class Connexion_DB {
 
    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     * 
     */
	static String fileName = "test.db";
    public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:C:/Users/guill/Documents/SQLite_Database/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     */
    
    public static void connect(String fileName) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/Users/guill/Documents/SQLite_Database/" + fileName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private static Connection connect2() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/guill/Documents/SQLite_Database/" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static void createNewTable(String fileName, String tableName) {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/guill/Documents/SQLite_Database/" + fileName;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+ tableName +" (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void selectAll(){
        String sql = "SELECT id, name, capacity FROM imagesPanneaux";
        
        try (Connection conn = connect2();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Get the warehouse whose capacity greater than a specified capacity
     * @param capacity 
     */
    public void getCapacityGreaterThan(double capacity){
               String sql = "SELECT id, name, capacity "
                          + "FROM warehouses WHERE capacity > ?";
        
        try (Connection conn = Connexion_DB.connect2();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setDouble(1,capacity);
            //
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
     
    public static void main(String[] args) {
        //createNewDatabase("test.db");
        //connect("test.db");
    	//createNewTable("test.db", "imagesPanneaux");
    	selectAll();
    	
    }
}