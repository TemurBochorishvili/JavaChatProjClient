package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Controller {

    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    Button button;
    private Socket socket;

    public Controller() {
    }

    @FXML
    public void initialize(){

        try{
            socket = new Socket("localhost", 5555);

            Thread receive = new Thread(()->{

                try{

                    Scanner scanner = new Scanner(socket.getInputStream());

                    while(scanner.hasNextLine()){

                        String area = textArea.getText();
                        textArea.setText(area + "\n" + scanner.nextLine());
                        textField.setText("");
                    }



                }catch (IOException e) {
                    System.out.println("oe bijo");
                }
            });

            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        onClick();
    }

    @FXML
    void onClick(){

        button.setOnAction((ActionEvent event) -> {

            String textFieldText = textField.getText();
            String textAreaText = textArea.getText();
            Thread send;
            Thread receive;

            send = new Thread(()->{

                try{

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);

                    printWriter.println(textFieldText);

                    printWriter.flush();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });

            send.start();



        });
    }
}
