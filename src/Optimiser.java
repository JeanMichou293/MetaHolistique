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
		int counter = 1;
		while (this.badSolutionsCount < this.maxBadSolutions) {
			project.iterateOptimisation();
			System.out.println(counter + ": cost=" + project.getDuration());

			if (project.getDuration() < bestSolution.getCost()) {
				Solution solution = project.exportSolution();
				this.bestSolution = solution;
				this.badSolutionsCount = 0;
			} else {
				this.badSolutionsCount++;
			}
			counter++;
		}
	}

	public Solution getBestSolution()
	{
		return this.bestSolution;
	}
}
