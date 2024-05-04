package supermarketmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Atlas
 */
public class SuperMarketManagementSystem extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * // * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
