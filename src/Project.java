import java.util.ArrayList;

public class Project
{
	private ArrayList<Job> jobs = new ArrayList<Job>();

	public void addJob(Job job)
	{
		this.jobs.add(job);
	}
	
	public void print()
	{
		for (Job job : jobs)
			job.print();
	}
}
