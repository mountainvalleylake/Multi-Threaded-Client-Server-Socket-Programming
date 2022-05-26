package Server;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by graphics on 3/12/2017.
 */
public class ServerSocketHandler implements Runnable {
    public int size, number;
    public Hashtable<Integer, String> StudentList = new Hashtable<>();
    public ArrayList<String> allowedLanguage = new ArrayList<>();
    private String rootPath;
    private java.net.ServerSocket serverSocket;
    public static int clientcount = 0;
    private Thread t;
    public ServerGUI serverGUI;
    public ServerThread sthread;
    public ServerSocketHandler(ServerGUI serverGUI){
        this.serverGUI = serverGUI;
        this.t = new Thread(this);
        t.start();
    }
    public boolean StudentLists(Integer id, String ip,ServerThread serverThread){
        boolean val;
        val = StudentList.containsKey(id);
        if(!val){
            StudentList.put(id, ip);
            MakeDirectory(id,serverThread);
        }
        System.out.println(StudentList);
        return val;
    }
    public void SetLanguageArray(){
        this.allowedLanguage = serverGUI.GetLanguageList();
    }
    public ArrayList<String> GetLanguageArray(){
        return allowedLanguage;
    }
    public void SetMaxSize(){
        this.size = serverGUI.GetMaxFileSize();
    }
    public int GetMaxSize(){
        return size;
    }
    public void SetMaxNum(){
        this.number = serverGUI.GetMaxFileNum();
    }
    public int GetMaxNum(){
        return number;
    }
    public void SetRootPathHandler(){
        this.rootPath = serverGUI.GetRootPath();
        System.out.println(rootPath);
    }
    public String GetRootPathHandler(){
        return rootPath;
    }
    public void MakeDirectory(Integer studentID,ServerThread serverThread){
        String id = String.valueOf(studentID);
        String dirPath = rootPath + "\\" + id;
        File dir = new File(dirPath);
        if(!dir.exists()){
            if(dir.mkdir()){
                System.out.println("Created Successfully");
                //System.out.println(dirPath);
            }
            else{
                System.out.println("Could not create directory");
            }
        }
        serverThread.SetRootPath(dirPath);
        //System.out.println(dirPath);
        SetLanguageArray();
        SetMaxNum();
        System.out.println(number);
        SetMaxSize();
        System.out.println(size);
    }

    @Override
    public void run() {
        try {
            SetRootPathHandler();
            serverSocket = new java.net.ServerSocket(5555);
            while (true){
                System.out.println("Here1");
                sthread = new ServerThread(serverSocket.accept(),clientcount++,this);
                serverSocket.getLocalPort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
