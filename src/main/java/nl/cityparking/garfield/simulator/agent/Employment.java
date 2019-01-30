package nl.cityparking.garfield.simulator.agent;

import java.util.ArrayList;

/**
 * Employment is an abstraction of an agents work.
 *
 * @author jesse
 * @since 1.0
 */
public class Employment {
	public final Employer employer;
	public final Position position;
	
	public Employment(Employer employer, Position position) {
		this.employer = employer;
		this.position = position;
	}
}
