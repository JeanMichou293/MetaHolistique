import java.util.ArrayList;
import java.util.HashMap;

public class Optimiser
{
	private Project project;
	private Solution bestSolution;
	private int iterationCount;
	private int maxIterationCount = 100;

	public Optimiser(Project project, Solution solution)
	{
		this.project = project;
		this.bestSolution = solution;
		this.iterationCount = 0;
	}

	public void start()
	{
		this.optimise(this.bestSolution);
	}

	private void optimise(Solution solution)
	{
		while (this.iterationCount < this.maxIterationCount) {
			// Make relevant shift
			solution.shift();
			
			// Update project solution
			this.project.updateFromSolution(solution);

			if (solution.betterThan(bestSolution))
				this.bestSolution = solution;
			this.iterationCount++;
		}
	}

	public Solution getBestSolution()
	{
		return this.bestSolution;
	}
}
