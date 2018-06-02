import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		testDirectory("./textJobData/Barnes");
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
		int result = Solver.solveByHeuristic(project);
		System.out.println(result);// + "\t" + Verifier.verify(project));
	}
}
