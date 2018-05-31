public abstract class Solver
{
	// For now this is a crappy heuristic
	public static void solve(Project project)
	{
		Operation nextOperation;

		// Get the operation starting the soonest among every job (if equality, pick the
		// first)
		while ((nextOperation = project.getNextOperation()) != null) {
			System.out.print("Processing operation " + nextOperation + " ... ");
			Job job = nextOperation.getJob();

			// Assign operation to the idle machine with the highest affinity (shortest duration)
			int beginTime = 0;
			if (job.getLastProcessedInterval() != null)
				beginTime = job.getLastProcessedInterval().end;
			Machine machine = nextOperation.getMachineByAffinity(beginTime);
			int duration = nextOperation.getMachineAffinity(machine);
			Interval newInterval = new Interval(beginTime, beginTime + duration);
			machine.assignOperation(nextOperation, newInterval);
			System.out.println("Assigned " + machine + " (duration=" + duration + ")");

			// Remove operation from queue
			nextOperation.getJob().removeFromQueue();
			
			// Add operation to processed
			job.process(nextOperation, newInterval);
		}
	}
}
