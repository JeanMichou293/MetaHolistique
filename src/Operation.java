import java.util.HashMap;
import java.util.Map.Entry;

public class Operation
{
	private int id;
	private Job job;

	// List of affinities for each machine
	private HashMap<Machine, Integer> affinities =
		new HashMap<Machine, Integer>();

	public Operation(int id, Job job)
	{
		this.id = id;
		this.job = job;
	}

	public void addMachine(Machine machine, int time)
	{
		this.affinities.put(machine, time);
	}

	public int getId()
	{
		return this.id;
	}

	public Job getJob()
	{
		return this.job;
	}

	public HashMap<Machine, Integer> getAffinities()
	{
		return this.affinities;
	}

	public int getMachineAffinity(Machine machine)
	{
		return this.affinities.get(machine);
	}

	// Return idle machine with the highest affinity (shortest duration)
	public Machine getMachineByAffinity(int time)
	{
		Machine bestMachine = null;
		int minDuration = Integer.MAX_VALUE;
		for (Entry<Machine, Integer> entry : this.affinities.entrySet()) {
			Machine machine = entry.getKey();
			Interval interval = null;
			try {
				interval =
					new Interval(time, time + this.affinities.get(machine));
			} catch (IntervalException e) {
				e.printStackTrace();
			}
			if (!machine.isBusy(interval)) {
				int duration = this.affinities.get(machine);
				if (duration < minDuration) {
					minDuration = duration;
					bestMachine = machine;
				}
			}
		}
		return bestMachine;
	}

	public void process(int time, Machine machine)
	{
		// System.out.print("Processing operation " + this + " (job=" + this.getJob().getId() + ") ");

		// Assign operation
		int duration = this.getMachineAffinity(machine);
		Interval interval = null;
		try {
			interval = new Interval(time, time + duration);
		} catch (IntervalException e) {
			e.printStackTrace();
		}
		machine.assignOperation(this, interval);
		// System.out.println("-> " + machine + " (d=" + duration + ")");

		// Set operation as processed
		job.setProcessed(this, interval);
	}

	public String toString()
	{
		String str = "id=" + Integer.valueOf(this.id) + "  ";
		boolean first = true;
		for (Entry<Machine, Integer> entry : affinities.entrySet()) {
			if (first) {
				str += entry.getKey().getId() + "(" + entry.getValue() + ")";
				first = false;
			} else {
				str +=
					"|" + entry.getKey().getId() + "(" + entry.getValue() + ")";
			}
		}
		return str;
	}

	public boolean isMachineAvailable(int time)
	{
		for (Entry<Machine, Integer> entry : this.affinities.entrySet()) {
			Machine machine = entry.getKey();
			Interval interval = null;
			try {
				interval =
					new Interval(time, time + this.affinities.get(machine));
			} catch (IntervalException e) {
				e.printStackTrace();
			}
			if (!entry.getKey().isBusy(interval))
				return true;
		}
		return false;
	}
}
