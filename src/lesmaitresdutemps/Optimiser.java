package lesmaitresdutemps;

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
				return jobs.get((new Random()).nextInt(jobs.size()));
			}

			public String getName()
			{
				return "random job";
			}
		};

		// Start with the "longest job" heuristic
		// iterate(longestJob, 100, 0);

		// Start iteration from the best solution
		// project.load(bestSolution);

		// Switch to another method to avoid revisiting the same solutions
		// over and over
		iterate(randomJob, 100, 100);
	}

	private static void iterate(JobSelector jobSelector, int maxBadSolutions,
		int maxBacktracks)
	{
		System.out.println("Iterating with the \"" + jobSelector.getName()
			+ "\" heuristic...");

		int backtracksCount = 0;
		while (backtracksCount <= maxBacktracks) {
			int badSolutionsCount = 0;
			while (badSolutionsCount < maxBadSolutions) {
				project.iterateOptimisation(jobSelector);

				// System.out.println((loopCount + 1) + ": cost="
				// + project.getDuration() + " " + Verifier.verify(project));

				if (project.getDuration() < bestSolution.getCost()) {
					Solution solution = project.export();
					bestSolution = solution;
					badSolutionsCount = 0;
				} else {
					badSolutionsCount++;
				}
				loopCount++;
			}
			// System.out
			// .println((backtracksCount + 1) + ": Trying to backtrack...");
			project.load(bestSolution);
			backtracksCount++;
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
