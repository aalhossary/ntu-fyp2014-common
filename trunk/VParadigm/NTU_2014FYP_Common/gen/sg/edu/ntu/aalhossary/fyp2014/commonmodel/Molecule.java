package sg.edu.ntu.aalhossary.fyp2014.commonmodel;

import java.util.ArrayList;

public class Molecule implements Particle {

	protected String name;
	protected float molecularMass;
	protected String formula;
	protected float internalEnergy;
	protected ArrayList<Chain> chains;
	public Interaction interaction;

	public Molecule() {
		chains = new ArrayList<Chain>();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Chain> getChains() {
		return this.chains;
	}
	
	/**
	 * 
	 * @param chains
	 */
	protected void setChains(java.util.List<org.biojava.bio.structure.Chain> chains) {
		Chain chain;
		for(int i=0;i<chains.size();i++){
			chain = new Chain();
			chain.setChainName(chains.get(i).getChainID());
			chain.setChainSequence(chains.get(i));
			this.chains.add(chain);
		}
	}

	public Interaction getInteraction() {
		return this.interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

}