package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ConToClient implements Runnable {
    private int port = 8000;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private ServerSocket serverSocket;
    private ConToClientListener listener;

    public interface ConToClientListener{
        void received(BMI bmi);
        void displayMessage(String message);
    }

    public ConToClient(ConToClientListener listener) {
        this.listener = listener;
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            listener.displayMessage(new Date() + ": Server started");
            socket = serverSocket.accept();
            listener.displayMessage(new Date() + ": Connected to client");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(true){
                BMI bmi = (BMI) in.readObject();
                double calculatedBmi = bmi.getWeight() / Math.pow(bmi.getHeight() / 100, 2);
                bmi.setBMI(calculatedBmi);
                if(calculatedBmi < 18.5)
                    bmi.setStatus("Underweight");
                else if (calculatedBmi < 25)
                    bmi.setStatus("Perfect weight");
                else if (calculatedBmi < 30)
                    bmi.setStatus("overWeight");
                else
                    bmi.setStatus("Obese");
                out.writeObject(bmi);
                listener.received(bmi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
