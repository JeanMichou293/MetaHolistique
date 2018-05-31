public class Main
{
	public static void main(String[] args)
	{
		Project project = Parser.parse(Parser.readFile("textJobData/test.txt"));
		System.out.println(project);

		System.out.println("Solving project...");
		int result = Solver.solveByHeuristic(project);
		System.out.println("Result = " + result);
	}
}
