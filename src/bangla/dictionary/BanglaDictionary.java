package bangla.dictionary;

import java.io.IOException;
import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BanglaDictionary extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
       
        
        Scene scene = new Scene(root,640,400);
        stage.setScene(scene);
        
        stage.setTitle("English To Bangla Dictionary");
        
        stage.setResizable(false);
        root.setStyle("-fx-padding:10;"+
                "-fx-border-style:solid;"+
                "-fx-border-insects:20;"+
                "-fx-border-width: 2;"+
                "-fx-border-radius: 4;"+
                "-fx-border-color: gray;");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

}
