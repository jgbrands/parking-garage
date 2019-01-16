package simulator;

import java.util.Random;

public class Simulator {
	private static final String AD_HOC = "1";
	private static final String PASS = "2";

	private CarQueue entranceQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private Time simulationTime;

    private int day = 0;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    public Simulator() {
        entranceQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
    }

    public void run() {
    	this.simulationTime = new Time(1);
	    this.simulationTime.onTick(this::onTick);

        while (true) {
        	this.simulationTime.tick();
        }
    }

    private void onTick() {
	    handleExit();
	    handleEntrance();
    }

    private void handleEntrance() {
    	carsArriving();
    	carsEntering(entranceQueue);
    }
    
    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);    	
    }

	private void carsEntering(CarQueue queue){

    }

    private void carsReadyToLeave(){

    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch (type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceQueue.addCar(new AdHocCar());
            }
            break;
    	}
    }
    
    private void carLeavesSpot(Car car){
        exitCarQueue.addCar(car);
    }
}
