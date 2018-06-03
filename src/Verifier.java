import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Verifier
{
	public static boolean verify(Project project)
	{
		return verifyMachines(project) && verifyOrderAndOverlapping(project);
	}

	// Verify every machine processes no more than one operation simultaneously
	private static boolean verifyMachines(Project project)
	{
		ArrayList<Machine> machines = project.getMachines();
		for (Machine machine : machines) {
			int duration = project.getDuration();
			// TODO: ordered intervals => constant time
			for (int time = 0; time <= duration; time++) {
				// Find operations being processed
				boolean found = false;
				HashMap<Operation, Interval> operations =
					machine.getOperations();
				for (Entry<Operation, Interval> entry : operations.entrySet()) {
					if (entry.getValue().isIn(time)) {
						if (found)
							return false;
						else
							found = true;
					}
				}
			}
		}
		return true;
	}

	private static boolean verifyOrderAndOverlapping(Project project)
	{
		for (Job job : project.getJobs()) {
			// Iterate ordered operations
			Interval prevInterval = null;
			for (Operation operation : job.getOperations()) {
				Interval interval = job.getOperationsInTime().get(operation);
				if (interval != null && (prevInterval == null
					|| prevInterval.end() <= interval.begin()))
					prevInterval = interval;
				else
					return false;
			}
		}
		return true;
	}
}
