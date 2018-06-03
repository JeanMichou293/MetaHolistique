public interface JobSelector
{
	public Job selectReferenceJob(Project project);

	public String getName();
}
