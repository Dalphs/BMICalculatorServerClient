package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConToServer implements Runnable {

    private int port = 8000;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private String host = "localhost";
    private ConToServerListener listener;
    private BMI bmi;

    public interface ConToServerListener{
        void answer(BMI bmi);
    }

    public ConToServer(ConToServerListener listener) {
        this.listener = listener;
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.writeObject(bmi);
            bmi = (BMI) in.readObject();
            listener.answer(bmi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BMI getBmi() {
        return bmi;
    }

    public void setBmi(BMI bmi) {
        this.bmi = bmi;
    }
}
