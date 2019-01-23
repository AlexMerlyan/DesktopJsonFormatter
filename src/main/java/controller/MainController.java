package controller;

import formatter.json.JsonFormatterImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class MainController {

    private JsonFormatterImpl jsonFormatter = new JsonFormatterImpl();

    @FXML
    private TextArea textArea;

    @FXML
    private Button compile;

    @FXML
    private Button format;

    public void formatCode() {
        String json = textArea.getText();
        textArea.setText(jsonFormatter.formatJson(json));
    }

}
