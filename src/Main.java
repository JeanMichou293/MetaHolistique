import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		//testDirectory("./textJobData/Barnes");
		testFile(new File("./textJobData/test.txt"));
		//testFile(new File("./textJobData/Hurink_Data/sdata/la40.fjs"));
	}

	public static void testDirectory(String path)
	{
		File folder = new File(path);
		File[] files = folder.listFiles();
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
		//System.out.println(result);
/*
		System.out.println("Exporting basic solution...");
		Solution basicSolution = project.exportSolution();
		System.out.println("Basic solution: " + basicSolution);

		System.out.println("Optimising...");
		Optimiser optimiser = new Optimiser(project, basicSolution);
		optimiser.start();
		long endTime = System.currentTimeMillis();
		System.out.print("Best solution: " + optimiser.getBestSolution());
		System.out.println(" (time=" + (endTime - startTime) + "ms)\n\n");
*/	}
}
