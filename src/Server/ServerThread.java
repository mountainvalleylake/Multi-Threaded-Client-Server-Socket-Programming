package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by graphics on 3/12/2017.
 */
public class ServerThread implements Runnable,Serializable{
    ServerSocketHandler serverSocketHandler;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ObjectInputStream oin = null;
    private ObjectOutputStream oout = null;
    private BufferedReader br;
    private InputStreamReader isr;
    private PrintWriter pw;
    private Thread thread;
    private String rootPath = null;
    int serial_no;
    int max_size = 0;
    int max_num = 0;
    int fileCount = 0;
    int fileSize = 0;
    ArrayList<String> langlist;
    ArrayList<String> nameList = new ArrayList<>();
    public ServerThread(Socket client, int serial, ServerSocketHandler serverSocketHandler) {
        System.out.println("Socket is connected");
        socket = client;
        serial_no = serial;
        this.serverSocketHandler = serverSocketHandler;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oin = new ObjectInputStream(in);
            oout = new ObjectOutputStream(out);
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            pw = new PrintWriter(out);
            this.thread = new Thread(this);
            System.out.println("Going to Thread");
            thread.start();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ServerThread(int port, int serial_no,String s){
        try {
            socket = new Socket(s,port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);//1 The 1st Pass
            pw = new PrintWriter(out);
            this.thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SetRootPath(String rootPath){
        this.rootPath = rootPath;
        System.out.println(rootPath);
    }

    @Override
    public void run() {
        System.out.println("Inside Run");
        try {
            //get StudentID
            //System.out.println("Reading something");
            boolean succ = false;
            String studentID = br.readLine();//1 Student ID
            Integer stID = Integer.valueOf(studentID);
            boolean value = CheckIfValidID(stID);
            pw.write(String.valueOf(value));// 1(a)
            pw.println();
            pw.flush();
            if(value){
                //System.out.println("passed it" + studentID);
                //get client's IP Address.
                String ipClient = socket.getRemoteSocketAddress().toString();
                System.out.println(ipClient);
                boolean val = serverSocketHandler.StudentLists(stID,ipClient,this);
                //System.out.println(val);
                if(val == true){ //2 The Second Pass
                    pw.write("true");//exists
                    pw.println();
                    pw.flush();
                    //no need for creating directory
                }
                else{ //2 The Second Pass
                    pw.write("false");//newly entered
                    pw.println();
                    pw.flush();
                    //create directory
                }
                max_num = serverSocketHandler.GetMaxNum();
                pw.write (String.valueOf(max_num));//3 The Third Pass
                pw.println();
                pw.flush();
                max_size = serverSocketHandler.GetMaxSize();
                pw.write(String.valueOf(max_size));//4 The Fourth Pass
                pw.println();
                pw.flush();
                langlist = serverSocketHandler.GetLanguageArray();
                oout.writeObject(langlist);//5th pass
                //System.out.println("Yeppi");
                while (br.readLine().equals("File")) {
                   //6th pass
                    //System.out.println("yoyo xoxo");
                    pw.write("Getting It dude, chill");//6.1
                    pw.println();
                    pw.flush();
                    succ = GetFileUploaded();
                    System.out.println(succ);

                }
                System.out.println("returned");
                if (br.readLine().equals("Directory")) {
                    succ = GetDirectoryUploaded();
                    System.out.println(succ);
                }

            }


            } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    public boolean CheckIfValidID(int st_id){
        boolean val = false;
        if((st_id >= 1305001 & st_id <= 1305120)||(st_id == 805119)||(st_id == 905100)){
            val = true;
        }
        return val;
    }
    public boolean IsUploadPermitted(int fileCount,int fileSize){
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
        for(String a:langlist){
            if(a.equals(extension)){
                val = true;
                System.out.println("true, it exists");
                break;
            }
        }
        return val;
    }
    public String ReviewedName(String name,String extension){
        String str = "";
        int counter = 0;
        if(nameList.isEmpty()){
            str = name + "." + extension;
            return str;
        }
        for(String rts: nameList){
            if(rts.contains(name)){
                counter++;
            }
        }
        if(counter==0){
            str = name + "." + extension;
            return str;
        }
        str = name + "(" + counter + ")." + extension;
        System.out.println(str);
        return str;
    }
    public boolean GetDirectoryUploaded(){
        boolean size_verify = false;
        boolean ext_verify = false;
        try {
            String str_count = br.readLine();//6a how many files
            int count = Integer.valueOf(str_count);
            System.out.println(count);
            for(int j = 0; j < count; j++){
                System.out.println(j + "th time");
                String aff = br.readLine();
                if(aff.equals("Yes")){//6b yes it is a file
                    String str_size = br.readLine();//7th pass the size
                    System.out.println(str_size);
                    int size_of_file = Integer.parseInt(str_size);
                    System.out.println(size_of_file);
                    String extension = br.readLine();// 8th pass the extension
                    System.out.println(extension);
                    //if(extension belongs to the array) then perform the tasks below
                    String filenamecurr = br.readLine();// 9th pass the file name
                    System.out.println(filenamecurr + rootPath);
                    int pos = filenamecurr.lastIndexOf(".");
                    if (pos > 0) {
                        filenamecurr = filenamecurr.substring(0, pos);
                    }
                    System.out.println(filenamecurr);
                    String nameUlt = ReviewedName(filenamecurr,extension);
                    System.out.println("The ultimate name "+ nameUlt);
                    String filePathUlt = rootPath + "\\" + nameUlt;
                    File file = new File(filePathUlt);
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                    byte[] arr = new byte[size_of_file];
                    int size = 512;
                    int chunks = size_of_file/512;
                    int last_chunk = size_of_file - (chunks*512);
                    for(int i = 0; i < chunks ; i++){
                        //System.out.println("Meaw Meaw Catwoman");
                        dis.read(arr,0,size);
                        dos.write(arr,0,size);
                        dos.write(1);
                    }
                    dis.read(arr,0,last_chunk);
                    dos.write(arr,0,last_chunk);
                    dos.flush();
                    System.out.println("Written Done");
                    fileCount++;
                    fileSize += size_of_file;
                    nameList.add(nameUlt);
                    size_verify = IsUploadPermitted(fileCount,fileSize);
                    ext_verify = IsExtensionPermitted(extension);
                }
                else if(aff.equals("Nothing")){
                    size_verify = false;
                    ext_verify = false;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size_verify && ext_verify;
    }
    public boolean GetFileUploaded(){
        boolean size_verify = false;
        boolean ext_verify = false;
        try {
            String str_size = br.readLine();//7th pass the size
            System.out.println(str_size);
            int size_of_file = Integer.parseInt(str_size);
            System.out.println(size_of_file);
            String extension = br.readLine();// 8th pass the extension
            System.out.println(extension);
            //if(extension belongs to the array) then perform the tasks below
            String filenamecurr = br.readLine();// 9th pass the file name
            int pos = filenamecurr.lastIndexOf(".");
            if (pos > 0) {
                filenamecurr = filenamecurr.substring(0, pos);
            }
            System.out.println(filenamecurr);
            String nameUlt = ReviewedName(filenamecurr,extension);
            System.out.println("The ultimate name "+ nameUlt);
            System.out.println(filenamecurr + rootPath);
            String filePathUlt = rootPath + "\\" + nameUlt;
            File file = new File(filePathUlt);
            DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            byte[] arr = new byte[size_of_file];
            int size = 512;
            int chunks = size_of_file/512;
            int last_chunk = size_of_file - (chunks*512);
            for(int i = 0; i < chunks ; i++){
                //System.out.println("Meaw Meaw Catwoman");
                dis.read(arr,0,size);
                dos.write(arr,0,size);
                System.out.println("sent a chunk");
                dos.write(1);//Acknowledgement
            }
            dis.read(arr,0,last_chunk);
            dos.write(arr,0,last_chunk);
            dos.flush();
            System.out.println("Written Done");
            fileCount++;
            fileSize += size_of_file;
            nameList.add(nameUlt);
            size_verify = IsUploadPermitted(fileCount,fileSize);
            ext_verify = IsExtensionPermitted(extension);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size_verify && ext_verify;
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
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

