package bangla.dictionary;

import javafx.fxml.Initializable;
import java.awt.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import com.gtranslate.Audio;
import com.gtranslate.Language;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.event.EventHandler;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javazoom.jl.decoder.JavaLayerException;

public class FXMLController implements Initializable {

    Connection conn = ConnectionDb.java_db();

    @FXML
    private Text mainWord;
    @FXML
    TableView<Eng> table;

    @FXML
    private TableColumn<Eng, String> wordlist;

    @FXML
    private TextField text;

    @FXML
    private TextArea details;

    @FXML
    private TextArea detailsExam;

    @FXML
    private TextArea detailsDef;

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
        System.out.println("it's work");
        if (eng == null) {
            details.setText("Nothing");
        } else {
            String id;
            id = eng.getSerial();
            findWord(id);
        }
    }
    @FXML
    void speechEvent(MouseEvent event) {
        // add speech
        Eng eng = new Eng();
        InputStream sound = null;
        Audio audio = Audio.getInstance();
        try {
            sound = audio.getAudio("speech", Language.ENGLISH);
            audio.play(sound);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    void keypress(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.DOWN) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    table.requestFocus();
                    table.getSelectionModel().select(0);
                    table.getFocusModel().focus(0);
                    Eng eng = table.getSelectionModel().getSelectedItem();
                    if (eng == null) {
                        details.setText("Nothing");
                    } else {
                        String id;
                        id = eng.getSerial();
                        try {
                            findWord(id);
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        }

        if (event.getCode() == KeyCode.ENTER) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    table.requestFocus();
                    table.getSelectionModel().select(0);
                    table.getFocusModel().focus(0);
                    Eng eng = table.getSelectionModel().getSelectedItem();
                    if (eng == null) {
                        details.setText("Nothing");
                    } else {
                        String id;
                        id = eng.getSerial();
                        try {
                            findWord(id);
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        }

        System.out.println("work");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                table.getSelectionModel().select(0);
                table.getFocusModel().focus(0);
                Eng eng = table.getSelectionModel().getSelectedItem();
                if (eng == null) {
                    details.setText("Nothing");
                } else {
                    String id;
                    id = eng.getSerial();
                    try {
                        findWord(id);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    final ObservableList<Eng> data = FXCollections.observableArrayList();
    FilteredList<Eng> filteredData = new FilteredList<>(data, b -> true);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                table.requestFocus();
                table.getSelectionModel().select(0);
                table.getFocusModel().focus(0);
                Eng eng = table.getSelectionModel().getSelectedItem();
                if (eng == null) {
                    details.setText("Nothing");
                } else {
                    String id;
                    id = eng.getSerial();
                    try {
                        findWord(id);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        try {
            // TODO
            wordlist.setCellValueFactory(new PropertyValueFactory<>("word"));
            table.setItems(null);
            table.setItems(data);
            loadData();
            table.getSelectionModel().select(4);
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

                if (eng.getWord().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else {
                    return false;
                }// Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Eng> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        //sortedData.comparatorProperty().bind(table.comparatorProperty());
        Comparator<Eng> byName = new Comparator<Eng>() {
            @Override
            public int compare(Eng o1, Eng o2) {
                String searchKey = searchField.getText().toLowerCase();
                int item1Score = findScore(o1.getWord().toLowerCase(), searchKey);
                int item2Score = findScore(o2.getWord().toLowerCase(), searchKey);

                if (item1Score > item2Score) {
                    return -1;
                }

                if (item2Score > item1Score) {
                    return 1;
                }

                return 0;
            }

            private int findScore(String itemName, String searchKey) {
                int sum = 0;
                if (itemName.startsWith(searchKey)) {
                    sum += 2;
                }

                if (itemName.contains(searchKey)) {
                    sum += 1;
                }
                return sum;
            }
        };

        sortedData.setComparator(byName);

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

        String query1 = "select * from eng where serial =" + id;
        PreparedStatement pst1 = conn.prepareStatement(query1);

        ResultSet rs1 = pst1.executeQuery();
        String word1 = rs1.getString("word");

        mainWord.setText(word1);
        
//        mainWord.setOnMouseClicked(evt -> {
//            if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
//                InputStream sound = null;
//                Audio audio = Audio.getInstance();
//                System.out.println("okkkkkkkkk");
//                try {
//                    sound = audio.getAudio("Hello World", Language.ENGLISH);
//                    audio.play(sound);
//                } catch (IOException ex) {
//                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (JavaLayerException ex) {
//                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//            }
//        });

        while (rs.next()) {
            String word = rs.getString("word");
            String def = rs.getString("def");
            String exm = rs.getString("exm");
            details.setText(word);
            details.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            details.setMaxWidth(313);

            detailsDef.setText(def);
            detailsDef.setFont(Font.font("Verdana", 16));
            detailsDef.setMaxWidth(313);
            detailsDef.setWrapText(true);

            detailsExam.setText(exm);
            detailsExam.setFont(Font.font("Verdana", 16));
            detailsExam.setMaxWidth(313);
            detailsExam.setWrapText(true);

        }

    }

}
