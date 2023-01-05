import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemoManagement {
    Scanner input = new Scanner(System.in);

    public int mNo;
    public static ArrayList<String> Memos=new ArrayList<String>();

    public static void initialize(){
        getAllMemo();
    }

    public static void add(String memo) throws IOException{
        Memos.add(memo);
        String data=memo+"\n";
        File file=new File("Memo.txt");
        FileWriter writer=new FileWriter(file,true);
        writer.write(data);
        writer.close();
    }

    private static void getAllMemo(){
        File file=new File("Memo.txt");
        try {
            Scanner reader = new Scanner(file);
            String line;
            while(reader.hasNext()){
                line=reader.nextLine();
                Memos.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Memo File Not Found !");
        }
    }

    public static void updateMemo() throws IOException{
        File file=new File("Memo.txt");
        try (FileWriter writer = new FileWriter(file)) {
            String data;
            for (String m : Memos){
                data=m+"\n";
                writer.write(data);
            }
        }
    }

    public static void showMemo(){
        int counter=0;

        if (Memos.isEmpty()){
            System.out.println("\t==> No Memos added yet <==");
        }
        for (String m :Memos){
            System.out.printf("%5d | %-10s \n",++counter,m);
        }
    }

}