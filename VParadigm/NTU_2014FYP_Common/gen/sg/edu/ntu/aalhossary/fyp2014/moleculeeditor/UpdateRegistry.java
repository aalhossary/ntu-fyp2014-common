package sg.edu.ntu.aalhossary.fyp2014.moleculeeditor;

import java.util.ArrayList;
import java.util.List;

import javajs.util.P3;

import org.biojava.bio.structure.Structure;
import org.jmol.api.JmolViewer;
import org.jmol.java.BS;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.common.Atom;
import sg.edu.ntu.aalhossary.fyp2014.common.Model;

public class UpdateRegistry {
	JmolViewer viewer;
	List<Model> modelList;
	DataManager dataMgr;
	private ArrayList<Atom> selectedAtoms;
	
	public UpdateRegistry(JmolViewer viewer, List<Model> models){
		this.viewer = viewer;
		this.modelList = models;
		dataMgr = new DataManager();
	}
	
	/**********************************************************/
	/*** Methods that notify when there's changes in viewer ***/
	/**********************************************************/
	
	public void createUserModel(String fileName) {
		Structure structure = dataMgr.readFile(fileName);
		if(modelList==null)
			modelList = new ArrayList<Model>();
		Model model=null;
		for(int i=0;i<structure.nrModels();i++){
			// for each model, create and add to model list
			model = new Model();
			model.setModelName(structure.getPdbId());
			model.setMolecule(structure);	// set the model
			modelList.add(model);
		}
	}

	public void setSelectedAtoms(BS values) {
		System.out.println("Selected: " + values);
		if(selectedAtoms==null)
			selectedAtoms = new ArrayList<Atom>();
		else
			selectedAtoms.clear();
		

		String[] valueGet = values.toString().split("[{ }]");
		if(modelList!=null){
			Model currentModel = modelList.get(viewer.getDisplayModelIndex());
			for(int i=0;i<valueGet.length;i++){
				if(valueGet[i].compareTo("")!= 0){
					// if its list of continuous atoms
					if(valueGet[i].contains(":")){
						int start = Integer.parseInt(valueGet[i].split(":")[0])+1;
						int end = Integer.parseInt(valueGet[i].split(":")[1])+1;
						for(int j=start;j<=end;j++){
							selectedAtoms.add(currentModel.getAtomHash().get(currentModel.getModelName()+j));
						}
					}
					else{
						selectedAtoms.add(currentModel.getAtomHash().get(currentModel.getModelName()+(Integer.parseInt(valueGet[i])+1)));
					}
				}
			}
		}
	}
	
	public void evaluateUserAction(String script) {
		EvaluateUserAction userAction = new EvaluateUserAction(this);
		userAction.evaluateAction(script);
	}
	
	public void atomMoved(BS atomsMoved) {
		// get the list of atoms moved.
		setSelectedAtoms(atomsMoved);
		
		// update the atoms moved in user model.
		for(int i=0;i<selectedAtoms.size();i++){
			int atomIndex = selectedAtoms.get(i).getChainSeqNum()-1;
			double[] coords = {viewer.getAtomPoint3f(atomIndex).x,viewer.getAtomPoint3f(atomIndex).y,viewer.getAtomPoint3f(atomIndex).z};
			selectedAtoms.get(i).setCoordinates(coords);
		}
	}
	
	/*********************************************************/
	/*** Methods that notify when there's changes in model ***/
	/*********************************************************/
	
	// static method to display model from other platform
	public static void displayModels(List<Model> models, JmolViewer viewer){
		String pdb = modelsToPDB(models);	// convert model to pdb string
		viewer.openStringInline(pdb);
		
	}
	
	private static String modelsToPDB(List<Model> models) {
		for(int i=0;i<models.size();i++){
			
		}
		return null;
	}

	public void notifyUpdated(AbstractParticle[] particles){
		List<Atom> atomsUpdated = new ArrayList<Atom>();
		for(int i=0;i<particles.length;i++){
			if(particles[i] instanceof Atom){ // if particle is Atom type
				// get the updated atom from model
				atomsUpdated.add(modelList.get(0).getAtomHash().get(modelList.get(0).getModelName()+((Atom)particles[i]).getChainSeqNum()));
			}
		}
		
		// update atoms in jmol display
		for(int i=0;i<atomsUpdated.size();i++){
			Atom atom = atomsUpdated.get(i);
			float xOffset = atomsUpdated.get(i).getCoordinates()[0] - viewer.getAtomPoint3f(atomsUpdated.get(i).getChainSeqNum()-1).x;
			float yOffset = atomsUpdated.get(i).getCoordinates()[1] - viewer.getAtomPoint3f(atomsUpdated.get(i).getChainSeqNum()-1).y;
			float zOffset = atomsUpdated.get(i).getCoordinates()[2] - viewer.getAtomPoint3f(atomsUpdated.get(i).getChainSeqNum()-1).z;
			viewer.evalString("select atomno="+ atomsUpdated.get(i).getChainSeqNum());
			viewer.evalString("translateSelected {" + xOffset + " " + yOffset + " " + zOffset + "}");
		}
	}

	public ArrayList<Atom> getSelectedAtoms() {
		return selectedAtoms;
	}
}
