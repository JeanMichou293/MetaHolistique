import java.util.ArrayList;
import java.util.Random;

public abstract class Optimiser
{
	private static Project project;
	private static Solution bestSolution;
	private static int loopCount;

	public static void start(Project project, Solution solution)
	{
		Optimiser.project = project;
		bestSolution = solution;
		loopCount = 0;

		optimise();
	}

	private static void optimise()
	{
		// Anonymous classes to execute instructions from inside Project
		JobSelector longestJob = new JobSelector()
		{
			public Job selectReferenceJob(Project project)
			{
				return project.getLongestJob();
			}

			public String getName()
			{
				return "longest job";
			}
		};
		JobSelector randomJob = new JobSelector()
		{
			public Job selectReferenceJob(Project project)
			{
				ArrayList<Job> jobs = project.getJobs();
				int rand = (new Random()).nextInt(jobs.size());
				return jobs.get(rand);
			}

			public String getName()
			{
				return "random job";
			}
		};

		// Start with the "longest job" heuristic
		//iterate(longestJob, 50);

		// Switch to another method to avoid revisiting the same solutions
		// over and over
		// TODO: start iteration from the best solution (fetch it from memory
		// and overwrite Project with it)
		iterate(randomJob, 50000);
	}

	private static void iterate(JobSelector jobSelector, int maxBadSolutions)
	{
		int badSolutionsCount = 0;
		System.out.println("Iterating with the \"" + jobSelector.getName()
			+ "\" heuristic...");
		while (badSolutionsCount < maxBadSolutions) {
			project.iterateOptimisation(jobSelector);
			// System.out
			// .println((loopCount + 1) + ": cost=" + project.getDuration());

			if (project.getDuration() < bestSolution.getCost()) {
				Solution solution = project.exportSolution();
				bestSolution = solution;
				badSolutionsCount = 0;
			} else {
				badSolutionsCount++;
			}
			loopCount++;
		}
		System.out.println("Best solution found: " + bestSolution + "\n\n");
	}

	public static Solution getBestSolution()
	{
		return bestSolution;
	}

	public static int getLoopCount()
	{
		return loopCount;
	}
}
