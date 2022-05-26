package Server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Created by graphics on 3/15/2017.
 */
public class Server0Controller {
    ServerGUI serverGUI;
    @FXML
    private CheckBox allowC;
    @FXML
    private CheckBox allowCpp;
    @FXML
    private CheckBox allowJava;
    @FXML
    private CheckBox allowPython;
    @FXML
    private TextField maxSize;
    @FXML
    private TextField maxNum;
    @FXML
    private Button go;
    public void set0ServerGUI(ServerGUI serverGUI){
        this.serverGUI = serverGUI;
    }
    public void isAllowedC(ActionEvent event) {
         boolean selectedC = allowC.isSelected();
         if(selectedC){
             serverGUI.langlist.add("c");
             System.out.println("C selected");
         }
         else{
             serverGUI.langlist.remove("c");
             System.out.println("C removed");
         }
    }

    public void isAllowCpp(ActionEvent event) {
        boolean selectedCpp = allowCpp.isSelected();
        if(selectedCpp){
            serverGUI.langlist.add("cpp");
            System.out.println("Cpp Selected");
        }
        else{
            serverGUI.langlist.remove("cpp");
            System.out.println("Cpp removed");
        }
    }

    public void isAllowJava(ActionEvent event) {
        boolean selectedJava = allowJava.isSelected();
        if(selectedJava){
            serverGUI.langlist.add("java");
            System.out.println("Java Selected");
        }
        else{
            serverGUI.langlist.remove("java");
            System.out.println("Java removed");
        }
    }

    public void isAllowPython(ActionEvent event) {
        boolean selectedPython = allowPython.isSelected();
        if(selectedPython){
            serverGUI.langlist.add("py");
            System.out.println("Python Selected");
        }
        else{
            serverGUI.langlist.remove("py");
            System.out.println("Python removed");
        }
    }

    public void maxSizeTriggered(ActionEvent event) {
        String size = maxSize.getText();
        int maxSize = Integer.valueOf(size) * 1024;
        serverGUI.SetMaxFileSize(maxSize);
        System.out.println("maxsize is "+ maxSize);
    }

    public void maxNumTriggered(ActionEvent event) {
        String num = maxNum.getText();
        int maxNumber = Integer.valueOf(num);
        serverGUI.SetMaxFileNum(maxNumber);
        System.out.println("maxnum is "+maxNumber);

    }

    public void isGo(ActionEvent event) {
        go.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                serverGUI.ShowNextPage();
            }
        });
    }
}
