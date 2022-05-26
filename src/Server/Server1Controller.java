package Server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * Created by graphics on 3/11/2017.
 */
public class Server1Controller {
    private String rootPath;
    ServerGUI serverGUI;
    ServerSocketHandler serverSocketHandler;
    public void setServerGUI(ServerGUI serverGUI){
        this.serverGUI = serverGUI;
    }

    @FXML
    private Button DirectoryButton;
    @FXML
    private TextField directory;
    ObservableList<String> observableList = FXCollections.observableArrayList();

    public void chooseDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(serverGUI.stage1);
        rootPath = selectedDirectory.getAbsolutePath();
        directory.setText(rootPath);
//        serverSocketHandler = new ServerSocketHandler();
//        serverSocketHandler.SetRootPathHandler(rootPath);
        serverGUI.SetRootPath(rootPath);
        serverGUI.SetUpServerSocketHandler();
        System.out.println("Came Back From that blackhole");
        //serverGUI.ShowNextPage();
    }
}
