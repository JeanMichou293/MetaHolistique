import java.util.ArrayList;
import java.util.HashMap;

public class Operation
{
	private int id;
	// List of affinity for each machine
	private HashMap<Machine, Integer> affinities = new HashMap<Machine, Integer>();
	
	public enum State {PENDING, RUNNING, TERMINATED}; 
	private State state = State.PENDING;

	public Operation(int id)
	{
		this.id = id;
	}

	public void addMachine(Machine machine, int time)
	{
		this.affinities.put(machine, time);
	}

	public int getId()
	{
		return this.id;
	}

	public HashMap<Machine, Integer> getAffinities()
	{
		return this.affinities;
	}
	
	public static ArrayList<Operation> createOperations(int count)
	{
		ArrayList<Operation> operations= new ArrayList<Operation>();
		for (int i = 0; i < count; i++) {
			operations.add(new Operation(i));
		}
		
		return operations;
	}
}
