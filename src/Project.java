import java.util.ArrayList;

public class Project
{
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();

	public void process(ArrayList<Job> excludedJobs, int time)
	{
		ArrayList<Operation> opPool = new ArrayList<Operation>();

		// Exclude jobs
		ArrayList<Job> jobPool = new ArrayList<Job>();
		for (Job job : excludedJobs) {
			jobPool.remove(job);
		}

		for (Job job : jobPool) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end <= time) {
					// Add operation to pool
					opPool.add(operation);
				}
			}
		}

		// Select shortest operation (SJF)
		int minDuration = Integer.MAX_VALUE;
		Operation chosenOperation = null;
		Machine chosenMachine = null;
		for (Operation operation : opPool) {
			// Get machine with the best affinity
			Machine machine = operation.getMachineByAffinity(time);
			int duration = operation.getMachineAffinity(machine);
			if (duration < minDuration) {
				minDuration = duration;
				chosenOperation = operation;
				chosenMachine = machine;
			}
		}

		// Process operation
		if (chosenOperation != null)
			chosenOperation.process(time, chosenMachine);
	}

	public int solve()
	{
		return this.solve(new ArrayList<Job>());
	}

	private int solve(ArrayList<Job> excludedJobs)
	{
		int time = 0;
		while (!this.isQueueEmpty()) {
			// System.out.println("time=" + time);
			// Process every operation at the specified time
			this.process(excludedJobs, time);

			// TODO: optimisation: instead of incrementing, jump to next "interesting
			// moment"???
			time++;
		}
		return this.getDuration();
	}

	public void iterateOptimisation()
	{
		// Make relevant shift
		Job longestJob = this.getLongestJob();
		longestJob.shift();

		// Update other jobs
		ArrayList<Job> excludedJobs = new ArrayList<Job>();
		excludedJobs.add(longestJob);
		this.solve(excludedJobs);
	}

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
		return this.jobs.get(index);
	}

	public ArrayList<Job> getJobs()
	{
		return this.jobs;
	}

	public Machine getMachine(int index)
	{
		return this.machines.get(index);
	}

	public ArrayList<Machine> getMachines()
	{
		return this.machines;
	}

	public int getDuration()
	{
		return this.getLongestJob().getDuration();
	}

	public Job getLongestJob()
	{
		int maxDuration = 0;
		Job longerJob = null;
		for (Job job : this.jobs) {
			int duration = job.getDuration();
			if (duration > maxDuration) {
				maxDuration = duration;
				longerJob = job;
			}
		}
		return longerJob;
	}

	public boolean isQueueEmpty()
	{
		for (Job job : jobs) {
			if (job.getFromQueue() != null)
				return false;
		}
		return true;
	}

	public Solution exportSolution()
	{
		return new Solution(this);
	}

	public String toString()
	{
		String str = "";
		for (Job job : jobs)
			str += job + "\n";
		return str.substring(0, str.length() - 1);
	}
}
