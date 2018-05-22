import java.util.ArrayList;
import java.util.Map.Entry;

public class Job
{
	private int id;
	private ArrayList<Operation> operations = new ArrayList<Operation>();

	public Job(int id)
	{
		this.id = id;
	}

	public void addOperation(Operation operation)
	{
		this.operations.add(operation);
	}

	public int getId()
	{
		return this.id;
	}

	public ArrayList<Operation> getOperations()
	{
		return this.operations;
	}
	
	public void print()
	{
		System.out.print("J" + this.id + "o0");
		for (Operation operation : operations) {
			System.out.print(" --");
			boolean first = true;
			for(Entry<Machine, Integer> entry : operation.getAffinities().entrySet()) {
				if (first){
					System.out.print(entry.getKey().getID()+"("+entry.getValue()+")");
					first = false;
				}
				else
					System.out.print("|"+entry.getKey().getID()+"("+entry.getValue()+")");
			}
			System.out.print("-> J" + this.id + "o" + (operation.getId()+1));
		}
		System.out.println();
	}
}
