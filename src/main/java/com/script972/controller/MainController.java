package com.script972.controller;

import com.script972.DB.DBMani;
import com.script972.DB.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

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
        String content=user.getSecretInfo();
        String passwordF=password.getText();//Password from FORM дольчевита
        System.out.println("Пароль  с БД"+pass+" Сгенерований пароль "+GeneratePassword(passwordF, name));
        if (pass.equals(GeneratePassword(passwordF, name))){
            error("Пароль правильний Аутентифікація пройдена");
            error("Секретна інформація="+content);
        } else
        {
            error("шось пішло не так");
        }
    }

    private String GeneratePassword(String passwordF, String name) {
        char [] alphavit={'а', 'б', 'в', 'г', 'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п',
                'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я', '’', '.','_'};
        
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
            return encrypt(matrixPassword, devidePass(passwordF));

    }
                                                                                                                /*DO IT*/
    String encrypt(char[][] matrixPassword, String[] password){
        int count=0;
        String outPassword=null;
        int [] bigram1=new int[2];
        int [] bigram2=new int[2];
        System.out.println("Пароль"+password.length);
        while(count<password.length-1) {
            for (int i = 0; i < matrixPassword.length; i++) {
                for (int j = 0; j < matrixPassword[i].length; j++) {
                    //System.out.println("Пароль "+String.valueOf(password[count].charAt(1))+" Матрица "+String.valueOf(matrixPassword[i][j]));
                    if (String.valueOf(password[count].charAt(0)).equals(String.valueOf(matrixPassword[i][j]))){
                       // System.out.print("Первый символ биграмы "+password[count].charAt(0)+" ");
                       // System.out.println("s="+i+" c="+j);
                        bigram1[0]=i;
                        bigram1[1]=j;
                    }
                    if (String.valueOf(password[count].charAt(1)).equals(String.valueOf(matrixPassword[i][j]))){
                        //System.out.print("Второй символ биграмы "+password[count].charAt(1)+" ");
                        //System.out.println("s="+i+" c="+j);
                        bigram2[0]=i;
                        bigram2[1]=j;
                    }
                   // System.out.println(matrixPassword.length);
                }
            }

            //bigram[0]==s
            //bigram[1]==c

            // System.out.println("Символ "+matrixPassword[i][j] +" Биграмы Первая "+password[count].charAt(0)+" S="+bigram1[0]+" C="+bigram1[1]+" Вторая Биграма "+password[count].charAt(1)+" S="+bigram2[0]+" C="+bigram2[1]);
            System.out.print(password[count].charAt(0)+" C="+bigram1[0]+" S="+bigram1[1]);
            System.out.print(" "+password[count].charAt(1)+"C="+bigram2[0]+" S="+bigram2[1]);

            // первый случай s1==s2 //CHECK
            if (bigram1[0]==bigram2[0]){
                System.out.println();
                bigram1[0]=bigram1[0];
               // System.out.print("Действия "+bigram1[1]+"+1%"+matrixPassword[0].length);
                bigram1[1]=(bigram1[1]+1)%matrixPassword[0].length;
               // System.out.print(" "+bigram1[1]);
                System.out.println("Первая биграма буква "+password[count].charAt(0)+" S="+bigram1[0]+" C="+bigram1[1]+" Вывод "+matrixPassword[bigram1[0]][bigram1[1]]);

                bigram2[0]=bigram2[0];
                bigram2[1]=(bigram2[1]+1)%matrixPassword[0].length;
                System.out.println("Вторая биграма буква "+password[count].charAt(1)+"  S="+bigram2[0]+" C="+bigram2[1] +" Вывод "+matrixPassword[bigram2[0]][bigram2[1]]);
                outPassword=outPassword+matrixPassword[bigram1[0]][bigram1[1]]+matrixPassword[bigram2[0]][bigram2[1]];
            }else   //CHECK
            if(bigram1[1]==bigram2[1]){
                System.out.println();
                bigram1[0]=(bigram1[0]+1)%matrixPassword.length;
                bigram1[1]=bigram1[1];
                System.out.println("Первая биграма буква "+password[count].charAt(0)+" S="+bigram1[0]+" C="+bigram1[1] );
                bigram2[0]=(bigram2[0]+1)%matrixPassword.length;
                bigram2[1]=bigram2[1];
                System.out.println("Вторая биграма буква "+password[count].charAt(1)+"  S="+bigram2[0]+" C="+bigram2[1] );
                outPassword=outPassword+matrixPassword[bigram1[0]][bigram1[1]]+matrixPassword[bigram2[0]][bigram2[1]];
            }
            else//в єтой ветке ошибка
            {
                int tempS1=bigram1[0];
                int tempC1=bigram1[1];
                int tempS2=bigram2[0];
                int tempC2=bigram2[1];
                System.out.println();
                bigram1[0]=tempS1;
                bigram1[1]=tempC2;
                System.out.println("Первая биграма буква "+password[count].charAt(0)+" S="+bigram1[0]+" C="+bigram1[1] );// CHECK
                bigram2[0]=tempS2;
                bigram2[1]=tempC1;//error
               // System.out.println(bigram1[1]);
                System.out.println("Вторая биграма буква "+password[count].charAt(1)+"  S="+bigram2[0]+" C="+bigram2[1] );
                outPassword=outPassword+matrixPassword[bigram1[0]][bigram1[1]]+matrixPassword[bigram2[0]][bigram2[1]];
            }




            count++;
        }
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        System.out.println("Вывод строки "+outPassword);

        System.out.print("divided password ");
        for (int i = 0; i < password.length; i++) {
            System.out.print(password[i]+" ");
        }

        outPassword=outPassword.substring(4);
        System.out.println("outPassword="+outPassword);
        return outPassword;

    }

    private String[] devidePass(String passwordF) {
        if(passwordF.length()%2!=0)
            passwordF=passwordF+".";
        System.out.println(passwordF.length());
        String []bigram=new String[(int) Math.ceil(passwordF.length()/2)+1];
        boolean check=true;
        int k=0;
        String temp = null;
        for (int i = 0; i < passwordF.length(); i++) {
            if (i%2==0){
                temp=String.valueOf(passwordF.charAt(i));
            }else{
                temp=temp+String.valueOf(passwordF.charAt(i));
                bigram[k]=temp;
                k++;
            }
        }

        error("Біграми поділені");

        return bigram;
    }


    void fillMatrix(char[][] matrixPassword){
        GridPane.setGridLinesVisible(true);
        for (int i = 0; i < matrixPassword.length; i++) {
            for (int j = 0; j < matrixPassword[i].length; j++) {
                GridPane.add(new Label(String.valueOf(matrixPassword[i][j])),j,i);
            }
        }
        error("Матриця виведена ");
    }


    public void error(String s) {
        contentOut.setText(contentOut.getText()+s+"\n");
    }
}
