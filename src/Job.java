import java.util.ArrayList;

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
		boolean first = true;
		for (Operation operation : operations) {
			if (!first)
				System.out.print(" -> ");
			else
				first = false;
			System.out.print("J" + this.id + "o" + operation.getId());
		}
		System.out.println();
	}
}
