package lab;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.agent.impl.DynamicAction;

public class AmatriceEnviroment extends AbstractEnvironment {
	// Add new Actions
	public static final Action ACTION_MOVE_FORWARD = new DynamicAction("Forward");
	public static final Action ACTION_GRAB = new DynamicAction("Grab");
	public static final Action ACTION_TAKE_OFF = new DynamicAction("Take Off");
	public static final Action ACTION_TURN_RIGHT_BY_90_DEGREES = new DynamicAction("Turn Right by 90 degrees");
	public static final Action ACTION_TURN_LEFT_BY_90_DEGREES = new DynamicAction("Turn Left by 90 degrees");

	public enum LocationState {
		Human, None, Rock, Final, Limit
	};

	protected AmatriceEnvironmentState envState = null;

	public AmatriceEnviroment() {
		envState = new AmatriceEnvironmentState();
	}

	private boolean facingUp = false;
	private boolean facingDown = false;
	private boolean facingLeft = true;
	private boolean facingRight = false;
	
	private int agentXPosition, agentYPosition;
	
	@Override
	public void executeAction(Agent agent, Action action) {
		String currentLocation, nextLocation;
		LocationState state;
		
		if(getNextLocationState() == LocationState.Rock){
			System.out.println("Meh... a rock");
		}
		else if(getNextLocationState() == LocationState.Limit){
			System.out.println("Meh... end of the line");
		}
		if(getNextLocationState() == null){
			System.out.println("HELP! I'M LOST!");
		}
		
		if (ACTION_MOVE_FORWARD == action) {

			state = envState.getLocationState(envState.getAgentLocation(agent));
			if (state == LocationState.None) {
				System.out.println("nothing here");
			} else if (state == LocationState.Rock) {
				System.out.println("Meh... a rock");
			}
			
			// get current agent location
			agentXPosition = getAgentXPosition(envState.getAgentLocation(agent));
			agentYPosition = getAgentYPosition(envState.getAgentLocation(agent));
			currentLocation = envState.getAgentLocation(agent);
			
			// increment current agent location based on the direction it is facing
			
			System.out.println("Agent Position: "+agentXPosition + ", " + agentYPosition);
			
			if (facingUp){
				//inttXPosition+1) +","+ agentYPositi nextLocation = Integer.parseInt(currentLocation) + 1;
				System.out.println("Moving up");
				nextLocation = (agentXPosition +","+ (agentYPosition+1));
				envState.setLocationState(currentLocation, LocationState.None);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else if (facingDown){
				//int nextLocation = Integer.parseInt(currentLocation) + 1;
				System.out.println("Moving down");
				nextLocation = (agentXPosition +","+ (agentYPosition-1));
				envState.setLocationState(currentLocation, LocationState.None);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else if (facingRight){
				//int nextLocation = Integer.parseInt(currentLocation) + 1;
				System.out.println("Moving right");
				nextLocation = ((agentXPosition+1) +","+ agentYPosition);
				envState.setLocationState(currentLocation, LocationState.None);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else if (facingLeft){
				//int nextLocation = Integer.parseInt(currentLocation) + 1;
				System.out.println("Moving left");
				nextLocation = ((agentXPosition-1) +","+ agentYPosition);
				envState.setLocationState(currentLocation, LocationState.None);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			
			if(proximitySensor()){}
				
			
			

		}
		if (ACTION_GRAB == action) {
			
			currentLocation = envState.getAgentLocation(agent);

			state = envState.getLocationState(currentLocation);
			if (state == LocationState.Human) {
				System.out.println("A HUMAN!! WHAT SHOULD I DO??");
				
				envState.setLocationState(envState.getAgentLocation(agent), LocationState.None);
				this.updatePerformanceMeasure(agent, 250);
			}
		}
		if(ACTION_TAKE_OFF == action){
			
			currentLocation = envState.getAgentLocation(agent);

			state = envState.getLocationState(currentLocation);			
			if (state == LocationState.Final) {
				System.out.println("My job here is done!");
				
			}
			
		}
		if(ACTION_TURN_RIGHT_BY_90_DEGREES == action){
			
			if(facingUp){
				facingRight = true;
				facingUp = false;
				nextLocation = ((agentXPosition+1) +","+ agentYPosition);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else if(facingDown){
				facingLeft = true;
				facingDown = false;
				nextLocation = ((agentXPosition-1) +","+ agentYPosition);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else if(facingRight){
				facingDown = true;
				facingRight = false;
				nextLocation = (agentXPosition +","+ (agentYPosition-1));
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else if(facingLeft){
				facingUp = true;
				facingLeft = false;
				nextLocation = (agentXPosition +","+ (agentYPosition+1));
				envState.setAgentLocation(agent, nextLocation + "");
			}
			this.updatePerformanceMeasure(agent, -0.5);
			
		}
		if(ACTION_TURN_LEFT_BY_90_DEGREES == action){
			if(facingUp){
				facingLeft = true;
				facingUp = false;
			}
			else if(facingDown){
				facingRight = true;
				facingDown = false;
			}
			else if(facingRight){
				facingUp = true;
				facingRight = false;
			}
			else if(facingLeft){
				facingDown = true;
				facingLeft = false;
			}
		}

		
		// Complete the other actions
	}

	@Override
	public void addAgent(Agent a) {
		envState.setAgentLocation(a, "1,1");
		super.addAgent(a);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		String agentLocation = envState.getAgentLocation(anAgent);
		return new AmatriceEnvironmentPercept(agentLocation, envState.getLocationState(agentLocation));
	}
	
	private int getAgentXPosition(String pos){
		String[] tokens = pos.split(",");
		return Integer.parseInt(tokens[0]);
		
	}
	
	private int getAgentYPosition(String pos){
		String[] tokens = pos.split(",");
		return Integer.parseInt(tokens[1]);
		
	}
	
	public LocationState getNextLocationState(){
		if(facingUp)
			return envState.getLocationState(agentXPosition + "," + (agentYPosition + 1));
		else if(facingDown)
			return envState.getLocationState(agentXPosition + "," + (agentYPosition - 1));
		else if(facingRight)
			return envState.getLocationState((agentXPosition+1) + "," + agentYPosition);
		else
			return envState.getLocationState((agentXPosition-1) + "," + agentYPosition);
	}
	
	public boolean proximitySensor(){
		if ((this.getNextLocationState() == LocationState.Rock) || this.getNextLocationState() == null)
			return true;
		return false;
	}

}
