package sg.edu.ntu.aalhossary.fyp2014.common;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.aalhossary.fyp2014.moleculeeditor.MoleculeEditor;
import sg.edu.ntu.aalhossary.fyp2014.moleculeeditor.UpdateRegistry;

public class TestDisplayParticles {
	public static void main(String[] args){
		
		showTest();
		showTest2();
	}
	public static void showTest2() {
		MoleculeEditor editor = new MoleculeEditor();
		List<Model>models = new ArrayList<Model>();
		UpdateRegistry.displayModels(models, editor.getViewer());
		AbstractParticle p1 = new Atom();
		AbstractParticle p2 = new Atom();
		AbstractParticle[] particles = {p1, p2};
		editor.getMediator().notifyUpdated(particles);
	}

	public static void showTest() {
		MoleculeEditor editor = new MoleculeEditor();
		// coordinates of atoms must be within -50 to 50
		// NOTE FROM JMOL
		/* Moves the center of rotation to the specified value. 
		 * A value of 100 will move the molecule completely out of the window. 
		 * The value represents the percentage of the display window, and 0 is the window center. 
		 * A value of 50 will move the center of the molecule to the edge of the window. 
		 * Positive values are to the right of center for X and below center for Y.
		 */
		AbstractParticle p1 = new Atom();
		p1.movePositionBy(0, 10, 5, 0);
		AbstractParticle p2 = new Atom();
		p2.movePositionBy(-5, 0, -5, 0);
		editor.getMediator().displayParticles(p1, p2);
		
		// use this to update the moved particles
		AbstractParticle[] particles = {p1, p2};
		editor.getMediator().notifyUpdated(particles);
	}
	
	
}