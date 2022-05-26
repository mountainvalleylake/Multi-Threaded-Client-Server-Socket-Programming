package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class ClientGUI extends Application {
    Stage stage0,stage1,stage2,stage3;
    Client0Controller client0Controller;
    Client1Controller client1Controller;
    Client2Controller client2Controller;
    Client3Controller client3Controller;
    ClientSocketHandler clientSocketHandler;
    boolean valid;
    String filePath, filename;
    String dirPath;
    int maxnum, maxsize;
    public void clientExit(){
        clientSocketHandler.CleanUp();
        exit(0);
    }
    public void SetFilePath(String filePath,String filename){
        this.filePath = filePath;
        this.filename = filename;
        clientSocketHandler.SetFilePath(filePath,filename);
        boolean perm = clientSocketHandler.FileUpLoader();
        if(!perm){
            ShowUploadWarningPage();
        }
    }
    public String GetFilePath(){
        return filePath;
    }
    public void SetDirectoryPath(String dirPath){
        this.dirPath = dirPath;
        clientSocketHandler.SetDirPath(dirPath);
        boolean perm = clientSocketHandler.DirectoryUpload();
        if(!perm){
            ShowUploadWarningPage();
        }
    }
    public String GetDirectoryPath(){
        return dirPath;
    }
    public void SetClientSocketHandler(ClientSocketHandler clientSocketHandler){
        this.clientSocketHandler = clientSocketHandler;
    }
    public void SetMaxNum(int maxnum){
        this.maxnum = maxnum;
    }
    public int GetMaxNum(){
        return maxnum;
    }
    public void SetMaxSize(int maxsize){
        this.maxsize = maxsize;
    }
    public int GetMaxSize(){
        return maxsize;
    }
    public void IDValidity(){
        //valid = clientSocketHandler.GetIDValid();
        ShowIDWarningPage();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage0 = primaryStage;
        ShowFirstPage();
    }
    public void ShowFirstPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Client0.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client0Controller = loader.getController();
        client0Controller.set0ClientGUI(this);

        stage0.setTitle("Specification");
        stage0.setScene(new Scene(root));
        stage0.show();
    }
    public void ShowSecondPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Client1.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client1Controller = loader.getController();
        client1Controller.set1ClientGUI(this);
        client1Controller.setMaxnum(maxnum);
        client1Controller.setMaxsize(maxsize);
        stage1 = stage0;
        stage1.setTitle("Choose File");
        stage1.setScene(new Scene(root));
        stage1.show();
    }
    public void ShowIDWarningPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Client2.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client2Controller = loader.getController();
        client2Controller.set2ClientGUI(this);
        stage2 = new Stage();
        stage2.setTitle("Invalid ID");
        stage2.setScene(new Scene(root));
        stage2.show();
    }
    public void ShowUploadWarningPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Client3.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client3Controller = loader.getController();
        client3Controller.set3ClientGUI(this);
        stage3 = new Stage();
        stage3.setTitle("Error");
        stage3.setScene(new Scene(root));
        stage3.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
