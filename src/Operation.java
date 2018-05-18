import java.util.HashMap;

public class Operation
{
	private int id;
	private HashMap<Integer, Integer> machines = new HashMap<Integer, Integer>();
	
	public enum State {PENDING, RUNNING, TERMINATED}; 
	private State state = State.PENDING;

	public Operation(int id)
	{
		this.id = id;
	}

	public void addMachine(int machine, int time)
	{
		this.machines.put(machine, time);
	}

	public int getId()
	{
		return this.id;
	}

	public HashMap<Integer, Integer> getMachines()
	{
		return this.machines;
	}
}
