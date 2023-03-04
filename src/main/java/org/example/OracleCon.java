package org.example;

import org.example.files.ReadConnectionData;

import java.sql.*;

public class OracleCon {
    private static OracleCon instance = null;
    private Connection con = null;
    private Statement stmt;
    private ResultSet rs;

    private static boolean logged = false;
    private String user = "";
    private String pass = "";

    private OracleCon(){}

    Boolean isConn(){
        return con != null;
    }

    void setLogged(Boolean logged){
        OracleCon.logged = logged;
    }

    ResultSet getRs(){
        return rs;
    }

    public void resetLogin(){
        logged = false;
        user = "";
        pass = "";
    }

    public static OracleCon getInstance(){
        if (instance == null)
            instance = new OracleCon();

        return instance;
    }

    SQLException executeQuery(String query){
        try {
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            return e;
        }
        return null;
    }

    public SQLException createConnection(String user, String pass){
        this.user = user;
        this.pass = pass;
        try{
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(
                    new ReadConnectionData().getConString(),this.user,this.pass);   //"jdbc:oracle:thin:@localhost:1521:xe"
            stmt=con.createStatement();
        }catch(SQLException e){
            this.con = null;
            return e;
        }
        return null;
    }

    public void closeConn(){
        try{
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
