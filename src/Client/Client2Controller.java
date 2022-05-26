package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by graphics on 3/16/2017.
 */
public class Client2Controller {
    ClientGUI clientGUI;
    @FXML
    private Button reEntry;
    public void set2ClientGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
    }
    public void reEntryFired(ActionEvent event) {
        clientGUI.ShowFirstPage();
    }
}
