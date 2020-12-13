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
    /**
     * This method is used when the 'exit' button of the GUI is pressed
     * @param createAccount
     */
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

    /**
     * This method is used in the login window. Checks if username and password both exist and match each other.
     * @param loginEvent
     * @throws Exception
     */
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

    /**
     * This method is used when the 'Create Account' button is pressed in the login window. Creates the 'Create Account' window.
     * @param createAccountEvent
     * @throws Exception
     */
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



    /**
     * This method is used in the create account window. Checks if both passwords entered match each other and if so,
     * creates a new username and password.
     * @param createAccountButton
     * @throws Exception
     */
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
    /**
     * Used when "Owner" button is pressed in the main menu window. Launches owners window.
     * @param ownerBTN
     * @throws Exception
     */
    public void ownersButton(ActionEvent ownerBTN) throws Exception
    {
        Stage owners = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("owners.fxml"));
        owners.setTitle("CS4013 Project - Owners");
        owners.setScene(new Scene(root, 900, 600));
        owners.show();
    }

    /**
     * Used when "Property" button is pressed in main menu window. Launches properties window.
     * @param propertyBTN
     * @throws Exception
     */
    public void propertyButton(ActionEvent propertyBTN) throws Exception
    {
        Stage properties = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("properties.fxml"));
        properties.setTitle("CS4013 Project - Properties");
        properties.setScene(new Scene(root, 600, 450));
        properties.show();
    }

    /**
     * Used when "Tax" button is pressed in main menu window. Launches tax window.
     * @param taxBTN
     * @throws Exception
     */
    public void taxButton(ActionEvent taxBTN) throws Exception
    {
        Stage tax = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("tax.fxml"));
        tax.setTitle("CS4013 Project - Tax");
        tax.setScene(new Scene(root, 900, 600));
        tax.show();
    }


}






















