public class Main
{
	public static void main(String[] args)
	{
		Graph g1 = new Graph();
		Job j1 = new Job(0);
		Operation o1 = new Operation(0);
		j1.addOperation(o1);
		g1.addJob(j1);
	}
}
