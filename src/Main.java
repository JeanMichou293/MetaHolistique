import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		String path = null;
		
		//path = "./textJobData/Barnes";
		//path = "./textJobData/test.txt";
		path = "./textJobData/Hurink_Data/sdata/la40.fjs";
		
		try{
			path = args[0];
			System.out.println("Chemin de test passé en paramètre");
		}
		catch (Exception e){
			System.out.println("Chemin de test automatique (voir code du main)");
		}
		
		int res = test(path);
		
		if (res == 0)
			System.out.println("Test complété !");
		else
			System.out.println("Erreur : Veuillez rentrer un chemin de fichier / dossier existant");
			
	}
	
	public static int test(String path){
		
		File path_to_test = new File(path);
		
		if (path_to_test.exists()) {
		    if (path_to_test.isFile()) {
		    	testFile(path_to_test);
		    } else if (path_to_test.isDirectory()) {
		        testDirectory(path_to_test);
		    }
		    else
		    	return -1;   
		}
		else
			return -1;
		
		return 0;
		
	}

	public static void testDirectory(File directory)
	{
		File[] files = directory.listFiles();
		for (File file : files)
			testFile(file);
	}

	public static void testFile(File file)
	{
		Project project = Parser.parse(Parser.readFile(file));
		System.out.print(file.getName() + ": ");

		long startTime = System.currentTimeMillis();
		int result = Solver.solveByHeuristic(project);
		System.out.println(result + "\t" + Verifier.verify(project));
		// System.out.println(result);

		System.out.println("Exporting basic solution...");
		Solution basicSolution = project.exportSolution();
		System.out.println("Basic solution: " + basicSolution);

		System.out.println("Optimising...");
		Optimiser.start(project, basicSolution);
		long endTime = System.currentTimeMillis();
		System.out.print("Final best solution: " + Optimiser.getBestSolution());
		System.out.println(" (time=" + (endTime - startTime) + "ms  loops="
			+ Optimiser.getLoopCount() + ")\n\n");
	}
}
