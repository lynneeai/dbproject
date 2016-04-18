package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

public class connectDB {
    
    private static Connection myConn;
    
    public static Connection getConnection() {
        if (myConn != null) return myConn;
        return Connect();
    }
    
    public static Connection Connect() {
        String USER_NAME = "DB2016_G42";
        String PASSWORD = "dbrocks";
        String HOSTNAME = "diassrv2.epfl.ch";
        String PORT = "1521";
        String SID = "orcldias";
                
        String connection_url = "jdbc:oracle:thin:@" + HOSTNAME + ":" + PORT + ":" + SID;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Class not found "+ e);
        }
        System.out.println("JDBC Class found");
        
        try {
            myConn = DriverManager.getConnection(connection_url,USER_NAME,PASSWORD); 
            System.out.println("Connected");
        }
        catch(SQLException e){
            System.out.println("SQL exception occured" + e);
        }
        return myConn;
    }
    
    public ResultSet ExecuteSQL(String sql_stmt) {
        ResultSet rs = null;
        try {
            Statement stmt = myConn.createStatement();
            rs = stmt.executeQuery(sql_stmt);
        } catch (SQLException ex) {
            System.out.println("The following error has occured: " + ex.getMessage());
        }
        return rs; 
    }
    
    
    public void query() {
        int no_of_rows = 0;
        try {
            ResultSet rs = ExecuteSQL("SELECT * FROM AUTHORS WHERE ROWNUM <= 5");
            while (rs.next()) {
                no_of_rows++;
                int id = rs.getInt("AUTHOR_ID");
                String name = rs.getString("AUTHOR_NAME");
                Timestamp birthdate = rs.getTimestamp("BIRTHDATE");
                System.out.println(id+"   "+name+"    "+birthdate);
            }
            System.out.println("There are "+ no_of_rows + " record in the table");
        }
        catch(SQLException e){
            System.out.println("SQL exception occured" + e);
        }       
    }
    
    private static void close(Connection myConn) throws SQLException {
	if (myConn != null) {
            myConn.close();
	}
    }

    /*public static void main(String[] args) {
        connectDB book = new connectDB();
        book.getConnection();
        book.query();
        System.out.println("Terminated");
    }*/
    
}
