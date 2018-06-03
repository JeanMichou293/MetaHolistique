import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		// testDirectory("./textJobData/Barnes");
		// testFile(new File("./textJobData/test.txt"));
		testFile(new File("./textJobData/Hurink_Data/sdata/la40.fjs"));
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
		Solver.solveByHeuristic(project);
		int result = project.getDuration();
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
