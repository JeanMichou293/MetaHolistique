import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		//testDirectory("./textJobData/Barnes");
		//testDirectory("./textJobData/Brandimarte_Data"); // Crappy format!!!
		//testDirectory("./textJobData/Dauzere_Data");
		//testDirectory("./textJobData/Hurink_Data/sdata");
		//testFile(new File("./textJobData/test.txt"));
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
		
		int result = Solver.solveByHeuristic(project);
		System.out.println(result + "\t" + Verifier.verify(project));
	}
}
