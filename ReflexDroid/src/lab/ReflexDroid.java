package lab;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.impl.AbstractAgent;
import aima.core.agent.impl.aprog.SimpleReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.EQUALCondition;
import aima.core.agent.impl.aprog.simplerule.Rule;


public class ReflexDroid extends AbstractAgent {

	
	public ReflexDroid() {
		super(new SimpleReflexAgentProgram(getRuleSet()));
	}
	
	
	private static Set<Rule> getRuleSet() {
		Set<Rule> rules = new LinkedHashSet<Rule>();

		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE,
				AmatriceEnviroment.LocationState.None),
				AmatriceEnviroment.ACTION_MOVE_FORWARD));
		
		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE,
				AmatriceEnviroment.LocationState.Rock),
				AmatriceEnviroment.ACTION_TURN_RIGHT_BY_90_DEGREES));
		
		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE,
				AmatriceEnviroment.LocationState.Limit),
				AmatriceEnviroment.ACTION_TURN_RIGHT_BY_90_DEGREES));
		
		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE,
				AmatriceEnviroment.LocationState.Human),
				AmatriceEnviroment.ACTION_GRAB));
		
		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE, 
				AmatriceEnviroment.LocationState.Final),
				AmatriceEnviroment.ACTION_TAKE_OFF));
		
		// Add new RULES according to the Amatrice world specification
		
		
		return rules;
	}
}
