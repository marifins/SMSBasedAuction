import java.io.*;

public class setting {

    /**
     * Creates a new instance of <code>setting</code>.
     */
    public setting() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	String str; String s = "";
        try{
        	BufferedReader in = new BufferedReader(new FileReader("setting.txt"));
        	while((str = in.readLine())!= null){
        		s += str + "\n";
        	}
        	System.out.println(s);
        	in.close();
        }catch(IOException ioe){}
		String[] hsArray = s.split("\n");
		int a = hsArray[0].lastIndexOf("=") + 2;
		int b = hsArray[1].lastIndexOf("=") + 2;
		System.out.println(hsArray[0].substring(a));
		System.out.println(hsArray[1].substring(b));
    }
}
