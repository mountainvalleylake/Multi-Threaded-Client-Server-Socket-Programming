package Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Client0Controller {
    ClientGUI clientGUI;
    @FXML
    private TextField IP;
    @FXML
    private TextField StdID;
    @FXML
    private TextField port;
    @FXML
    private Button submit;
    @FXML
    public void getStudentInfo(ActionEvent event) {
        String studentID = StdID.getText();
        String IPAddress = IP.getText();
        String ports = port.getText();
        int StID = Integer.parseInt(studentID);
        int port = Integer.parseInt(ports);
        ClientSocketHandler clientSocketHandler = new ClientSocketHandler(port,StID,IPAddress,clientGUI);
        clientGUI.SetClientSocketHandler(clientSocketHandler);
        System.out.println("outta blackhole and set it");
        //boolean validity = clientGUI.IDValidity();
        boolean validity = true;
        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                if(validity){
                    clientGUI.ShowSecondPage();
                }
                else{
                    clientGUI.ShowFirstPage();
                }
            }
        });
    }
    public void set0ClientGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
    }
}
