import java.util.ArrayList;

public class Project
{
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();

	public void addJob(Job job)
	{
		this.jobs.add(job);
	}

	public void addMachine(Machine machine)
	{
		this.machines.add(machine);
	}
	
	public Job getJob(int index){
		return jobs.get(index);
	}
	
	public Machine getMachine(int index){
		return machines.get(index);
	}
	
	public void print()
	{
		for (Job job : jobs)
			job.print();
	}
}
