package com.script972.controller;

import com.script972.DB.DBMani;
import com.script972.DB.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Observable;

/**
 * Created by script972 on 13.11.2016.
 */
public class MainController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextArea contentOut;

    @FXML
    private GridPane GridPane;



    public void lol(ActionEvent actionEvent) throws SQLException {
        contentOut.setText("Start \n");
        DBMani dbMani=new DBMani();
        String logIn=login.getText();
        String passWord=password.getText();
        ObservableList<User> listTrueLogin=dbMani.TakePersonLog(logIn);
        if(listTrueLogin.isEmpty())
            error("Not such user");
        else
            CheckPassword(listTrueLogin);

    }

    public  void CheckPassword(ObservableList<User> listTrueLogin){
        User user=listTrueLogin.get(0); //From DB
        String name=user.getName();
        String pass=user.getPassword(); //Password from DB
        String passwordF=password.getText();//Password from FORM дольчевита
        GeneratePassword(passwordF, name);
    }

    private void GeneratePassword(String passwordF, String name) {
        char [] alphavit={'а', 'б', 'в', 'г', 'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п',
                'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я', '’', '.',' '};
        char [] nameForGenerate=name.toCharArray();
        System.out.println(nameForGenerate);
        char [][] matrixPassword=new char[7][5];
        int c=0;
        int k=0;
        char [] newAlphavit=new char[alphavit.length-nameForGenerate.length];
        int count=0;
        boolean check=false;
        for (int i = 0; i < alphavit.length; i++) {
            check=false;
            for (int j = 0; j < nameForGenerate.length; j++) {
                if (alphavit[i]==nameForGenerate[j])
                    check=true;
            }
            if (check==true)
                continue;
            else {
                newAlphavit[count]=alphavit[i];
                count++;
            }
        }



        for (int i = 0; i < matrixPassword.length; i++)
            for (int j = 0; j < matrixPassword[i].length; j++) {
                if (c<nameForGenerate.length) {
                    matrixPassword[i][j] = nameForGenerate[c]; c++;
                }else {
                    if (k < newAlphavit.length) {
                        matrixPassword[i][j] = newAlphavit[k];
                        k++;
                    }
                }
            }


            fillMatrix(matrixPassword);

        for (int i = 0; i < matrixPassword.length; i++) {
            for (int j = 0; j < matrixPassword[i].length; j++) {
                System.out.print(matrixPassword[i][j]+" ");
            }
            System.out.println();
        }
    }



    void fillMatrix(char[][] matrixPassword){
        GridPane.setGridLinesVisible(true);
        for (int i = 0; i < matrixPassword.length; i++) {
            for (int j = 0; j < matrixPassword[i].length; j++) {
                GridPane.add(new Label(String.valueOf(matrixPassword[i][j])),j,i);
            }
        }
    }



    public void error(String s) {
        contentOut.setText(contentOut.getText()+s+"\n");
    }
}
