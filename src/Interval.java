public class Interval
{
	public int begin;
	public int end;

	public Interval(int begin, int end) throws IntervalException
	{
		if (begin > end) {
			throw new IntervalException(
				"You provided a wrong interval, moron!");
		} else {
			this.begin = begin;
			this.end = end;
		}
	}

	public boolean isIn(int time)
	{
		// Upper bound is excluded to allow operations to start at the end of others
		return this.begin <= time && this.end > time;
	}
	
	public void shift(int duration)
	{
		this.begin += duration;
		this.end += duration;
	}

	// TODO: more efficient: hashmap between time and time interval???
}
