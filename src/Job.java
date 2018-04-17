import java.util.ArrayList;

public class Job
{
	String id;
	ArrayList<Operation> operations = new ArrayList<Operation>();

	public Job(String id)
	{
		this.id = id;
	}

	public void addOperation(Operation operation)
	{
		this.operations.add(operation);
	}

	public String getID()
	{
		return this.id;
	}

	public ArrayList<Operation> getOperations()
	{
		return this.operations;
	}
}
