public class Interval
{
	private int begin;
	private int end;

	public Interval(int begin, int end) throws IntervalException
	{
		if (begin > end) {
			throw new IntervalException(
				"You provided an invalid interval, moron!");
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

	public boolean overlaps(Interval interval)
	{
		return (this.end > interval.begin && this.begin < interval.end);
	}

	public int begin()
	{
		return this.begin;
	}

	public int end()
	{
		return this.end;
	}

	public String toString()
	{
		return "[" + this.begin + "," + this.end + "]";
	}
}
