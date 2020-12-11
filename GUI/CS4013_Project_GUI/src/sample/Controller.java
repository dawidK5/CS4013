package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller
{



    @FXML
    private Label statusLabel;

    @FXML
    private TextField usernameField1;

    @FXML
    private TextField passwordField1;

    @FXML
    private javafx.scene.control.Button closeButton;

    public void LoginAttempt(ActionEvent login) throws Exception
    {
        if(usernameField1.getText().equals("") && passwordField1.getText().equals(""))
        {
            statusLabel.setText("Enter Username and Password");
        }
        else if(usernameField1.getText().equals(""))
        {
            statusLabel.setText("Enter Username");
        }
        else if(passwordField1.getText().equals(""))
        {
            statusLabel.setText("Enter Password");
        }
        else if(usernameField1.getText().equals("user") && passwordField1.getText().equals("password"))
        {
            statusLabel.setText("Login Successful!");


            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            primaryStage.setTitle("CS4013 Project - Main Menu");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();

        }
        else
        {
            statusLabel.setText("Incorrect Username or Password");
        }

    }

    public void CreateAccount(ActionEvent createAccount) throws Exception
    {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        primaryStage.setTitle("CS4013 Project - Create Account");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


}






















