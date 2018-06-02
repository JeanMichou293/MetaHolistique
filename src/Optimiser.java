public class Optimiser
{
	private Project project;
	private Solution bestSolution;
	private int badSolutionsCount;
	private int maxBadSolutions = 100;

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
		// TODO: the one moment when solution should be saved is if it is better than best solution
		while (this.badSolutionsCount < this.maxBadSolutions) {
			project.iterateOptimisation();

			// Separate solution from project
			Solution solution = project.exportSolution();

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
