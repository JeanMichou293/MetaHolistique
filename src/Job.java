import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Job
{
	private int id;
	private ArrayList<Operation> operations = new ArrayList<Operation>();
	private LinkedList<Operation> queue = new LinkedList<Operation>();
	private LinkedList<Operation> processed = new LinkedList<Operation>();
	private HashMap<Operation, Interval> operationsInTime = new HashMap<Operation, Interval>();

	public Job(int id)
	{
		this.id = id;
	}

	public void addOperation(Operation operation)
	{
		// Add new operation at the end
		this.operations.add(operation);
		this.queue.add(operation);
	}

	public int getId()
	{
		return this.id;
	}

	public Operation getFromQueue()
	{
		return this.queue.peekFirst();
	}
	
	public void removeFromQueue()
	{
		this.queue.pop();
	}

	public Interval getLastProcessedInterval()
	{
		Operation operation = this.processed.peekLast();
		return this.operationsInTime.get(operation);
	}
	
	public void process(Operation operation, Interval interval)
	{
		this.processed.add(operation);
		this.operationsInTime.put(operation, interval);
	}

	public String toString()
	{
		String str = "";

		str += "J" + this.id + "o0";

		for (Operation operation : operations) {
			str += " --" + operation + "-> J" + this.id + "o" + (operation.getId() + 1);
		}

		return str;
	}
}
