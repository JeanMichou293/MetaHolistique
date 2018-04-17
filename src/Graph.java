import java.util.ArrayList;

public class Graph {
	
	int nb_machines;
	
	ArrayList<Operation> nodes;
	
	Operation start;
	Operation end;
	
	public Graph(int nb_machines) {
		
		nodes = new ArrayList<Operation>();
		
		nodes.add(new Operation("Start"));
		nodes.add(new Operation("End"));
		
	}
	
	public void addJob(Job j) {
		
		ArrayList<Operation> operations = j.getOperations();
		
		if (operations.size() > 0) {
			
			this.start.addEdgeOut(operations.get(0));
			operations.get(0).addEdgeIn(this.start);

			for (int index = 0 ; index < operations.size()-1 ; index++) {
				
				operations.get(index).addEdgeOut(operations.get(index+1));
				operations.get(index+1).addEdgeIn(operations.get(index));
				
			}

			operations.get(operations.size()-1).addEdgeOut(this.end);
			this.end.addEdgeIn(operations.get(operations.size()-1));
			
		}
		
	}
	
	
	

}
