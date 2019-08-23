package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerController implements ConToClient.ConToClientListener {
    @FXML
    TextArea serverTextArea;

    public void initialize(){
        serverTextArea.setEditable(false);
        new Thread(new ConToClient(this)).start();
    }


    @Override
    public void received(BMI bmi) {
        serverTextArea.appendText("Weight: " + bmi.getWeight() + "\nHeight: " + bmi.getHeight() + "\nBMI is " + bmi.getBMI() + ". " + bmi.getStatus() + "\n");
    }

    @Override
    public void displayMessage(String message) {
        serverTextArea.appendText(message + "\n");
    }
}
