import java.util.ArrayList;

public class Machine
{
	private int id;
	private ArrayList<Operation> batch = new ArrayList<Operation>();

	public Machine(int id)
	{
		this.id = id;
	}
	
	public static ArrayList<Machine> createMachines(int count)
	{
		ArrayList<Machine> machines = new ArrayList<Machine>();
		for (int i = 0; i < count; i++) {
			machines.add(new Machine(i));
		}
		
		return machines;
	}
}
