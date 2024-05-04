
package supermarketmanagementsystem;

import java.sql.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
public class EmployeeDashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Button P_close;
    @FXML
    private Label purchase_employee;
    @FXML
    private Button logout;
    @FXML
    private TextField purchase_brand;
    @FXML
    private ComboBox<?> purchase_productName;
    @FXML
    private Spinner<Integer> purchase_quantity;
    @FXML
    private Button purchase_addBtn;
    @FXML
    private Label purchase_total;
    @FXML
    private Button purchase_pay;
    @FXML
    private Button purchase_receiptBtn;
    @FXML
    private TableView<customerData> purchase_tableView;
    @FXML
    private TableColumn<customerData, String> purchase_col_brand;
    @FXML
    private TableColumn<customerData, String> purchase_col_productName;
    @FXML
    private TableColumn<customerData, String> purchase_col_quantity;
    @FXML
    private TableColumn<customerData, String> purchase_col_price;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    public void purchaseSearch(){
        
        String searchB = "SELECT * FORM product WHERE brand = '"
                +purchase_brand.getText()+"' and status = 'Available'";
        
        connect = database.connectDb();
        
        try{
            
            prepare = connect.prepareStatement(searchB);
            result = prepare.executeQuery();
            
            ObservableList listProduct = FXCollections.observableArrayList();
            
            while(result.next()){
                listProduct.add(result.getString("product_name"));
            }
            purchase_productName.setItems(listProduct);
            
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private SpinnerValueFactory spinner;
    
    public void purchaseSpinner(){                                     //min max display
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,0);
        
        purchase_quantity.setValueFactory(spinner);
    }
    
    private int qty;
    public void purchaseSpinnerValue(){
        qty = purchase_quantity.getValue();
        
       // System.out.println(qty);
    }
    
    public ObservableList<customerData> purchaseListData(){
        ObservableList<customerData> customerList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";
        
        connect = database.connectDb();
        try{
            customerData custD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                custD = new customerData(result.getInt("customer_id")
                        , result.getString("brand")
                        , result.getString("productName")
                        , result.getInt("quantity")
                        ,result.getDouble("price")
                        ,result.getDate("date"));
                
                customerList.add(custD);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return customerList;
    }
    
    private ObservableList<customerData> purchaseList;
    public void purchaseShowListData(){
        purchaseList = purchaseListData();
        
        purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        purchase_tableView.setItems(purchaseList);
    }
    
    private int customerId;
    
    public void purchaseCustomer(){
        
        String cID = "SELECT customer_id FROM customer";
        
        connect = database.connectDb();
        try{
            prepare = connect.prepareStatement(cID);
            result = prepare.executeQuery();
            
            while(result.next()){
                //final row customerid
                customerId = result.getInt("customer_id");
            }
            int checkNum = 0;
            
            String checkCustomerId = "SELECT customer_id FROM customer_receipt";
            
            statement = connect.createStatement();
            result = statement.executeQuery(checkCustomerId);
            
            while(result.next()){
                checkNum = result.getInt("customer_id");
            }
            
            if(customerId == 0){
                customerId+=1;
            }else if(checkNum == customerId){
                customerId+=1;
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
     public void logout() {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

    public void displayEmployeeName(){
        purchase_employee.setText(getData.employeedId);
    }
    public void close(){
        System.exit(0);
    }
    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayEmployeeName();
        
        purchaseShowListData();
        purchaseSpinner();
        
        purchaseSpinnerValue();
    }    

    
    
}
