package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TasksForm {
    private JPanel panelTasks;
    private JTextArea resultTextArea;
    private JTabbedPane tasksTabbedPane;
    private JComboBox kontaComboBox;
    private JPanel managePanel;
    private JPanel addPanel;
    private JTextField userTextField;
    private JComboBox kontaComboBox1;
    private JTextField passTextField;
    private JButton passButton;
    private JButton searchButton;
    private JButton unlockButton;
    private JComboBox newComboBox;
    private JTextField newLoginField;
    private JButton newAddButton;
    private JComboBox newPassComboBox;
    private JTextField newPassField;
    private JComboBox newManycomboBox;
    private JSpinner newToSpinner;
    private JSpinner newFromSpinner;
    private JComboBox deleteComboBox;
    private JTextField deleteLoginField;
    private JButton deleteButton;

    SearchForm searchForm = new SearchForm();

    private String tab[] = {"Wybierz", "Podaj:"};
    OracleCon oc;

    TasksForm(){
        oc = OracleCon.getInstance();
        createTasksTabbedPane();
        newPassField.setVisible(false);

        kontaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kontaComboBox.getSelectedItem() == "Podaj:"){
                    userTextField.setVisible(true);
                }else{
                    userTextField.setVisible(false);
                }
            }
        });

        kontaComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kontaComboBox1.getSelectedItem() == "Podaj:"){
                    passTextField.setVisible(true);
                }else{
                    passTextField.setVisible(false);
                }
            }
        });

        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "";
                String[] logins = getLogins();
                resultTextArea.setText("");

                if(kontaComboBox1.getSelectedItem() == "Podaj:"){
                    String newPass = passTextField.getText().toUpperCase();
                    for(String login : logins){
                        query = "ALTER USER " + login + " IDENTIFIED BY " + newPass + "\n";
                        executeQuery(query);
                    }
                }else if(kontaComboBox1.getSelectedItem() == "Jak login"){
                    for(String login : logins){
                        query = "ALTER USER " + login + " IDENTIFIED BY " + login + "\n";
                        executeQuery(query);
                    }
                }else{
                    resultTextArea.setText("Brak polecenia do wykonania.");
                }
            }
        });

        unlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "";
                String[] logins = getLogins();

                if(kontaComboBox1.getSelectedItem() == "Podaj:"){
                    String newPass = passTextField.getText().toUpperCase();
                    for(String login : logins){
                        query = "ALTER USER " + login + " IDENTIFIED BY " + newPass + " ACCOUNT UNLOCK\n";
                        executeQuery(query);
                    }
                }else if(kontaComboBox1.getSelectedItem() == "Jak login"){
                    for(String login : logins){
                        query = "ALTER USER " + login + " IDENTIFIED BY " + login + " ACCOUNT UNLOCK\n";
                        executeQuery(query);
                    }
                }else{
                    resultTextArea.setText("Brak polecenia do wykonania.");
                }
            }
        });
        newAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajKonto();
            }
        });
        newPassComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newPassComboBox.getSelectedItem() == "Podaj:"){
                    newPassField.setVisible(true);
                }else{
                    newPassField.setVisible(false);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultTextArea.setText("");
                String query = "";
                String[] logins;
                if(deleteComboBox.getSelectedItem() == "Podaj:"){
                    logins = new String[1];
                    logins[0] = deleteLoginField.getText().toUpperCase();
                }else{
                    logins = getLoginsTable();
                }

                for(String login : logins){
                    query = "DROP USER " + login + " CASCADE\n";
                    executeQuery(query);
                }
            }
        });
    }

    private void executeQuery(String query){
        ResultSet rs = null;
        SQLException SQLe;

        SQLe = oc.executeQuery(query);
        if(SQLe == null){
            resultTextArea.append("Wykonano polecenie:\n " + query);
        }else{
            resultTextArea.setText(SQLe.toString());
        }
    }

    private String[] getLogins(){
        String[] logins;
        if(kontaComboBox.getSelectedItem() == "Podaj:"){
            logins = new String[1];
            logins[0] = userTextField.getText().toUpperCase();
        }else{
            logins = getLoginsTable();
        }
        return logins;
    }

    private String[] getLoginsTable(){
        String[] logins;
        int count = searchForm.getUsersTable().getSelectedRowCount();
        int[] rows = searchForm.getUsersTable().getSelectedRows();
        logins = new String[count];
        for(int i=0; i<count; i++){
            logins[i] = searchForm.getUsersTable().getValueAt(rows[i],1).toString();
        }
        return logins;
    }

    private void dodajKonto(){
        resultTextArea.setText("");
        if(newComboBox.getSelectedItem() == "Student"){
            dodajStudent();
        }
    }

    private void dodajStudent(){
        String login = newLoginField.getText().toUpperCase();
        String pass = "";
        if(newPassComboBox.getSelectedItem() == "Podaj:"){
            pass = newPassField.getText().toUpperCase();
        }else{
            pass = login;
        }

        String query = "";
        if(newManycomboBox.getSelectedItem() == "Pojedyncze"){
            query = "CREATE USER \"" + login + "\" IDENTIFIED BY \"" + pass + "\"\n";
            executeQuery(query);
            query = "GRANT \"RESOURCE\" TO " + login + "\n";
            executeQuery(query);
            query = "GRANT \"CONNECT\" TO " + login + "\n";
            executeQuery(query);
            query = "GRANT CREATE VIEW TO " + login + "\n";
            executeQuery(query);
            query = "GRANT UNLIMITED TABLESPACE TO " + login + "\n";
            executeQuery(query);
        }else{
            int a = (int) newFromSpinner.getValue();
            int b = (int) newToSpinner.getValue();
            if(b>a){
                for(int i=a; i<=b; i++){
                    query = "CREATE USER \"" + login + i + "\" IDENTIFIED BY \"" + pass + "\"\n";
                    executeQuery(query);
                    query = "GRANT \"RESOURCE\" TO " + login + i + "\n";
                    executeQuery(query);
                    query = "GRANT \"CONNECT\" TO " + login + i + "\n";
                    executeQuery(query);
                    query = "GRANT CREATE VIEW TO " + login + i + "\n";
                    executeQuery(query);
                    query = "GRANT UNLIMITED TABLESPACE TO " + login + i + "\n";
                    executeQuery(query);
                }
            }else{
                resultTextArea.setText("Niepoprawny zakres!");
            }
        }
    }

    JPanel getPanelTasks(){
        return panelTasks;
    }

    JButton getSearchButton(){return searchButton;}

    private void createTasksTabbedPane(){
        //userTextField.setVisible(false);
        passTextField.setVisible(false);
        for (String x : tab) {
            kontaComboBox.addItem(x);
        }
        kontaComboBox1.addItem("Jak login");
        kontaComboBox1.addItem("Podaj:");
    }
}
