package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Controller implements ConToServer.ConToServerListener {
    @FXML
    Button submitButton;
    @FXML
    TextField heightTextField;
    @FXML
    TextField weightTextField;
    @FXML
    TextArea logTextArea;
    private ConToServer conToServer;

    public void initialize(){
        logTextArea.setEditable(false);
        conToServer = new ConToServer(this);
    }

    public void submitPressed(ActionEvent actionEvent) {
        double height = Double.parseDouble(heightTextField.getText());
        double weight = Double.parseDouble(weightTextField.getText());
        logTextArea.appendText("Weight: " + weight + "\nHeight: " + height + "\n");
        BMI bmi = new BMI(height, weight);

        conToServer.setBmi(bmi);
        conToServer.run();
    }

    @Override
    public void answer(BMI bmi) {
        logTextArea.appendText("BMI is " + bmi.getBMI() + ". " + bmi.getStatus() + "\n");
    }
}
