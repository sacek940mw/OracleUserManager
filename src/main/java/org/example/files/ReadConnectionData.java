package org.example.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadConnectionData {
    String conString;

    public ReadConnectionData() {
        this.conString = "";
        getData();
    }

    public void getData(){

        try (BufferedReader br = new BufferedReader(new FileReader("src/files/connectionData.txt"))){
            conString = br.readLine().replace("\n","");
            System.out.println(conString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConString() {
        return conString;
    }
}
