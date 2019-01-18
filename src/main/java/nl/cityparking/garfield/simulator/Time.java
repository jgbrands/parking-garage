package nl.cityparking.garfield.simulator;

public class Time {
	private static final long minutesPerHour = 60;
	private static final long minutesPerDay = 60 * 24;
	private static final long minutesPerWeek = 60 * 24 * 7;

	private long tickSpeed = 100000000; // How many nanoseconds have to pass before we tick?
	private long lastTick = 0;

	private long minutesPerTick;
	private long minutes = 0;

	private long nextHour = minutesPerHour;
	private long nextDay = minutesPerDay;
	private long nextWeek = minutesPerWeek;

	private Runnable onTickFn = null;
	private Runnable onMinuteFn = null;
	private Runnable onHourFn = null;
	private Runnable onDayFn = null;
	private Runnable onWeekFn = null;

	public Time(long minutesPerTick) {
		this.minutesPerTick = minutesPerTick;
	}

	public void tick() {
		long currentTime = System.nanoTime();

		if (currentTime < this.lastTick + this.tickSpeed) {
			return;
		} else {
			this.lastTick = currentTime;
		}

		if (this.onTickFn != null) {
			this.onTickFn.run();
		}

		for (int i = 0; i < minutesPerTick; i++) {
			this.minutes++;

			// We start with the largest unit (weeks) here, because it is actually the most sensible thing to do.
			// The parameters of the nl.cityparking.garfield.simulator usually change every week, and since the smaller units depend on actions
			// from the larger units we do it this way.
			if (this.minutes == this.nextWeek) {
				this.nextWeek += minutesPerWeek;

				if (this.onWeekFn != null) {
					this.onWeekFn.run();
				}
			}

			if (this.minutes == this.nextDay) {
				this.nextDay += minutesPerDay;

				if (this.onDayFn != null) {
					this.onDayFn.run();
				}
			}

			if (this.minutes == this.nextHour) {
				this.nextHour += minutesPerHour;

				if (this.onHourFn != null) {
					this.onHourFn.run();
				}
			}

			if (this.onMinuteFn != null) {
				this.onMinuteFn.run();
			}
		}
	}

	public void onTick(Runnable f) {
		this.onTickFn = f;
	}

	public void onMinutePassed(Runnable f) {
		this.onMinuteFn = f;
	}

	public void onHourPassed(Runnable f) {
		this.onHourFn = f;
	}

	public void onDayPassed(Runnable f) {
		this.onDayFn = f;
	}

	public void onWeekPassed(Runnable f) {
		this.onWeekFn = f;
	}

	/**
	 * Calculates how many hours have passed in ticks.
	 * @return Number of hours that have passed.
	 */
	public long getHoursPassed() {
		return this.minutes / minutesPerHour;
	}

	/**
	 * Calculates the current hour of the current day. The returned value will be in the range of 0 .. 6, where 0
	 * represents 0:00, and 23 represents 23:00.
	 */
	public long getHourOfDay() {
		return this.getHoursPassed() % 24;
	}

	/**
	 * Calculates how many days have passed in ticks.
	 * @return Number of days that have passed.
	 */
	public long getDaysPassed() {
		return this.minutes / minutesPerDay;
	}

	/**
	 * Returns the current day of the current week. The returned value will be in the range of 0 .. 6, where 0
	 * represents the first day of the week (Monday) and 6 represents the last day of the week (Sunday.)
	 * @return The current day of the week.
	 */
	public long getDayOfWeek() {
		return getDaysPassed() % 7;
	}

	/**
	 * Calculates how many weeks have passed in ticks.
	 * @return Number of weeks that have passed.
	 */
	public long getWeeksPassed() {
		return this.minutes / minutesPerWeek;
	}

	/**
	 * Returns the time that to pass between ticks.
	 * @return The time in (real) nanoseconds.
	 */
	public long getTickSpeed() {
		return this.tickSpeed;
	}

	/**
	 * Sets the amount of time that has to pass between ticks in real life nanoseconds.
	 * @param tickSpeed The time to wait in nanoseconds.
	 */
	public void setTickSpeed(long tickSpeed) {
		this.tickSpeed = tickSpeed;
	}
}
