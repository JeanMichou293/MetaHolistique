import java.util.ArrayList;

public class Machine
{
	private int id;
	private ArrayList<Operation> batch = new ArrayList<Operation>();

	public Machine(int id)
	{
		this.id = id;
	}

	public int getID()
	{
		return id;
	}
	public ArrayList<Operation> getBatch()
	{
		return batch;
	}
}
