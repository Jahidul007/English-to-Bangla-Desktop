
package bangla.dictionary;

import javafx.fxml.Initializable;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLController implements Initializable{
    
    @FXML
    private TextField text;

    @FXML
    private ListView wordlist;

    @FXML
    private Button addbtn;

    @FXML
    private ScrollPane details;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }
}
