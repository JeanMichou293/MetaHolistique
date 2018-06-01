import java.util.ArrayList;
import java.util.HashMap;

public class Job
{
	private int id;
	private ArrayList<Operation> operations = new ArrayList<Operation>();
	private int processingIndex; // Pointer on the next operation to be processed
	private Interval lastProcessedInterval;
	private HashMap<Operation, Interval> operationsInTime =
		new HashMap<Operation, Interval>();

	public Job(int id)
	{
		this.id = id;
	}

	public void addOperation(Operation operation)
	{
		// Add new operation at the end
		this.operations.add(operation);
		this.processingIndex = 0;
	}

	public int getId()
	{
		return this.id;
	}

	public Operation getFromQueue()
	{
		if (this.processingIndex < this.operations.size())
			return this.operations.get(this.processingIndex);
		else
			return null;
	}

	public Interval getLastProcessedInterval()
	{
		return this.lastProcessedInterval;
	}

	public void setProcessed(Operation operation, Interval interval)
	{
		this.lastProcessedInterval = interval;
		this.operationsInTime.put(operation, interval);
		this.processingIndex++;
	}

	public int getDuration()
	{
		Operation lastOperation =
			this.operations.get(this.operations.size() - 1);
		return this.operationsInTime.get(lastOperation).end;
	}

	public String toString()
	{
		String str = "J" + this.id + "o0";

		for (Operation operation : operations) {
			str += " --" + operation + "-> J" + this.id + "o"
				+ (operation.getId() + 1);
		}

		return str;
	}
}
