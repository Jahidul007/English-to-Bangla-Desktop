package bangla.dictionary;

import javafx.fxml.Initializable;
import java.awt.Image;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class FXMLController implements Initializable {

    Connection conn = ConnectionDb.java_db();

    @FXML
    TableView<Eng> table;

    @FXML
    private TableColumn<Eng, String> wordlist;

    @FXML
    private TextField text;

    @FXML
    private TextArea details;

    @FXML
    private Button button;

    @FXML
    private TextField searchField;

    @FXML
    void onClick(ActionEvent event) {

        System.out.println("ha ha ");
    }
    
    @FXML
    void selectedItems(MouseEvent event) throws SQLException{
        Eng eng = table.getSelectionModel().getSelectedItem();
        if (eng== null){
            details.setText("NOthing");
        } else{
            String id;
            id = eng.getSerial();
            findWord(id);
        }
    }
    @FXML
    void selectedItems(KeyEvent event) throws SQLException{
        Eng eng = table.getSelectionModel().getSelectedItem();
        if (eng== null){
            details.setText("NOthing");
        } else{
            String id;
            id = eng.getSerial();
            findWord(id);
        }
    }
    
    
    
     
    final ObservableList<Eng> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO

            wordlist.setCellValueFactory(new PropertyValueFactory<>("word"));
            table.setItems(null);
            table.setItems(data);
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void loadData() throws SQLException {
        String query = "select * from eng";
        PreparedStatement pst = conn.prepareStatement(query);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String serial = rs.getString("serial");
            String word = rs.getString("word");
            data.add(new Eng(serial, word));
        }

    }
    private void findWord(String id) throws SQLException{
        
        String query = "select * from other where serial ="+id;
        PreparedStatement pst = conn.prepareStatement(query);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String word = rs.getString("word");
            details.setText(word);
        }
        
    }

}
