package bangla.dictionary;

import javafx.fxml.Initializable;
import java.awt.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.KeyCode;

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
    void selectedItems(MouseEvent event) throws SQLException {
        Eng eng = table.getSelectionModel().getSelectedItem();
        if (eng == null) {
            details.setText("Nothing");
        } else {
            String id;
            id = eng.getSerial();
            findWord(id);
        }
    }

    @FXML
    void selectedItems2(KeyEvent event) throws SQLException {

        if (event.getCode() == KeyCode.ENTER) {
            Eng eng = table.getSelectionModel().getSelectedItem();
            System.out.println("INDEX:" + eng.getSerial());
            if (eng == null) {
                details.setText("Nothing");
            } else {
                String id;
                id = eng.getSerial();
                findWord(id);
            }
        }
        if (event.getCode() == KeyCode.UP) {
            Eng eng = table.getSelectionModel().getSelectedItem();
            System.out.println("INDEX:" + eng.getSerial());
            if (eng == null) {
                details.setText("Nothing");
            } else {
                String id;
                id = eng.getSerial();
                findWord(id);
            }
        }

        if (event.getCode() == KeyCode.DOWN) {
            Eng eng = table.getSelectionModel().getSelectedItem();
            System.out.println("INDEX:" + eng.getSerial());
            if (eng == null) {
                details.setText("Nothing");
            } else {
                String id;
                id = eng.getSerial();
                findWord(id);
            }
        }

    }

    final ObservableList<Eng> data = FXCollections.observableArrayList();
    FilteredList<Eng> filteredData = new FilteredList<>(data, b -> true);

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
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(eng -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (eng.getWord().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
                                else{
                                    return false; 
                                }// Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Eng> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table.setItems(sortedData);

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

    private void findWord(String id) throws SQLException {

        String query = "select * from other where serial =" + id;
        PreparedStatement pst = conn.prepareStatement(query);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String word = rs.getString("word");
            details.setText(word);
        }

    }

}
