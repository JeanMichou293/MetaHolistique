import java.util.ArrayList;
import java.util.HashMap;

public class Solution
{
	private Project project; // For sync
	private int cost;
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();

	public Solution(Project project)
	{
		this.project = project;
		this.saveFromProject();
	}

	public boolean betterThan(Solution solution)
	{
		return this.cost < solution.cost;
	}

	// Save operations, machines and cost
	public void saveFromProject()
	{
		this.jobs = new ArrayList<Job>();
		this.machines = new ArrayList<Machine>();

		// Duplicate jobs
		for (Job job : this.project.getJobs()) {
			Job job2 = new Job(job.getId());
			HashMap<Operation, Interval> opInTime =
				new HashMap<Operation, Interval>(job.getOperationsInTime());
			job2.setOperationsInTime(opInTime);
			this.jobs.add(job2);
		}

		// Duplicate machines
		for (Machine machine : this.project.getMachines()) {
			Machine machine2 = new Machine(machine.getId());
			HashMap<Operation, Interval> opInTime =
				new HashMap<Operation, Interval>(machine.getOperations());
			machine2.setOperations(opInTime);
			this.machines.add(machine2);
		}

		// Save cost
		this.cost = this.project.getDuration();
	}

	public String toString()
	{
		return "cost=" + Integer.toString(this.cost);
	}
}