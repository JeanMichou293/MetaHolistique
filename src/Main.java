public class Main
{
	public static void main(String[] args)
	{
		Graph g1 = new Graph(3);
		Job j1 = new Job("test");
		Operation o1 = new Operation("a");
		j1.addOperation(o1);
		g1.addJob(j1);
	}
}
