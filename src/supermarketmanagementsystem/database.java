
package supermarketmanagementsystem;
import java.sql.*;

/**
 *
 * @author Atlas
 */
public class database {
    
    public static Connection connectDb(){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/pos_db", "root","");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
}
