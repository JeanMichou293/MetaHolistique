import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Project
{
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();

	public void process(ArrayList<Job> excludedJobs, int time)
	{
		ArrayList<Operation> opPool = new ArrayList<Operation>();

		// Exclude jobs
		ArrayList<Job> jobPool = new ArrayList<Job>(this.jobs);
		for (Job job : excludedJobs) {
			jobPool.remove(job);
		}

		for (Job job : jobPool) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			// TODO: make it more efficient
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end <= time) {
					// Add operation to pool
					opPool.add(operation);
				}
			}
		}

		// FIXME: terminate SJF implementation
		while (!opPool.isEmpty()) {
			HashMap<Machine, ArrayList<Operation>> opPoolHash =
				new HashMap<Machine, ArrayList<Operation>>();

			// Prepare hashmap for conflict resolution
			Iterator<Operation> i = opPool.iterator();
			while (i.hasNext()) {
				Operation operation = i.next();
				Machine machine = operation.getMachineByAffinity(time);
				if (machine == null) {
					i.remove(); // Prevent concurrent modification
				} else {
					ArrayList<Operation> opForMachine = opPoolHash.get(machine);
					if (opForMachine == null)
						opForMachine = new ArrayList<Operation>();
					opForMachine.add(operation);
					opPoolHash.put(machine, opForMachine);
				}
			}

			for (Entry<Machine, ArrayList<Operation>> entry : opPoolHash
				.entrySet()) {
				// Select the shortest operation (SJF) for each machine
				int minDuration = Integer.MAX_VALUE;
				Operation chosenOperation = null;
				for (Operation operation : entry.getValue()) {
					// Get machine with the best affinity
					int duration = operation.getMachineAffinity(entry.getKey());
					if (duration < minDuration) {
						minDuration = duration;
						chosenOperation = operation;
					}
				}

				// Process operation
				if (chosenOperation != null) {
					opPool.remove(chosenOperation);
					chosenOperation.process(time, entry.getKey());
					// XXX: debug
					System.out.println(chosenOperation);
				}
			}
		}
	}

	public int solve()
	{
		return this.solve(new ArrayList<Job>());
	}

	private int solve(ArrayList<Job> excludedJobs)
	{
		int time = 0;
		this.resetQueue();
		while (!this.isQueueEmpty()) {
			System.out.println("time=" + time);
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

	private void resetQueue()
	{
		for (Job job : jobs)
			job.resetQueue();
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
