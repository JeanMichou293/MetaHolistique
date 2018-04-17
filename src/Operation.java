import java.util.ArrayList;
import java.util.HashMap;

public class Operation
{
	private String id;
	private ArrayList<Operation> edgesIn = new ArrayList<Operation>();
	private ArrayList<Operation> edgesOut = new ArrayList<Operation>();
	private HashMap<Integer, Integer> machines = new HashMap<Integer, Integer>();

	public Operation(String id)
	{
		this.id = id;
	}

	public void addMachine(int machine, int time)
	{
		this.machines.put(machine, time);
	}

	public void addEdgeIn(Operation node)
	{
		this.edgesIn.add(node);
	}

	public void addEdgeOut(Operation node)
	{
		this.edgesOut.add(node);
	}

	public String getID()
	{
		return this.id;
	}

	public ArrayList<Operation> getEdgeIn()
	{
		return this.edgesIn;
	}

	public ArrayList<Operation> getEdgeOut()
	{
		return this.edgesOut;
	}

	public HashMap<Integer, Integer> getMachines()
	{
		return this.machines;
	}
}
