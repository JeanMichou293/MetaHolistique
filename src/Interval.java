public class Interval
{
	public int begin;
	public int end;

	public Interval(int begin, int end)
	{
		this.begin = begin;
		this.end = end;
	}

	public boolean isIn(int time)
	{
		// Upper bound is excluded to allow operations to start at the end of others
		return this.begin <= time && this.end > time;
	}

	// TODO: more efficient: hashmap between time and time interval???
}
