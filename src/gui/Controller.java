package gui;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.CSVHandler;

public class Controller
{
    public String username = "user";
    public String password = "password";
    Stage  login = new Stage();


    //Exit button:
    public void exitGUI(ActionEvent createAccount)
    {
        Platform.exit();
    }

    //Login Window:
    @FXML
    private Label statusLabel;

    @FXML
    private TextField usernameField1;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private javafx.scene.control.Button closeButton;

    public void LoginAttempt(ActionEvent loginEvent) throws Exception
    {
        CSVHandler csv = new CSVHandler();
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
        else if(csv.readFromOwners(usernameField1.getText()) != null && passwordField1.getText().equals(this.password))
        {
            statusLabel.setText("Login Successful!");


            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            login.setTitle("CS4013 Project - Main Menu");
            login.setScene(new Scene(root, 600, 450));
            login.show();

        }
        else
        {
            statusLabel.setText("Incorrect Username or Password");
        }

    }

    public void CreateAccount(ActionEvent createAccountEvent) throws Exception
    {

        Stage createAccount = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        createAccount.setTitle("CS4013 Project - Create Account");
        createAccount.setScene(new Scene(root, 600, 450));
        createAccount.show();

    }

    //Create Account Window:
    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField confirmPassword;



    public void CreateAccountButton(ActionEvent createAccountButton) throws Exception {
        if (newPassword.getText().equals(confirmPassword.getText())) {
            this.username = newUsername.getText();
            this.password = newPassword.getText();



            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            login.setTitle("CS4013 Project - Login");
            login.setScene(new Scene(root, 600, 450));
            login.show();
        }
    }

    //Main Menu Window:
    public void ownersButton(ActionEvent ownerBTN) throws Exception
    {
        Stage owners = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("owners.fxml"));
        owners.setTitle("CS4013 Project - Owners");
        owners.setScene(new Scene(root, 900, 600));
        owners.show();
    }

    public void propertyButton(ActionEvent propertyBTN) throws Exception
    {
        Stage properties = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("properties.fxml"));
        properties.setTitle("CS4013 Project - Properties");
        properties.setScene(new Scene(root, 600, 450));
        properties.show();
    }

    public void taxButton(ActionEvent taxBTN) throws Exception
    {
        Stage tax = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("tax.fxml"));
        tax.setTitle("CS4013 Project - Tax");
        tax.setScene(new Scene(root, 900, 600));
        tax.show();
    }


}






















