public class Optimiser
{
	private Project project;
	private Solution bestSolution;
	private int badSolutionsCount;
	private int maxBadSolutions = 5000;

	public Optimiser(Project project, Solution solution)
	{
		this.project = project;
		this.bestSolution = solution;
		this.badSolutionsCount = 0;
	}

	public void start()
	{
		this.optimise();
	}

	private void optimise()
	{
		while (this.badSolutionsCount < this.maxBadSolutions) {
			project.iterateOptimisation();

			// Separate solution from project
			// XXX: solution should be saved only when found to be the best
			Solution solution = project.exportSolution();
			System.out.println("Iteration over: " + solution);

			if (solution.betterThan(bestSolution)) {
				this.bestSolution = solution;
				this.badSolutionsCount = 0;
			} else {
				this.badSolutionsCount++;
			}
		}
	}

	public Solution getBestSolution()
	{
		return this.bestSolution;
	}
}
