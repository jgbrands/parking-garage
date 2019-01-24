package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.parking.ParkingFloor;
import nl.cityparking.garfield.simulator.parking.ParkingLot;
import nl.cityparking.garfield.simulator.parking.ParkingSpace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class ParkingManager {
	private ArrayList<ParkingFloor> floors = new ArrayList<>();

	private ForkJoinPool pool = ForkJoinPool.commonPool();
	private boolean useThreading = false;

	public void addFloor(int lots, int lotSize) {
		floors.add(new ParkingFloor(lots, lotSize));
	}

	public void addFloors(int count, int lots, int lotSize) {
		for (int i = 0; i < count; i++) {
			floors.add(new ParkingFloor(lots, lotSize));
		}
	}

	public boolean handleArrival(Arrival arrival) {
		for (ParkingFloor floor: floors) {
			if (floor.getFreeSpots() > 0) {
				floor.parkArrival(arrival);
				return true;
			}
		}

		return false;
	}

	public List<Agent> getLeavingAgents(long currentTime) {
		if (useThreading) {
			return pool.invoke(new FindLeaversTask(floors, currentTime));
		}

		ArrayList<Agent> leavingAgents = new ArrayList<>();

		for (ParkingFloor floor: floors) {
			for (ParkingLot lot: floor.getParkingLots()) {
				if (lot.getAmountOfOccupants() == 0) {
					continue;
				}

				leavingAgents.addAll(lot.getSpaces().parallelStream()
						.filter(ParkingSpace::isOccupied)
						.filter(s -> s.getOccupiedUntil() <= currentTime)
						.map(lot::freeSpace)
						.collect(Collectors.toList()));
			}
		}

		return leavingAgents;
	}

	public ArrayList<ParkingFloor> getFloors() {
		return floors;
	}

	public void useThreading(boolean threading) {
		useThreading = true;
	}

	private static class FindLeaversTask extends RecursiveTask<List<Agent>> {
		private ArrayList<ParkingFloor> floors;
		private long currentTime;

		public FindLeaversTask(ArrayList<ParkingFloor> floors, long currentTime) {
			this.floors = floors;
			this.currentTime = currentTime;
		}

		@Override
		protected List<Agent> compute() {
			ArrayList<WorkerTask> workerTasks = new ArrayList<>();

			// Create a task for each lot!
			for (ParkingFloor floor: floors) {
				for (ParkingLot lot: floor.getParkingLots()) {
					if (lot.getAmountOfOccupants() == 0) {
						continue;
					}

					workerTasks.add(new WorkerTask(lot, currentTime));
				}
			}

			return ForkJoinTask.invokeAll(workerTasks).stream()
					.map(ForkJoinTask::join)
					.flatMap(Collection::stream)
					.collect(Collectors.toList());
		}

		private static class WorkerTask extends RecursiveTask<List<Agent>> {
			private final static int THRESHOLD = 400;

			private ParkingLot parkingLot;
			private long currentTime;

			public WorkerTask(ParkingLot parkingLot, long currentTime) {
				this.parkingLot = parkingLot;
				this.currentTime = currentTime;
			}

			@Override
			protected List<Agent> compute() {
				return parkingLot.getSpaces().stream()
						.filter(ParkingSpace::isOccupied)
						.filter(s -> s.getOccupiedUntil() <= currentTime)
						.map(s -> parkingLot.freeSpace(s))
						.collect(Collectors.toList());
			}
		}
	}
}
