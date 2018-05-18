import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser
{

        public static void read(String FILENAME) {

            BufferedReader br = null;
            FileReader fr = null;

            try {

                fr = new FileReader(FILENAME);
                br = new BufferedReader(fr);

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

        }

        public static void main(String[] args){

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        }

}
