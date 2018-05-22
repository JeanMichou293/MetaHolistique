public class Main
{
	public static void main(String[] args)
	{
		/*
		Project p1 = new Project();
		Job j1 = new Job(0), j2 = new Job(1);
		Operation o1 = new Operation(0), o2 = new Operation(1), o3 = new Operation(2);
		j1.addOperation(o1);
		j1.addOperation(o2);
		j2.addOperation(o3);
		p1.addJob(j2);
		p1.addJob(j1);
		p1.print();
		*/
		
		Project p2 = Parser.parse(Parser.readFile("C:\\Users\\Adrian\\Documents\\GitHub\\MetaHolistique\\textJobData\\Barnes\\mt10c1.fjs"));
		p2.print();
		
	}
}
