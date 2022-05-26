package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by graphics on 3/11/2017.
 */
public class ServerGUI extends Application{
    int maxFileNum,maxFileSize;
    ServerSocketHandler s;
    Server1Controller server1Controller;
    Server0Controller server0Controller;
    String rootPath;
    public ArrayList<String> langlist = new ArrayList<>();
    public Stage stage0,stage1;
    public void SetUpServerSocketHandler(){
        s = new ServerSocketHandler(this);
        //s.SetRootPathHandler(rootPath);
    }
    public void SetMaxFileNum(int maxFileNum){
        this.maxFileNum = maxFileNum;
    }
    public void SetMaxFileSize(int maxFileSize){
        this.maxFileSize = maxFileSize;
    }
    public int GetMaxFileNum(){
        return maxFileNum;
    }
    public int GetMaxFileSize(){
        return maxFileSize;
    }
    public ArrayList<String> GetLanguageList(){ return langlist; }
    public ServerSocketHandler GetServerSocketHandler(){
        return s;
    }
    public void SetRootPath(String rootPath){
        this.rootPath = rootPath;
    }
    public String GetRootPath(){
        return rootPath;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage0 = primaryStage;
        ShowFirstPage();
    }
    public void ShowFirstPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Server0.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        server0Controller = loader.getController();
        server0Controller.set0ServerGUI(this);

        stage0.setTitle("Specification");
        stage0.setScene(new Scene(root));
        stage0.show();

    }
    public void ShowNextPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Server1.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        server1Controller = loader.getController();
        server1Controller.setServerGUI(this);
        stage1 = stage0;
        stage1.setTitle("Directory");
        stage1.setScene(new Scene(root));
        stage1.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
