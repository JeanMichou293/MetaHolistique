import java.util.HashMap;
import java.util.Map.Entry;

public class Operation
{
	private int id;
	private Job job;

	// List of affinity for each machine
	private HashMap<Machine, Integer> affinities = new HashMap<Machine, Integer>();

	/*
	 * public enum State {PENDING, RUNNING, TERMINATED}; private State state =
	 * State.PENDING;
	 */

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

	// Return idle machine with the highest affinity
	public Machine getMachineByAffinity(int time)
	{
		Machine bestMachine = null;
		int minDuration = Integer.MAX_VALUE;
		for (Machine machine : this.affinities.keySet()) {
			if (!machine.isBusy(time)) {
				int duration = this.affinities.get(machine);
				if (duration < minDuration) {
					minDuration = duration;
					bestMachine = machine;
				}
			}
		}
		return bestMachine;
	}
	
	public boolean canHaveIdleMachine(int time)
	{
		for (Entry<Machine, Integer> entry : this.affinities.entrySet()) {
			if (!entry.getKey().isBusy(time))
				return true;
		}
		return false;
	}

	public String toString()
	{
		String str = "";
		boolean first = true;
		for (Entry<Machine, Integer> entry : affinities.entrySet()) {
			if (first) {
				str += entry.getKey().getID() + "(" + entry.getValue() + ")";
				first = false;
			} else
				str += "|" + entry.getKey().getID() + "(" + entry.getValue() + ")";
		}
		return str;
	}
}
