public abstract class Solver
{
	// For now this is a crappy heuristic
	public static void solveByHeuristic(Project project)
	{
		int time = 0;
		while (!project.isQueueEmpty()) {
			System.out.println("time=" + time);
			// Process every operation at the specified time
			project.process(time);
			
			// TODO: optimisation: instead of incrementing, jump to next "interesting moment"
			time++;
		}
	}
}
