package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.config.GarageLayout;
import nl.cityparking.garfield.simulator.parking.ParkingFloor;
import nl.cityparking.garfield.simulator.parking.ParkingLot;
import nl.cityparking.garfield.simulator.parking.ParkingSpace;
import nl.cityparking.garfield.simulator.parking.ParkingSpaceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * The ParkingManager is the representation of the 'physical' garage itself. It defines the floors it has, the lots
 * that each floor has and the amount of spaces each lot has. It is primarily responsible for managing and tracking
 * the state of the garage.
 *
 * Many queries can be performed upon the ParkingManager, such as which Agents are leaving the garage, or which cars
 * are parked invalidly. The ParkingManager also manages the influx of cars, assigning them to a spot.
 *
 * @author Jesse
 * @since 1.0
 */
public class ParkingManager {
	private ArrayList<ParkingFloor> floors = new ArrayList<>();

	private ForkJoinPool pool = ForkJoinPool.commonPool();
	private boolean useThreading = true;

	public void generateFromLayout(GarageLayout garageLayout) {
		for (GarageLayout.ParkingFloorLayout floorLayout: garageLayout.floorLayouts) {
			ParkingFloor floor = new ParkingFloor();

			for (GarageLayout.ParkingLotLayout lotLayout: floorLayout.lotLayouts) {
				ParkingLot lot = new ParkingLot();
				lot.addSpaces(lotLayout.openSpaces, ParkingSpaceType.OPEN);
				lot.addSpaces(lotLayout.passholderSpaces, ParkingSpaceType.PASS_HOLDER_ONLY);
				lot.addSpaces(lotLayout.disabledParkingSpaces, ParkingSpaceType.DISABLED_ONLY);
				floor.addLot(lot);
			}

			floors.add(floor);
		}
	}

	/**
	 * Processes the arrival of a single agent. This method will fail if all spots are occupied.
	 *
	 * @param arrival The Arrival to process.
	 * @return true if the Arrival was processed succesfully, false if it failed.
	 */
	public boolean handleArrival(Arrival arrival) {
		ParkingSpaceType preferredType = ParkingSpaceType.OPEN;
		
		if (arrival.agent.isDisabledParker() && parkArrival(arrival, ParkingSpaceType.DISABLED_ONLY)) {
			return true;
		}
		
		if (arrival.agent.hasParkingPass() && parkArrival(arrival, ParkingSpaceType.PASS_HOLDER_ONLY)) {
			return true;
		}
		
		return parkArrival(arrival, ParkingSpaceType.OPEN);
	}
	
	private boolean parkArrival(Arrival arrival, ParkingSpaceType type) {
		for (ParkingFloor floor: floors) {
			if (floor.getFreeSpots(type) > 0) {
				floor.parkArrival(arrival, type);
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns a collection of Agents that are leaving the parking garage. The returned list must be processed further
	 * by the exit queues of the simulator.
	 *
	 * This method will free up the spaces the leaving agents occupied, making them available to new arrivals.
	 *
	 * @param currentTime The current time in simulator minutes.
	 * @return A collection of Agents that have left their parking spaces.
	 */
	public Collection<Departure> getLeavingAgents(long currentTime) {
		if (useThreading) {
			return pool.invoke(new FindLeaversTask(floors, currentTime));
		}

		ArrayList<Departure> leavingAgents = new ArrayList<>();

		for (ParkingFloor floor: floors) {
			for (ParkingLot lot: floor.getParkingLots()) {
				if (lot.getOccupants() == 0) {
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

	/**
	 * Retrieves all floors in the ParkingGarage.
	 * @return A Collection of ParkingFloors.
	 */
	public Collection<ParkingFloor> getFloors() {
		return floors;
	}

	/**
	 * Enables or disables the use of DNC based, task-stealing threading in the operations of the simulator. Note that
	 * threading is always used, and this only disables or enables the use of divide-and-conquer, task stealing based
	 * concurrency.
	 *
	 * @param threading true to enable DNC, task-stealing concurrency; false to disable it
	 */
	public void setThreading(boolean threading) {
		useThreading = true;
	}
	
	public long getOccupation(ParkingSpaceType type) {
		return floors.parallelStream()
				.mapToLong(f -> f.getOccupiedSpots(type))
				.sum();
	}
	
	public long getFreeSpaces() {
		return floors.parallelStream()
				.mapToLong(ParkingFloor::getFreeSpots)
				.sum();
	}
	
	/**
	 * FindLeaversTask is a work dividing task, that creates WorkerTasks for each parking lot on every single floor.
	 *
	 * @author Jesse
	 * @since 1.0
	 * @see WorkerTask
	 */
	private static class FindLeaversTask extends RecursiveTask<Collection<Departure>> {
		private Collection<ParkingFloor> floors;
		private long currentTime;

		/**
		 * Creates an instance of FindLeaversTask
		 * @param floors A Collection of ParkingFloors to operate on.
		 * @param currentTime The current time in simulator minutes.
		 */
		public FindLeaversTask(Collection<ParkingFloor> floors, long currentTime) {
			this.floors = floors;
			this.currentTime = currentTime;
		}

		/**
		 * Divides the work in tasks and then returns the
		 * @return
		 */
		@Override
		protected Collection<Departure> compute() {
			ArrayList<WorkerTask> workerTasks = new ArrayList<>();

			// Create a task for each lot!
			for (ParkingFloor floor: floors) {
				for (ParkingLot lot: floor.getParkingLots()) {
					if (lot.getOccupants() == 0) {
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

		/**
		 * WorkerTask processes a ParkingLot, collects all leaving agents, frees up their ParkingSpaces and then
		 * returns a collection of the departing Agents.
		 *
		 * @author Jesse
		 * @since 1.0
		 */
		private static class WorkerTask extends RecursiveTask<Collection<Departure>> {
			private ParkingLot parkingLot;
			private long currentTime;

			/**
			 * Initializes an instance of WorkerTask.
			 *
			 * @param parkingLot The ParkingLot to process.
			 * @param currentTime The current time in simulation minutes.
			 */
			public WorkerTask(ParkingLot parkingLot, long currentTime) {
				this.parkingLot = parkingLot;
				this.currentTime = currentTime;
			}

			/**
			 * Collects all departing Agents, frees their spaces and returns the Collection of departing Agents.
			 * @return Collection of departing Agents.
			 */
			@Override
			protected List<Departure> compute() {
				return parkingLot.getSpaces().stream()
						.filter(ParkingSpace::isOccupied)
						.filter(s -> s.getOccupiedUntil() <= currentTime)
						.map(s -> parkingLot.freeSpace(s))
						.collect(Collectors.toList());
			}
		}
	}
}
