package Client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by graphics on 3/12/2017.
 */
public class ClientSocketHandler implements Serializable{
    int fileCount = 0;
    int fileSize = 0;
    int max_num = 0;
    int max_size = 0;
    ClientGUI clientGUI;
    int port;
    int studentID;
    String iphost;
    String stdid;
    String filePath;
    String dirPath;
    boolean valid;
    ArrayList<String> langlst;
    private Socket s;
    private BufferedReader br;
    private InputStreamReader isr;
    private PrintWriter pw;
    private InputStream in;
    private OutputStream out;
    private ObjectOutputStream oout;
    private ObjectInputStream oin;
    private String fileName;

    //private
    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
    public ClientSocketHandler(int port, int studentID, String iphost,ClientGUI clientGUI){
        System.out.println("Yesssss");
        this.port = port;
        this.studentID = studentID;
        this.iphost = iphost;
        stdid = Integer.toString(studentID);
        this.clientGUI = clientGUI;
        try {
            s = new Socket(iphost,port);
            in = s.getInputStream();
            out = s.getOutputStream();
            //System.out.println("donny");
            oout = new ObjectOutputStream(out);
            oout.flush();
            //System.out.println("Tadaaaaa");
            oin = new ObjectInputStream(in);
            //System.out.println("duck");
            pw = new PrintWriter(out);
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            //System.out.println("writing in client side " + stdid);
            pw.write(stdid);//1 the 1st pass
            pw.println();
            pw.flush();
            String valid_id = br.readLine();
            boolean validity = Boolean.valueOf(valid_id);
            SetIDValid(validity);//set the validity of id
            if(validity){
                //Send if the ID is valid or not.
                //If valid we set a flag here and send it to the controller somehow
                String val = br.readLine();//2 the 2nd Pass
                String str_max_num = br.readLine();
                max_num = Integer.valueOf(str_max_num);// 3 the 3rd pass
                clientGUI.SetMaxNum(max_num);
                System.out.println(max_num);
                String str_max_val = br.readLine();
                max_size = Integer.valueOf(str_max_val); // 4 the 4th pass
                clientGUI.SetMaxSize(max_size);
                System.out.println(max_size);
                langlst = (ArrayList<String>) oin.readObject();//5 the list
                for(String a: langlst){
                    System.out.println(a);
                }
            }
            else{
                CleanUp();
                clientGUI.IDValidity();

            }

            //System.out.println("dodododod");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void CleanUp(){
        try {
            br.close();
            isr.close();
            pw.close();
            oin.close();
            oout.close();
            in.close();
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SetFilePath(String filePath,String fileName){
        this.filePath = filePath;
        this.fileName = fileName;
    }
    public void  SetDirPath(String dirPath){
        this.dirPath = dirPath;
    }
    public void SetIDValid(boolean valid){
        this.valid = valid;
    }
    public boolean GetIDValid(){
        return valid;
    }

    public boolean IsUploadPermitted(){
        boolean val;
        if((fileCount < max_num) && (fileSize <= max_size)){
            val = true;
        }
        else{
            val = false;
            System.out.println("Cant make it");
        }
        return val;
    }
    public boolean IsExtensionPermitted(String extension){
        boolean val = false;
        for(String a:langlst){
            if(a.equals(extension)){
                val = true;
                System.out.println("true, it exists");
                break;
            }
        }
        return val;
    }
    public boolean DirectoryUpload(){
        //for all files in directory
        boolean size_permission = false;
        boolean extension_permission = false;
        boolean perm = false;
        try {
            File folder = new File(dirPath);
            System.out.println(dirPath);
            File[] listOfFiles = folder.listFiles();
            pw.write("Directory");//send file msg 6th pass
            pw.println();
            pw.flush();
            //6a how many files
            System.out.println(listOfFiles.length);
            pw.write(String.valueOf(listOfFiles.length));
            pw.println();
            pw.flush();// sending how many files
            for(File file : listOfFiles)
//file sending stuff
            {
                if (file.isFile()) {
                    //File file = new File(listOfFiles[i]);
                    System.out.println("File " + file.getName());
                    System.out.println(file.length());
                    String size_str = Integer.toString((int)file.length());
                    System.out.println(size_str);
                    fileCount++;
                    fileSize += (int)file.length();
                    size_permission = IsUploadPermitted();
                    extension_permission = IsExtensionPermitted(getFileExtension(file));
                    perm = size_permission && extension_permission;
                    if(perm){
                        pw.write("Yes");//6b it is a file
                        pw.println();
                        pw.flush();
                        pw.write(size_str);//7th pass, the size
                        pw.println();
                        pw.flush();
                            pw.write(getFileExtension(file));//8th pass the file extension
                            pw.println();
                            pw.flush();
                            String filenamecurr = file.getName();
                            System.out.println(filenamecurr);
                            pw.write(filenamecurr);//9th pass file name
                            pw.println();
                            pw.flush();
                            //add clause for file size here. I get the message if this file is valid to send or not
                            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(out));
                            byte[] arr = new byte[Math.toIntExact(file.length())];
                            long fileLength = file.length();
                            long current = 0;
                            int size = 512;
                            int chunks = (int) (fileLength/512);
                            int last_chunk = (int) (fileLength - (chunks * 512));
                            int msg = 0;
                            for(int j = 0; j < chunks; j++){
                                //System.out.println("Pew Pew Batman");
                                dis.read(arr,0,size);
                                dos.write(arr,0,size);
                                while ((msg = dis.read()) == 0){//Acknowledgement
                                    dos.write(arr,0,size);
                                }
                            }
                            dis.read(arr,0,last_chunk);
                            dos.write(arr,0,last_chunk);
                            dos.flush();
                            System.out.println("Sent");
                    }
                    else {
                        pw.write("Nothing");//6b it is a file
                        pw.println();
                        pw.flush();
                        fileCount--;
                        fileSize -= file.length();
                        System.out.println("Khew");
                        break;
                    }

                }

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return perm;
    }
    public boolean FileUpLoader(){
        boolean size_permission = false;
        boolean extension_permission = false;
        try {
            File file = new File(filePath);
            System.out.println(file.length());
            String size_str = Integer.toString((int)file.length());
            System.out.println(size_str);
            fileCount++;
            fileSize += (int)file.length();
            size_permission = IsUploadPermitted();
            extension_permission = IsExtensionPermitted(getFileExtension(file));
            if(size_permission && extension_permission){
                pw.write("File");//send file msg 6th pass
                pw.println();
                pw.flush();
                //System.out.println("Yo");
                System.out.println(br.readLine());// 6.1
                pw.write(size_str);//7th pass, the size
                pw.println();
                pw.flush();
                System.out.println("YoYo");
                pw.write(getFileExtension(file));//8th pass the file extension
                pw.println();
                pw.flush();
                //System.out.println("YoYoYo");
                String filenamecurr = file.getName();
                System.out.println(filenamecurr);
                pw.write(filenamecurr);//9th pass file name
                pw.println();
                pw.flush();
                //add clause for file size here. I get the message if this file is valid to send or not
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(out));
                byte[] arr = new byte[Math.toIntExact(file.length())];
                long fileLength = file.length();
                long current = 0;
                int size = 512;
                int chunks = (int) (fileLength/512);
                int last_chunk = (int) (fileLength - (chunks * 512));
                int msg = 0;
                for(int i = 0; i < chunks; i++){
                    //System.out.println("Pew Pew Batman");
                    dis.read(arr,0,size);
                    dos.write(arr,0,size);

                    if((msg = dis.read()) == 0){//Acknowledgement
                        int k = 0;//delay
                        for(int m = 0; m <= 7999; m++){
                            m++;
                        }
                        dos.write(arr,0,size);
                    }
                    System.out.println(msg);
                }
                dis.read(arr,0,last_chunk);
                dos.write(arr,0,last_chunk);
                dos.flush();
                System.out.println("Sent");

            }
            else {
                fileCount--;
                fileSize -= file.length();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extension_permission && size_permission;
    }
}
/**
 * Write the Student ID and other information in the socket.
 *
 */
