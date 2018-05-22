import java.util.HashMap;
import java.util.Map.Entry;

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

	public State getState()
	{
		return this.state;
	}

	public HashMap<Machine, Integer> getAffinities()
	{
		return this.affinities;
	}
	
	public String toString(){
		String str = "";
		boolean first = true;
		for(Entry<Machine, Integer> entry : affinities.entrySet()) {
			if (first){
				str+=entry.getKey().getID()+"("+entry.getValue()+")";
				first = false;
			}
			else
				str+="|"+entry.getKey().getID()+"("+entry.getValue()+")";
		}
		return str;
	}
}
