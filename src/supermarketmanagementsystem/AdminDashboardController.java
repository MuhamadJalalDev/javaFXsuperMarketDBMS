
package supermarketmanagementsystem;



import java.sql.*;
import javafx.collections.transformation.FilteredList;
import javafx.beans.value.ObservableValue;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Atlas
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private Button addProduct_addBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField addProduct_brandName;

    @FXML
    private Button addProduct_clearBtn;

    @FXML
    private TableColumn<productData, String> addProduct_col_brandName;

    @FXML
    private TableColumn<productData, String> addProduct_col_price;

    @FXML
    private TableColumn<productData, String> addProduct_col_productID;

    @FXML
    private TableColumn<productData, String> addProduct_col_productName;

    @FXML
    private TableColumn<productData, String> addProduct_col_status;

    @FXML
    private Button addProduct_deleteBtn;

    @FXML
    private AnchorPane addProduct_form;

    @FXML
    private TextField addProduct_price;

    @FXML
    private TextField addProduct_productID;

    @FXML
    private TextField addProduct_productName;

    @FXML
    private TextField addProduct_search;

    @FXML
    private ComboBox<?> addProduct_status;

    @FXML
    private TableView<productData> addProduct_tableView;

    @FXML
    private Button addProduct_updateBtn;

    @FXML
    private Button addProducts_btn;

    @FXML
    private Button close;

    @FXML
    private Label dashboard_IncomeToday;

    @FXML
    private Label dashboard_activeEmployees;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Button employee_btn;

    @FXML
    private Button employees_clearBtn;

    @FXML
    private TableColumn<employeeData, String> employees_col_date;

    @FXML
    private TableColumn<employeeData, String> employees_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> employees_col_firstName;

    @FXML
    private TableColumn<employeeData, String> employees_col_gender;

    @FXML
    private TableColumn<employeeData, String> employees_col_lastName;

    @FXML
    private TableColumn<employeeData, String> employees_col_password;

    @FXML
    private Button employees_deleteBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private TextField employees_firstName;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TextField employees_password;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private TableView<employeeData> employees_tableView;

    @FXML
    private Button employees_updateBtn;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void addProductsAdd() {

        String insertProduct = "INSERT INTO product "
                + "(product_id, brand, product_name, status, price) "
                + "VALUES (?, ?, ?, ?, ?) ";

        connect = database.connectDb();
        try {
            Alert alert;

            if (addProduct_productID.getText().isEmpty()
                    || addProduct_brandName.getText().isEmpty()
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_status.getSelectionModel().getSelectedItem() == null
                    || addProduct_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT product_id FROM product WHERE product_id = '"
                        + addProduct_productID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + addProduct_productID.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertProduct);
                    prepare.setString(1, addProduct_productID.getText());
                    prepare.setString(2, addProduct_brandName.getText());
                    prepare.setString(3, addProduct_productName.getText());
                    prepare.setString(4, (String) addProduct_status.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addProduct_price.getText());

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addProductsShowData();

                    addProductsClear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsUpdate() {
        String updateProduct = "UPDATE product SET brand = '"
                + addProduct_brandName.getText() + "', product_name = '"
                + addProduct_productName.getText() + "', status = '"
                + addProduct_status.getSelectionModel().getSelectedItem() + "', price = '"
                + addProduct_price.getText() + "' WHERE product_id = '"
                + addProduct_productID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addProduct_productID.getText().isEmpty()
                    || addProduct_brandName.getText().isEmpty()
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_status.getSelectionModel().getSelectedItem() == null
                    || addProduct_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + addProduct_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateProduct);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addProductsShowData();

                    addProductsClear();

                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsDelete() {
        String deleteProduct = "DELETE FROM product WHERE product_id = '"
                + addProduct_productID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addProduct_productID.getText().isEmpty()
                    || addProduct_brandName.getText().isEmpty()
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_status.getSelectionModel().getSelectedItem() == null
                    || addProduct_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product ID: " + addProduct_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteProduct);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addProductsShowData();

                    addProductsClear();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsClear() {
        addProduct_productID.setText("");
        addProduct_brandName.setText("");
        addProduct_productName.setText("");
        addProduct_status.getSelectionModel().clearSelection();
        addProduct_price.setText("");
    }
    private String[] statusList = {"Available", "Not Available"};

    public void addProductsStatusList() {
        List<String> listS = new ArrayList<>();

        for (String data : statusList) {
            listS.add(data);
        }

        ObservableList statusData = FXCollections.observableArrayList(listS);
        addProduct_status.setItems(statusData);
    }
    


    public ObservableList<productData> addProductsListData() {
        ObservableList<productData> prodList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = database.connectDb();

        try {
            productData prod;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                prod = new productData(result.getString("product_id"),
                        result.getString("brand"),
                        result.getString("product_name"),
                        result.getString("status"),
                        result.getDouble("price"));

                prodList.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prodList;
    }

    private ObservableList<productData> addProductsList;

    public void addProductsShowData() {
        addProductsList = addProductsListData();

        addProduct_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProduct_col_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProduct_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProduct_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addProduct_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProduct_tableView.setItems(addProductsList);
    }

    public void addProductsSelect() {
        productData prod = addProduct_tableView.getSelectionModel().getSelectedItem();
        int num = addProduct_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        addProduct_productID.setText(prod.getProductId());
        addProduct_brandName.setText(prod.getBrand());
        addProduct_productName.setText(prod.getProductName());
        addProduct_price.setText(String.valueOf(prod.getPrice()));
    }
    
    public void employeesSave() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String insertEmployee = "INSERT INTO employee "
                + "(employee_id,password,firstName,lastName,gender,date) "
                + "VALUES (?, ?, ?, ?, ?, ?) ";

        connect = database.connectDb();

        try {
            Alert alert;
            if (employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                String checkExist ="SELECT employee_id FROM employee WHERE employee_id = '"
                        +employees_employeeID+"'";
                
                statement = connect.createStatement();
                result = statement.executeQuery(checkExist);
                
                if(result.next()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Employee ID: "+ employees_employeeID.getText() + "was already exist!");
                alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(insertEmployee);
                    prepare.setString(1, employees_employeeID.getText());
                    prepare.setString(2, employees_password.getText());
                    prepare.setString(3, employees_firstName.getText());
                    prepare.setString(4, employees_lastName.getText());
                    prepare.setString(5, (String) employees_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Saved!");
                    alert.showAndWait();
                    
                    employeesShowListData();
                    
                    employeesReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String[] genderList = {"Male","Female"};
    public void employeesGender() {
        List<String> genderL = new ArrayList<>();
        
        for(String data: genderList){
            genderL.add(data);
        }
        ObservableList listG = FXCollections.observableArrayList(genderL);
        employees_gender.setItems(listG);
    }
    public void employeesUpdate() {
        String updateEmployee = "UPDATE employee SET password = '"
                + employees_password.getText() + "', firstName = '"
                + employees_firstName.getText() + "', lastName = '"
                + employees_lastName.getText() + "', gender = '"
                + employees_gender.getSelectionModel().getSelectedItem()
                + "'WHERE employee_id = '"+employees_employeeID.getText()+"'";

        connect = database.connectDb();
        
        try{
            Alert alert;

            if (employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE "+ employees_employeeID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateEmployee);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully updated!");
                    alert.showAndWait();
                    
                    employeesShowListData();
                    employeesReset();
                } else {
                    return;

                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
    public void employeesDelete() {
        String deleteEmployee = "DELETE FROM employee WHERE employee_id = '"
                + employees_employeeID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE "+ employees_employeeID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteEmployee);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully deleted!");
                    alert.showAndWait();
                    
                    employeesShowListData();
                    employeesReset();
                }else return;
            }
            
        }catch(Exception e){e.printStackTrace();}
    }
    public void employeesReset(){
        employees_employeeID.setText("");
        employees_password.setText("");
        employees_firstName.setText("");
        employees_lastName.setText("");
        employees_gender.getSelectionModel().clearSelection();
    }
    
    public ObservableList<employeeData> employeesListData () {
        ObservableList<employeeData> emData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee";

        connect = database.connectDb();
        
        try{
            employeeData employeeD;
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                employeeD = new employeeData(result.getString("employee_id")
                        , result.getString("password")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("gender")
                        , result.getDate("date"));
                
                emData.add(employeeD);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return emData;
    }
    
    private ObservableList<employeeData> employeesList;
    public void employeesShowListData(){
        employeesList = employeesListData();
        
       employees_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
       employees_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
       employees_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
       employees_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
       employees_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
       employees_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
       
       employees_tableView.setItems(employeesList);
    }
    public void employeesSelect(){
        employeeData employeeD = employees_tableView.getSelectionModel().getSelectedItem();
        int num = employees_tableView.getSelectionModel().getSelectedIndex();
        
        if((num-1)< -1){
            return;
        }
        employees_employeeID.setText(employeeD.getEmployeeId());
        employees_password.setText(employeeD.getPassword());
        employees_firstName.setText(employeeD.getFirstName());
        employees_lastName.setText(employeeD.getLastName());
    }

    public void logout() {
        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confiramtion Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProduct_form.setVisible(false);
            employees_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to top right, #23cf9f, #3faba1);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employee_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProduct_form.setVisible(true);
            employees_form.setVisible(false);

            addProducts_btn.setStyle("-fx-background-color:linear-gradient(to top right, #23cf9f, #3faba1);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            employee_btn.setStyle("-fx-background-color:transparent");

            addProductsShowData();
            addProductsStatusList();

        } else if (event.getSource() == employee_btn) {
            dashboard_form.setVisible(false);
            addProduct_form.setVisible(false);
            employees_form.setVisible(true);

            employee_btn.setStyle("-fx-background-color:linear-gradient(to top right, #23cf9f, #3faba1);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            
            employeesShowListData();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();

        addProductsShowData();
        addProductsStatusList();
        
        employeesShowListData();
        employeesGender();
    }

}
