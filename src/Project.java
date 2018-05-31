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

	public Operation getNextOperation()
	{
		Operation nextOperation = null;
		int soonerEnd = Integer.MAX_VALUE;
		for (Job job : this.jobs) {
			Interval interval = job.getLastProcessedInterval();
			Operation operation = job.getFromQueue(); // TODO: check not null
			if (interval == null) {
				if (operation.canHaveIdleMachine(0)) {
					return operation;
				}
			}
			else {
				// TODOOOOO
			}
				
			
			if (!operation.canHaveIdleMachine(0))
			
			// No operation has been processed within this job
			if (interval == null)
				return job.getFromQueue();
			
			if (interval.end < soonerEnd) {
				nextOperation = job.getFromQueue();
				soonerEnd = interval.end;
			}
		}
		return nextOperation;
	}

	public String toString()
	{
		String str = "";
		for (Job job : jobs)
			str += job + "\n";
		return str.substring(0, str.length() - 1);
	}
}
