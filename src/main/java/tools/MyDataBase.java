package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    public final String url ="jdbc:mysql://localhost:3306/pi-dev";
    public final String user ="root";
    public final String password ="";

    private Connection cnx;
    private static MyDataBase myDataBase;

    private MyDataBase() {
        try {
            cnx = DriverManager.getConnection(url,user,password);
            System.out.println("connection etablie");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static MyDataBase getInstance() {
        if(myDataBase==null)
            myDataBase = new MyDataBase();
        return myDataBase;
    }

    public Connection getCnx() {
        return cnx;
    }
}
