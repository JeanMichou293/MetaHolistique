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
		for (Job job : this.jobs) {
			// Get operation at this time
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue();
			
			// Operations remain unprocessed
			if (operation != null && operation.canHaveIdleMachine(time)) {
				// No operations have been processed yet
				if (interval == null || interval.end <= time) {
					// Process operation
					operation.process(time);
				}
			}
		}
	}

	public String toString()
	{
		String str = "";
		for (Job job : jobs)
			str += job + "\n";
		return str.substring(0, str.length() - 1);
	}
	
	public boolean isQueueEmpty()
	{
		for (Job job : jobs) {
			if (job.getFromQueue() != null)
				return false;
		}
		return true;
	}
}
