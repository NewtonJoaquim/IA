package lab;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import aima.core.agent.Agent;
import aima.core.agent.EnvironmentState;
import lab.AmatriceEnviroment.LocationState;

public class AmatriceEnvironmentState implements EnvironmentState, FullyObservableAmatriceEnvironmentPercept, Cloneable {

	private Map<String, AmatriceEnviroment.LocationState> state;
	private Map<Agent, String> agentLocations;
	
	/**
	 * Constructor
	 */
	public AmatriceEnvironmentState() {
		state = new LinkedHashMap<String, AmatriceEnviroment.LocationState>();
		agentLocations = new LinkedHashMap<Agent, String>();
		initWorld();
	}

	private void printWorld(int rows, int columns){
		for(int i = 0; i<=columns+1;i++){
			for(int j = 0; j<=rows+1; j++){
				if(state.get(j + "," + i).equals(LocationState.Human))
					System.out.print("H");
				else if(state.get(j + "," + i).equals(LocationState.None))
					System.out.print("0");
				else if(state.get(j + "," + i).equals(LocationState.Rock))
					System.out.print("R");
				else if(state.get(j + "," + i).equals(LocationState.Final))
					System.out.print("F");
				else if(state.get(j + "," + i).equals(LocationState.Limit))
					System.out.print("L");
			}
			System.out.println();
		}
	}
	
	private void initWorld() {
		// TODO Auto-generated method stub
		// Fill the AmatriceEnviroment specification
		// call the fill method bellow filling each stage
		
		// Example:
		// fill(LocationState.Human, LocationState.Rock, LocationState.Human);
		boolean thereIsAHuman = false;
		//Random numberOfStages = new Random();
		Random stateRand = new Random();
		
		//int n = numberOfStages.nextInt(15 - 8 + 1) + 8; // Random value between 8-15
		int numberOfRows = 15;
		int numberOfColumns = 20;
		this.state.clear();
		
		for (int i = 0; i <= numberOfRows + 1; i++) {
			for(int j = 0; j <= numberOfColumns + 1; j++){
				
				if(i == 0 ||i == numberOfRows + 1 || j == 0 || j == numberOfColumns + 1)
					this.state.put(i + "," + j, LocationState.Limit);
				
				else if(stateRand.nextInt(100)<=10){	//This means, for [0 1 2 ... 100] if the value is <= 10, its a 10% chance event
					this.state.put(i + "," + j, LocationState.Human);
					thereIsAHuman = true;
				}
				else if(stateRand.nextInt(100)<=33){
					this.state.put(i + "," + j, LocationState.Rock);
				}
				else 
					this.state.put(i + "," + j, LocationState.None);
			}
		}
		
		this.state.remove(numberOfRows + "," + numberOfColumns);
		this.state.put(numberOfRows + "," + numberOfColumns, LocationState.Final); // Defines the take off location at the end of the enviroment
		//this.state.put((n -1) + "", LocationState.Human);
		printWorld(numberOfRows, numberOfColumns);
	}
	
	

	@Override
	public String getAgentLocation(Agent a) {
		return agentLocations.get(a);
	}

	/**
	 * Sets the agent location
	 * 
	 * @param a
	 * @param location
	 */
	public void setAgentLocation(Agent a, String location) {
		agentLocations.put(a, location);
	}

	@Override
	public AmatriceEnviroment.LocationState getLocationState(String location) {
		return state.get(location);
	}

	/**
	 * Sets the location state
	 * 
	 * @param location
	 * @param s
	 */
	public void setLocationState(String location, AmatriceEnviroment.LocationState s) {
		state.put(location, s);
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() == obj.getClass()) {
			AmatriceEnvironmentState s = (AmatriceEnvironmentState) obj;
			return state.equals(s.state) && agentLocations.equals(s.agentLocations);
		}
		return false;
	}

	/**
	 * Override hashCode()
	 * 
	 * @return the hash code for this object.
	 */
	@Override
	public int hashCode() {
		return 3 * state.hashCode() + 13 * agentLocations.hashCode();
	}

	@Override
	public AmatriceEnvironmentState clone() {
		AmatriceEnvironmentState result = null;
		try {
			result = (AmatriceEnvironmentState) super.clone();
			result.state = new LinkedHashMap<String, LocationState>(state);
			agentLocations = new LinkedHashMap<Agent, String>(agentLocations);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns a string representation of the environment
	 * 
	 * @return a string representation of the environment
	 */
	@Override
	public String toString() {
		return this.state.toString();
	}

	public boolean isAgentAtHome(Agent agent) {
		// TODO Auto-generated method stub
		int actualLocation = Integer.parseInt(this.getAgentLocation(agent));
		return actualLocation == state.size();
	}

}
