package lesmaitresdutemps;

import java.util.HashMap;
import java.util.Map.Entry;

public class Solution
{
	private int cost;
	private HashMap<Job, HashMap<Operation, Interval>> jobOperations;
	private HashMap<Machine, HashMap<Operation, Interval>> machineOperations;

	public Solution(Project project)
	{
		this.saveFromProject(project);
	}

	// Save operations, machines and cost
	public void saveFromProject(Project project)
	{
		this.jobOperations = new HashMap<Job, HashMap<Operation, Interval>>();
		this.machineOperations =
			new HashMap<Machine, HashMap<Operation, Interval>>();

		// Save jobs
		for (Job job : project.getJobs()) {
			HashMap<Operation, Interval> jobTimeline =
				new HashMap<Operation, Interval>(job.getOperationsInTime());
			// Copy interval (shifting can affect it)
			for (Entry<Operation, Interval> entry : jobTimeline.entrySet()) {
				Interval interval = entry.getValue();
				jobTimeline.put(entry.getKey(), interval.copy());
			}
			this.jobOperations.put(job, jobTimeline);
		}

		// Save machines
		for (Machine machine : project.getMachines()) {
			HashMap<Operation, Interval> machineTimeline =
				new HashMap<Operation, Interval>(machine.getOperations());
			// Copy interval (shifting can affect it)
			for (Entry<Operation, Interval> entry : machineTimeline
				.entrySet()) {
				Interval interval = entry.getValue();
				machineTimeline.put(entry.getKey(), interval.copy());
			}
			this.machineOperations.put(machine, machineTimeline);
		}

		// Save cost
		this.cost = project.getDuration();
	}

	public int getCost()
	{
		return this.cost;
	}

	public HashMap<Job, HashMap<Operation, Interval>> getOpByJob()
	{
		return this.jobOperations;
	}

	public HashMap<Machine, HashMap<Operation, Interval>> getOpByMachine()
	{
		return this.machineOperations;
	}

	// TODO: not required anymore
	public int getRealCost()
	{
		int maxDuration = 0;
		@SuppressWarnings("unused")
		Job longestJob = null;
		for (Entry<Job, HashMap<Operation, Interval>> entry : this.jobOperations
			.entrySet()) {
			Job job = entry.getKey();
			Operation lastOp =
				job.getOperations().get(job.getOperations().size() - 1);
			Interval interval = entry.getValue().get(lastOp);
			if (interval.end() > maxDuration) {
				maxDuration = interval.end();
				longestJob = job;
			}
		}
		return cost;
	}

	public int getRealCostJob(Job job)
	{
		Operation lastOp =
			job.getOperations().get(job.getOperations().size() - 1);
		return this.jobOperations.get(job).get(lastOp).end();
	}

	public String toString()
	{
		// XXX: debugging
		/*
		 * String str = "SOLUTION:\ncost=" + this.cost + " real_cost=" + this.getRealCost(); for (Entry<Job, HashMap<Operation, Interval>> entry : this.jobOperations .entrySet()) { Job job = entry.getKey(); str += "\n" + job + "  (real_cost=" + this.getRealCostJob(job) + ")"; } return str;
		 */

		return "cost=" + this.cost;
	}
}