import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
                res+=sCurrentLine+'\n';
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

    public static Project parse(String content){
    	
    	try{
    		// RECUPERATION DES LIGNES
	    	String[] lines = content.split("\n");
	    	
	    	// LIGNE D'INTRODUCTION AVEC RECUPERATION DES PARAMETRES
	    	String[] params = lines[0].split("   ");
	    	int nb_jobs = Integer.parseInt(params[0]);
	    	int nb_mach = Integer.parseInt(params[1]);
	    	
	    	
	    	
    	
    	}
    	catch(Exception e){
    		System.out.println("Fichier illisible!!!");
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    public static void main(String[] args){
    	
    	System.out.println(readFile("C:\\Users\\Adrian\\Documents\\GitHub\\MetaHolistique\\TextData\\test.txt"));

    }

}
