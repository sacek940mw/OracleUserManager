package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    static OracleCon oc = OracleCon.getInstance();
    static JFrame frame = new JFrame("title");
    static JFrame searchFrame = new JFrame("title");
    static TasksForm taskForm = new TasksForm();;
    static LoginForm loginForm = new LoginForm();


    private static JFrame setFrame(String title, int option, JPanel jPanel){
        JFrame tframe = new JFrame();
        tframe.setTitle(title);
        tframe.setContentPane(jPanel);
        tframe.setDefaultCloseOperation(option);
        tframe.pack();
        tframe.setVisible(true);
        if(title == "Wyszukaj użytkowników"){

        }
        tframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                tframe.dispose();
                if(title == "Logowanie"){
                    endProcedure();
                }else if(title == "Zadania"){
                    oc.resetLogin();
                    frame = setFrame("Logowanie", JFrame.EXIT_ON_CLOSE, loginForm.getPanelLogin());
                }else{

                }
            }
        });
        return tframe;
    }

    private static void endProcedure(){
        if(oc.isConn())
            oc.closeConn();
        System.exit(0);
    }

    public static void main(String[] args) {

        loginForm.getLogujButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginForm.tryLogin() == true){
                    frame.dispose();
                    frame = setFrame("Zadania", JFrame.DO_NOTHING_ON_CLOSE, taskForm.getPanelTasks());
                }
            }
        });

        taskForm.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!searchFrame.isVisible()){
                    searchFrame = setFrame("Wyszukaj użytkowników", JFrame.DO_NOTHING_ON_CLOSE, taskForm.searchForm.getPanelSearch());
                }
            }
        });

        frame = setFrame("Logowanie", JFrame.DO_NOTHING_ON_CLOSE, loginForm.getPanelLogin());
    }
}