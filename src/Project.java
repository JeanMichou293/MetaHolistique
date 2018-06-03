import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Project
{
	private static final int UNDEFINED = -1;
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	private int duration = UNDEFINED;

	
	
	// Scheduler: base
	public void process_base(ArrayList<Job> excludedJobs, int time)
	{
		for (Job job : this.jobs) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end() <= time) {
					Machine machine = operation.getMachineByAffinity(time);
					operation.process(time, machine);
				}
			}
		}
	}
	
	
	
	// Scheduler: rotate
	public void process_rotate(ArrayList<Job> excludedJobs, int time)
	{
		Collections.rotate(this.jobs, 1);
		
		for (Job job : this.jobs) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end() <= time) {
					Machine machine = operation.getMachineByAffinity(time);
					operation.process(time, machine);
				}
			}
		}
	}
	
	
	
	// Scheduler: shuffle
	public void process_shuffle(ArrayList<Job> excludedJobs, int time)
	{
		ArrayList<Job> jobs2 = new ArrayList<Job>(this.jobs);
		Collections.shuffle(jobs2);
		
		for (Job job : jobs2) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end() <= time) {
					Machine machine = operation.getMachineByAffinity(time);
					operation.process(time, machine);
				}
			}
		}
	}
	
	
	
	// Scheduler: limited SJF
	public void process_limited_sjf(ArrayList<Job> excludedJobs, int time)
	{
		ArrayList<Operation> potentialPool = new ArrayList<Operation>();
		
		for (Job job : this.jobs) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();

			// Operation can be assigned
			if (operation != null && operation.isMachineAvailable(time)) {
				// No operation is being executed at the moment
				if (interval == null || interval.end() <= time) {
					// Add operation to pool
					potentialPool.add(operation);
					/*Machine machine = operation.getMachineByAffinity(time);
					operation.process(time, machine);*/
				}
			}
		}

		// Select shortest operation (SJF)
		int minDuration = Integer.MAX_VALUE;
		Operation chosenOperation = null;
		Machine chosenMachine = null;
		for (Operation operation : potentialPool) {
			// Get machine with best affinity
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
	

	
	public void process_sjf(ArrayList<Job> excludedJobs, int time)
	{
		// Exclude jobs
		ArrayList<Job> jobPool = new ArrayList<Job>(this.jobs);
		for (Job job : excludedJobs)
			jobPool.remove(job);

		// Get next operations and add them to pool
		ArrayList<Operation> opPool = new ArrayList<Operation>();
		for (Job job : jobPool) {
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();
			if (operation != null
				&& (interval == null || interval.end() <= time))
				opPool.add(operation);
		}

		// Process eligible operations
		while (!opPool.isEmpty()) {
			HashMap<Machine, ArrayList<Operation>> resolutionTable =
				new HashMap<Machine, ArrayList<Operation>>();

			// Prepare conflict resolution table
			// NB: Iterator prevents concurrent modifications on the list
			Iterator<Operation> i = opPool.iterator();
			while (i.hasNext()) {
				Operation operation = i.next();
				Machine machine = operation.getMachineByAffinity(time);
				if (machine == null) {
					i.remove();
				} else {
					ArrayList<Operation> opForMachine =
						resolutionTable.get(machine);
					if (opForMachine == null)
						opForMachine = new ArrayList<Operation>();
					opForMachine.add(operation);
					resolutionTable.put(machine, opForMachine);
				}
			}

			// Select the shortest operation (SJF) for each conflicting machine
			for (Entry<Machine, ArrayList<Operation>> entry : resolutionTable
				.entrySet()) {
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
					// System.out.println(chosenOperation);
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
		this.duration = UNDEFINED;
		while (!this.isQueueEmpty()) {
			// System.out.println("time=" + time);
			// Process every operation at the specified time
			this.process_sjf(excludedJobs, time);

			time++;
		}

		this.updateDuration();
		return this.getDuration();
	}

	public void iterateOptimisation()
	{
		this.resetQueue();

		// Make relevant shift
		Job longestJob = this.getLongestJob();
		longestJob.shift();
		longestJob.setProcessed();

		// Reset operations (except for the longest job)
		for (Job job : this.jobs) {
			if (job != longestJob) {
				job.setOperationsInTime(new HashMap<Operation, Interval>());
				job.resetLastProcessedInterval();
			}
		}

		// Reset machines
		for (Machine machine : this.machines)
			machine.setOperations(new HashMap<Operation, Interval>());
		for (Operation operation : longestJob.getOperations()) {
			for (Entry<Machine, Integer> entry : operation.getAffinities()
				.entrySet()) {
				Machine machine = entry.getKey();
				machine.getOperations().put(operation,
					longestJob.getOperationsInTime().get(operation));
			}
		}

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

	private void updateDuration()
	{
		this.duration = this.getLongestJob().getDuration();
	}

	public int getDuration()
	{
		return this.duration;
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
