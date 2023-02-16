package org.example;

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

    Statement getStmt(){
        return stmt;
    }

    Connection getCon(){
        return con;
    }

    Boolean getLogged(){
        return logged;
    }

    Boolean isConn(){
        if(con != null){
            return true;
        }else{
            return false;
        }
    }

    void batchStmt(String query) throws SQLException {
        stmt.addBatch(query);
    }

    void setLogged(Boolean logged){
        this.logged = logged;
    }

    String getUser(){
        return user;
    }

    String getPass(){
        return pass;
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

    public SQLException executeBatch(){
        try{
            stmt.executeBatch();
        }catch(SQLException e){
            return e;
        }
        return null;
    }

    public SQLException createConnection(String user, String pass){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@155.158.112.45:1521:oltpstud",user,pass);   //"jdbc:oracle:thin:@localhost:1521:xe"
            stmt=con.createStatement();
        }catch(SQLException | ClassNotFoundException e){
            this.con = null;
            return (SQLException) e;
        }
        this.user = user;
        this.pass = pass;
        return null;
    }

    public SQLException closeConn(){
        try{
            con.close();
        }catch(SQLException e){
            return e;
        }
        return null;
    }
}
