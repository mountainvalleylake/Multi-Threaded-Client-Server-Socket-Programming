package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

/**
 * Created by graphics on 3/15/2017.
 */
public class Client1Controller {
    ClientGUI clientGUI;
    String filePath,filename;
    String dirPath;
    int maxnum, maxsize;
    @FXML
    private Button filePicker;
    @FXML
    private Button syncDir;
    @FXML
    private TextField textfile;
    @FXML
    private TextField textdir;
    @FXML
    private ListView<String> ListFile;
    public void set1ClientGUI(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
    }
    public void setMaxnum(int maxnum){
        this.maxnum = maxnum;
        //show it into the GUI
    }
    public void setMaxsize(int maxsize){
        this.maxsize = maxsize;
        //show it into the GUI
    }
    public void getFilePicker(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(clientGUI.stage1);
        filePath = selectedFile.getAbsolutePath();
        filename = selectedFile.getName();
        textfile.setText(filePath);
        clientGUI.SetFilePath(filePath,filename);
        System.out.println("Get File Picker :) ");
        //change the scene
    }
    public void getDirChooser(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(clientGUI.stage1);
        dirPath = selectedDirectory.getAbsolutePath();
        textdir.setText(dirPath);
        clientGUI.SetDirectoryPath(dirPath);
        System.out.println("Get Dir Picker :( ");
        //change the scene
    }

    public void exitfired(ActionEvent event) {
        clientGUI.clientExit();
    }

    public void redoFired(ActionEvent event) {
    }
}
