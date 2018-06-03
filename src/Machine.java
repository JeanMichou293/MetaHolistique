import java.util.HashMap;

public class Machine
{
	private int id;
	private HashMap<Operation, Interval> operationsInTime =
		new HashMap<Operation, Interval>();

	public Machine(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public boolean isBusy(Interval interval)
	{
		// TODO: ordered intervals => constant time
		for (Operation operation : this.operationsInTime.keySet()) {
			if (this.operationsInTime.get(operation).overlaps(interval))
				return true;
		}
		return false;
	}

	public void assignOperation(Operation operation, Interval interval)
	{
		operationsInTime.put(operation, interval);
	}

	public HashMap<Operation, Interval> getOperations()
	{
		return this.operationsInTime;
	}

	public void setOperations(HashMap<Operation, Interval> operations)
	{
		this.operationsInTime = operations;
	}

	public String toString()
	{
		return "M" + Integer.toString(this.id);
	}
}
