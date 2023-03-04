package org.example;

import javax.swing.*;
import java.sql.SQLException;

public class LoginForm {
    private JButton logujButton;
    private JPanel panelLogin;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JLabel panelLabel;
    private JLabel loginLabel;
    private JLabel passLabel;
    private JLabel infoLabel;

    JPanel getPanelLogin(){
        return panelLogin;
    }

    JButton getLogujButton(){
        return logujButton;
    }

    public LoginForm() {

    }

    Boolean tryLogin(){
        infoLabel.setText("Logowanie...");
        OracleCon oc = OracleCon.getInstance();
        SQLException msg = oc.createConnection(loginTextField.getText().trim(), passwordField.getText().trim());
        if(msg != null){
            System.out.println(msg.toString());
            if(msg.toString().contains("ORA-01017")){
                infoLabel.setText("Błędny login/hasło");
            }else{
                infoLabel.setText(msg.toString());
            }
            oc.setLogged(false);
            return false;
        }else{
            oc.setLogged(true);
            infoLabel.setText("OK");
            return true;
        }
    }
}
