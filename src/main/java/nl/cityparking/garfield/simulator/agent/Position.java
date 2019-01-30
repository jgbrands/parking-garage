package nl.cityparking.garfield.simulator.agent;

import java.util.ArrayList;

public class Position {
	public final String name;
	private double baseHourlyWage = 15.0;
	private long occupiedVacancies = 0;
	private long totalVacancies = 0;
	private Schedule schedule = new Schedule();
	
	public Position(String name) {
		this.name = name;
	}
	
	public double getBaseHourlyWage() {
		return baseHourlyWage;
	}
	
	public void setBaseHourlyWage(double baseHourlyWage) {
		this.baseHourlyWage = baseHourlyWage;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public long getTotalVacancies() {
		return totalVacancies;
	}
	
	public boolean setTotalVacancies(long totalVacancies) {
		if (totalVacancies > this.totalVacancies || totalVacancies >= this.totalVacancies - occupiedVacancies) {
			this.totalVacancies = totalVacancies;
			return true;
		}
		
		return false;
	}
	
	public long getOpenVacancies() {
		return totalVacancies - occupiedVacancies;
	}
	
	public synchronized boolean popVacancy() {
		if (getOpenVacancies() > 0) {
			occupiedVacancies++;
			return true;
		}
		
		return false;
	}
	
	public synchronized void freeVacancy() {
		if (occupiedVacancies > 0) {
			occupiedVacancies--;
		}
	}
}
