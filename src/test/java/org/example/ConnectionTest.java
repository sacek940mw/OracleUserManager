package org.example;

import org.example.files.ReadCredentials;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
W folderze files powinien znajdować się plik o nazwie "login_and_pass.txt" zawierający testowe loginy do bazy danych.
Plik powinien zawierać 2 wiersze. Pierwszy wiersz: "login-pass", drugi parę login-hasło oddzielone myślnikiem.

Przykłądowa struktura pliku:
login-pass
admin-hasloAdmin


 */

public class ConnectionTest {
    @Test
    public void testReadCredentials(){
        ReadCredentials rc = new ReadCredentials(0);
        assertEquals("login",rc.getLogin());
        assertEquals("pass",rc.getPass());
    }
    @Test
    public void testTryConnection(){
        ReadCredentials rc = new ReadCredentials(1);
        String login = rc.getLogin(), pass = rc.getPass();
        String status = "";
        int errorCode = 0;
        String errorMessage = "";

        //Connection connection = null;
        try(Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@155.158.112.45:1521:oltpstud",login,pass)){
            //connection = DriverManager.getConnection("jdbc:oracle:thin:@155.158.112.45:1521:oltpstud",login,pass);
            if(connection == null){
                status = "Brak polaczenia";
            }else{
                status = "Jest polaczenie";
            }
        }catch(SQLException e){
            e.printStackTrace();
            errorCode = e.getErrorCode();
        }catch (Exception e){
            e.printStackTrace();
            errorMessage = e.getMessage();
        }

        assertEquals("Jest polaczenie", status);
        assertEquals(0, errorCode);
        assertEquals("", errorMessage);
    }
}
