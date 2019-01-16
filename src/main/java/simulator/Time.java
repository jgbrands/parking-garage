package simulator;

public class Time {
	private long minutes = 0;
	private long minutesPerTick;

	private Runnable onTickFn = null;

	public Time(long minutesPerTick) {
		this.minutesPerTick = minutesPerTick;
	}

	/**
	 * Increments time
	 */
	public void tick() {
		this.minutes += minutesPerTick;
		this.onTickFn.run();
	}

	public void onTick(Runnable f) {
		this.onTickFn = f;
	}

	/**
	 * Calculates how many hours have passed in ticks.
	 * @return Number of hours that have passed.
	 */
	public long getHoursPassed() {
		return this.minutes / 60;
	}

	/**
	 * Calculates how many days have passed in ticks.
	 * @return Number of days that have passed.
	 */
	public long getDaysPassed() {
		return this.minutes / 60 / 24;
	}

	/**
	 * Calculates how many weeks have passed in ticks.
	 * @return Number of weeks that have passed.
	 */
	public long getWeeksPassed() {
		return this.minutes / 60 / 24 / 7;
	}
}
