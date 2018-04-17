import java.util.ArrayList;
import java.util.HashMap;

public class Operation {
	
	
	String id;
	
	ArrayList<Operation> edges_in;
	ArrayList<Operation> edges_out;
	
	HashMap<Integer,Integer> machines;
	
	public Operation (String id) {
		
		this.id = id;

		machines = new HashMap<Integer,Integer>();
		
	}
	
	public void addMachine(int machine,int time) {
		
		this.machines.put(machine, time);
		
	}
	
	public void addEdgeIn(Operation node) {
		
		this.edges_in.add(node);
		
	}
	
	public void addEdgeOut(Operation node) {
		
		this.edges_out.add(node);
		
	}
	
	public String getID() {
		
		return this.id;
		
	}
	
	public ArrayList<Operation> getEdgeIn() {
		
		return this.edges_in;
		
	}
	
	public ArrayList<Operation> getEdgeOut() {
		
		return this.edges_out;
		
	}
	
	public HashMap<Integer,Integer> getMachines() {
		
		return this.machines;
		
	}
	
	

}
