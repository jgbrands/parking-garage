package nl.cityparking.garfield.simulator.agent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Employment contains data about which days the agent works, which aids in
 * deciding when they arrive at the garage and when they leave.
 */
public class Employment {
	private ArrayList<WorkingHours> workingHours = new ArrayList<>();

	public void addWorkingHours(WorkingHours hours) {
		// TODO: Check if they overlap
		workingHours.add(hours);
		Collections.sort(workingHours);
	}

	public boolean isWorking(long hourOfWeek) {
		for (WorkingHours hours : workingHours) {
			if (hourOfWeek >= hours.startHour && hourOfWeek < hours.endHour) {
				return true;
			}
		}

		return false;
	}

	public boolean worksBetween(long begin, long end) {
		for (WorkingHours h: workingHours) {
			if (h.startHour >= begin && h.startHour <= end) {
				return true;
			}
		}

		return false;
	}

	public WorkingHours getNextWorkHour(long currentHour) {
		for (WorkingHours h: workingHours) {
			if (h.startHour >= currentHour) {
				return h;
			}
		}

		return null;
	}

	public final ArrayList<WorkingHours> getWorkingHours() {
		return workingHours;
	}

	public static class WorkingHours implements Comparable<WorkingHours> {
		private long startHour = 0;
		private long endHour = 0;

		public WorkingHours(long startHour, long endHour) {
			startHour = limit(startHour);
			endHour = limit(endHour);
			assert (startHour < endHour);

			this.startHour = startHour;
			this.endHour = endHour;
		}

		public static long limit(long hour) {
			if (hour < 0) {
				return 0;
			} else if (hour > 167) {
				return 167;
			}

			return hour;
		}

		public long getStartHour() {
			return startHour;
		}

		public long getEndHour() {
			return endHour;
		}

		@Override
		public int compareTo(@NotNull Employment.WorkingHours o) {
			return Long.compare(endHour, o.startHour);
		}
	}
}
