import java.util.ArrayList;
import java.util.HashMap;

public class Solution
{
	private Project project;
	private int cost;

	public Solution(Project project)
	{
		this.project = project;
	}

	public boolean betterThan(Solution solution)
	{
		return this.cost < solution.cost;
	}

	public void shift()
	{
		// Detect gap(s) (first?) in the critical path (longer job)
		Job longerJob = this.project.getLongerJob();
		Operation opToShift = null;
		try {
			opToShift = getOpAfterFirstGap(longerJob);
		} catch (IntervalException e) {
			e.printStackTrace();
		}

		if (opToShift != null) {
			ArrayList<Operation> operations = longerJob.getOperations();
			HashMap<Operation, Interval> operationsInTime =
				longerJob.getOperationsInTime();
			// Go to position
			int index = 0;
			for (Operation operation : operations) {
				if (operation == opToShift)
					break;
				index++;
			}

			// Get gap duration
			int gapBegin;
			if (index > 0)
				gapBegin = operationsInTime.get(operations.get(index - 1)).begin;
			else
				gapBegin = 0;
			int gapEnd = operationsInTime.get(opToShift).begin;
			int gapDuration = gapEnd - gapBegin;

			// Shift backward every operation following the gap
			for (int i = index; i < operations.size(); i++) {
				Operation op = operations.get(i);
				Interval interval = operationsInTime.get(op);
				interval.shift(-1 * gapDuration);
			}
		}
	}

	private static Operation getOpAfterFirstGap(Job job)
		throws IntervalException
	{
		Interval lastInterval = null;
		for (Operation operation : job.getOperations()) {
			Interval interval = job.getOperationsInTime().get(operation);
			if ((lastInterval == null && interval.begin > 0)
				|| (lastInterval.end < interval.begin))
				return operation;
			else
				lastInterval = interval;
		}
		return null;
	}
}