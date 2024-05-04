
package supermarketmanagementsystem;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Atlas
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane employee_form;

    @FXML
    private AnchorPane admin_form;

    @FXML
    private Hyperlink admin_hyperLink;

    @FXML
    private Button admin_loginBtn;

    @FXML
    private PasswordField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private Hyperlink employee_hyperLink;

    @FXML
    private TextField employee_id;

    @FXML
    private Button employee_loginBtn;

    @FXML
    private PasswordField employee_password;

    @FXML
    private AnchorPane main_form;

    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;

    public void employeeLogin() {
        String employeeData = "SELECT * FROM employee WHERE employee_id = ? and password = ?";
        
        connect = database.connectDb();
        
        try {
            Alert alert;

            prepare = connect.prepareStatement(employeeData);

            if (employee_id.getText().isEmpty()
                    || employee_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all the blanks");
                alert.showAndWait();

            }else{
                prepare.setString(1, employee_id.getText());
                prepare.setString(2, employee_password.getText());
                
                result = prepare.executeQuery();

                if (result.next()) {
                    
                    getData.employeedId = employee_id.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Login!");
                    alert.showAndWait();
                    
                    employee_loginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("employeeDashboard.fxml"));
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong EmployeeID/Password");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {e.printStackTrace();}

    }

    public void adminLogin() {

        String adminData = "SELECT * FROM admin WHERE username = ? and password = ?";

        connect = database.connectDb();

        try {
            Alert alert;
            if (admin_username.getText().isEmpty()
                    || admin_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all the blanks");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(adminData);
                prepare.setString(1, admin_username.getText());
                prepare.setString(2, admin_password.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    
                    getData.username = admin_username.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Login!");
                    alert.showAndWait();
                    admin_loginBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username/password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == admin_hyperLink) {
            admin_form.setVisible(false);
            employee_form.setVisible(true);
        }
    }

    public void switchForm1(ActionEvent event) {
        if (event.getSource() == employee_hyperLink) {
            admin_form.setVisible(true);
            employee_form.setVisible(false);
        }
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
