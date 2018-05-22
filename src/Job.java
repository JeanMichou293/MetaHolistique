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
	
	public String toString()
	{
		String str = "";
		
		str+="J" + this.id + "o0";
		
		for (Operation operation : operations) {
			str+=" --"+operation+"-> J" + this.id + "o" + (operation.getId()+1);
		}
		
		return str;
	}
}
