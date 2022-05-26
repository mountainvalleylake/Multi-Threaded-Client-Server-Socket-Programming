package Client;

import javafx.event.ActionEvent;

/**
 * Created by graphics on 3/16/2017.
 */
public class Client3Controller {
    ClientGUI clientGUI;
    public void set3ClientGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
    }

    public void exitClicked(ActionEvent event) {
        clientGUI.clientExit();
    }

    public void UploaDBack(ActionEvent event) {
        clientGUI.ShowSecondPage();
    }
}
