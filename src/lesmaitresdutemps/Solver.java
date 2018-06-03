package lesmaitresdutemps;

public abstract class Solver
{
	// For now this is a crappy heuristic
	public static void solveByHeuristic(Project project)
	{
		project.solve();
	}
}
