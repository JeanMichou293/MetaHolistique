package lesmaitresdutemps;

import java.util.ArrayList;
import java.util.HashMap;

import lesmaitresdutemps.Interval;
import lesmaitresdutemps.IntervalException;

public class Job
{
	private int id;
	private ArrayList<Operation> operations = new ArrayList<Operation>();
	private int processingIndex; // Pointer on the next operation to be processed
	private Interval lastProcessedInterval;
	private HashMap<Operation, Interval> operationsInTime =
		new HashMap<Operation, Interval>();

	private class Gap
	{
		public Operation followingOp;
		public Interval interval;

		public Gap(Operation followingOp, Interval interval)
		{
			this.followingOp = followingOp;
			this.interval = interval;
		}
	}

	public Job(int id)
	{
		this.id = id;
		this.processingIndex = 0;
	}

	public void addOperation(Operation operation)
	{
		// Add new operation at the end
		this.operations.add(operation);
	}

	public int getId()
	{
		return this.id;
	}

	public Operation getFromQueue()
	{
		if (this.processingIndex < this.operations.size())
			return this.operations.get(this.processingIndex);
		else
			return null;
	}

	public void resetQueue()
	{
		this.processingIndex = 0;
	}

	public void setProcessed()
	{
		this.processingIndex = this.operations.size();
	}

	public Interval getLastProcessedInterval()
	{
		return this.lastProcessedInterval;
	}

	public void resetLastProcessedInterval()
	{
		this.lastProcessedInterval = null;
	}

	public void setProcessed(Operation operation, Interval interval)
	{
		this.lastProcessedInterval = interval;
		this.operationsInTime.put(operation, interval);
		this.processingIndex++;
	}

	public int getDuration()
	{
		Operation lastOperation =
			this.operations.get(this.operations.size() - 1);
		return this.operationsInTime.get(lastOperation).end();
	}

	public ArrayList<Operation> getOperations()
	{
		return this.operations;
	}

	public HashMap<Operation, Interval> getOperationsInTime()
	{
		return this.operationsInTime;
	}

	public void setOperations(ArrayList<Operation> operations)
	{
		this.operations = operations;
	}

	public void setOperationsInTime(HashMap<Operation, Interval> opInTime)
	{
		this.operationsInTime = opInTime;
	}

	private Gap getFirstGap() throws IntervalException
	{
		Interval lastInterval = null;
		for (Operation operation : this.operations) {
			Interval interval = this.operationsInTime.get(operation);
			if (lastInterval == null && interval.begin() > 0)
				return new Gap(operation, new Interval(0, interval.begin()));
			else if (lastInterval != null
				&& lastInterval.end() < interval.begin())
				return new Gap(operation,
					new Interval(lastInterval.end(), interval.begin()));
			else
				lastInterval = interval;
		}
		return null;
	}

	public void shift()
	{
		// Detect gap(s)
		Gap firstGap = null;
		try {
			firstGap = this.getFirstGap();
		} catch (IntervalException e) {
			e.printStackTrace();
		}
		if (firstGap == null)
			return;

		Operation opToShift = firstGap.followingOp;
		if (opToShift != null) {
			// Go to position
			int index = 0;
			for (Operation operation : this.operations) {
				if (operation == opToShift)
					break;
				index++;
			}

			int gapDuration =
				firstGap.interval.end() - firstGap.interval.begin();

			// Shift backward every operation following the gap
			for (int i = index; i < this.operations.size(); i++) {
				Operation op = this.operations.get(i);
				Interval interval = this.operationsInTime.get(op);
				interval.shift(-1 * gapDuration);
			}
		}
	}

	public String toString()
	{
		String str = "J" + this.id + "o0 (duration=" + this.getDuration() + ")";

		// XXX: debugging
		/*
		 * for (Operation operation : operations) { str += " --" + operation + "-> J" + this.id + "o" + (operation.getId() + 1); }
		 */

		return str;
	}
}
