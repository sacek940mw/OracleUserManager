package org.example;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SearchForm {
    private JButton showAllButton;
    private JPanel panelSearch;
    private JTable usersTable;
    private JTextArea resultTextArea;
    private JScrollPane resultScrollPane;
    private JComboBox searchComboBox;

    private OracleCon oc = OracleCon.getInstance();

    String[] colNames = {"Index", "USERNAME", "ACCOUNT_STATUS", "EXPIRY_DATE", "CREATED"};
    TableColumn[] columns;

    public SearchForm(){
        columns = new TableColumn[colNames.length];
        createUIComponents();
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllUsers();
            }
        });
    }

    JPanel getPanelSearch(){
        return panelSearch;
    }

    JTable getUsersTable() { return usersTable;}

    private void createUIComponents() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        usersTable.setDefaultEditor(Object.class, null);
        JTableHeader th = usersTable.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();

        for(int i=0; i<columns.length; i++){
            columns[i] = new TableColumn();
            model.addColumn(columns[i]);
        }
        for(int i=0; i<columns.length; i++){
            TableColumn tc = tcm.getColumn(i);
            tc.setHeaderValue(colNames[i]);
        }
        tcm.getColumn(0).setPreferredWidth(10);
    }

    private void showAllUsers(){
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        String tmp = "";
        ResultSet rs = null;
        SQLException SQLe;

        String query = "SELECT USERNAME, ACCOUNT_STATUS, EXPIRY_DATE, created FROM DBA_USERS";

        if(searchComboBox.getSelectedItem().toString() != "brak"){
            if(searchComboBox.getSelectedItem().toString() == "nazwa"){
                query += " ORDER BY USERNAME";
            }else{
                query += " ORDER BY created";
            }
        }

        int licz = 0;
        model.setRowCount(0);
        SQLe = oc.executeQuery(query);
        if(SQLe == null){
            rs = oc.getRs();
            try{
                while(rs.next()){
                    licz++;
                    tmp += (rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "\n");

                    //System.out.println(model.getRowCount());
                    model.addRow(new Object[]{licz, rs.getString(1),  rs.getString(2), rs.getString(3), rs.getString(4), ""});
                }
            }catch (SQLException e){
                tmp = e.toString();
            }
        }else{
            tmp = SQLe.toString();
        }
        resultTextArea.setText(tmp);
    }
}
