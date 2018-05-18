import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser
{

    public static String readFile(String FILENAME) {

        String res = "";

        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                res+=sCurrentLine;
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
        
        return res;

    }

    public static void main(String[] args){
    	
    	System.out.println(readFile(null));


    }

}
