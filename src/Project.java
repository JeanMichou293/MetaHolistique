import java.util.ArrayList;

public class Project
{
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<Machine> machines = new ArrayList<Machine>(); // TODO: Why?

	public void addJob(Job job)
	{
		this.jobs.add(job);
	}

	public void addMachine(Machine machine)
	{
		this.machines.add(machine);
	}

	public Job getJob(int index)
	{
		return jobs.get(index);
	}

	public Machine getMachine(int index)
	{
		return machines.get(index);
	}

	public void process(int time)
	{
		ArrayList<Operation> potentialPool = new ArrayList<Operation>();

		for (Job job : this.jobs) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end <= time) {
					// Basic heuristic
					// Process operation
					// operation.process(time);

					// Add operation to list
					potentialPool.add(operation);
				}
			}
		}

		// Select shortest operation (SJF)
		int minDuration = Integer.MAX_VALUE;
		Operation chosenOperation = null;
		for (Operation operation : potentialPool) {
			// Get machine with best affinity
			Machine machine = operation.getMachineByAffinity(time);
			int duration = operation.getMachineAffinity(machine);
			if (duration < minDuration) {
				minDuration = duration;
				chosenOperation = operation;
			}
		}

		// Process operation
		if (chosenOperation != null)
			chosenOperation.process(time);
	}

	public int getDuration()
	{
		int maxDuration = 0;
		for (Job job : jobs) {
			int duration = job.getDuration();
			if (duration > maxDuration)
				maxDuration = duration;
		}

		return maxDuration;
	}

	public boolean isQueueEmpty()
	{
		for (Job job : jobs) {
			if (job.getFromQueue() != null)
				return false;
		}
		return true;
	}

	public String toString()
	{
		String str = "";
		for (Job job : jobs)
			str += job + "\n";
		return str.substring(0, str.length() - 1);
	}
}
