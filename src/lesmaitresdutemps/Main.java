package lesmaitresdutemps;

import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		String path = "./textJobData/Hurink_Data/sdata/la40.fjs";
		// path = "./textJobData/Barnes";
		// path = "./textJobData/test.txt";

		try {
			path = args[0];
			System.out.println("Path passed by arguments");
		} catch (Exception e) {
			System.out.println("Path set to default (cf. code)");
		}

		if (test(path) == 0)
			System.out.println("Test done!");
		else
			System.out.println("Error: Please enter an existing path");

	}

	public static int test(String path)
	{
		File pathFile = new File(path);
		if (pathFile.exists()) {
			if (pathFile.isFile()) {
				testFile(pathFile);
			} else if (pathFile.isDirectory()) {
				testDirectory(pathFile);
			}
			return 0;
		}
		return -1;
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
		Solver.solveByHeuristic(project);
		int result = project.getDuration();
		System.out.println(result + "\t" + Verifier.verify(project));
		// System.out.println(result);

		System.out.println("Exporting basic solution...");
		Solution basicSolution = project.export();
		System.out.println("Basic solution: " + basicSolution);

		System.out.println("Optimising...");
		Optimiser.start(project, basicSolution);
		long endTime = System.currentTimeMillis();
		System.out.print("Final best solution: " + Optimiser.getBestSolution());
		System.out.println(" time=" + (endTime - startTime) + "ms loops="
			+ Optimiser.getLoopCount() + "\n\n");
	}
}
