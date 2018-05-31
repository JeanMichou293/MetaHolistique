import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		File f = new File(".");
		File[] paths = f.listFiles();
		for (File path : paths) {
			System.out.println(path);
		}

		Project project = Parser.parse(Parser.readFile("textJobData/test.txt"));
		System.out.println(project);
		
		System.out.println("Solving project...");
		Solver.solveByHeuristic(project);
	}
}
