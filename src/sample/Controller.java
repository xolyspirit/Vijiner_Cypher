package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Controller {

    private Service service = new Service();

    @FXML
    TextArea originalText;

    @FXML
    TextArea encodeText;

    @FXML
    TextArea decodeText;

    @FXML
    TextField keyWord;
    @FXML
    Label keyWordLength;

    @FXML
    TableView<Lgram> tableView;
    @FXML
    TableColumn<Lgram, String> lGram;
    @FXML
    TableColumn<Lgram, String> distances;
    @FXML
    TableColumn<Lgram, Integer> gcd;

    @FXML
    public void encode() {
        String text = originalText.getText();
        String key = keyWord.getText();
        if (key != null && text != null) {
            encodeText.setText(service.encode(text, key));
        }
    }

    @FXML
    public void decode() {
        String text = encodeText.getText();
        String key = keyWord.getText();
        if (text != null)
            decodeText.setText(service.decode(text, key));
    }

    @FXML
    public void testKasiski() {
        lGram.setCellValueFactory(new PropertyValueFactory<Lgram, String>("lGram"));
        distances.setCellValueFactory(new PropertyValueFactory<Lgram, String>("distances"));
        gcd.setCellValueFactory(new PropertyValueFactory<Lgram, Integer>("gcd"));
        List<Lgram> lgramList = service.analyzeTextByKasiski(encodeText.getText());
        if (lgramList.size() > 0){
            keyWordLength.setText(service.getMinValueOfGcd(lgramList).toString());
        } else {
            keyWordLength.setText("");
        }

        ObservableList<Lgram> lgrams = FXCollections.observableArrayList(lgramList);
        tableView.setItems(lgrams);
        tableView.setEditable(false);
    }

}
