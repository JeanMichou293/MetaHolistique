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

	public int getID()
	{
		return this.id;
	}

	public ArrayList<Operation> getOperations()
	{
		return this.operations;
	}
}
