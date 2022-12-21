package org.example.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCredentials {
    String login, pass;
    public void getCredentials(int row){
       // BufferedReader br = null;
        this.login = "";
        this.pass = "";

        try (BufferedReader br = new BufferedReader(new FileReader("src/files/login_and_pass.txt"))){
            //br = new BufferedReader( new FileReader("files\\multiline.txt") );
            String str;
            String[] cred;

            int tmp = 0;
            while( (str = br.readLine()) != null && tmp<=row) {
                //System.out.println(str);
                cred = str.split("-");
                this.login = cred[0];
                this.pass = cred[1];
                tmp++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ReadCredentials(int row) {
        getCredentials(row);
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
