import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
 
public class JDBCConnect {
 
  public static void main(String[] argv) {
 
 
 try {
 Class.forName("com.mysql.jdbc.Driver");
 } catch (ClassNotFoundException e) {
 e.printStackTrace();
 return;
 }
 System.out.println("MySQL JDBC Driver Located ");
 Connection connection = null;
 
 try {
 connection = DriverManager
 .getConnection("jdbc:mysql://db1194.mydbserver.com:3306/usr_p260403_1","p260403d1", "");
 System.out.println(" You are using the DBMS: "+connection.getMetaData().getDatabaseProductName());
 
 } catch (SQLException e) {
 System.out.println("Failed to get a Connection");
 e.printStackTrace();
 return;
 }
 
 if (connection != null) {
 System.out.println(" Database connection obtained. ");
 } else {
 System.out.println(" Failed to get a Database connection ");
 }
  }
}
