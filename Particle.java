// Author: Karl Mason

// This is an implementation of Particle Swarm Optimisation with Avoidance of Worst Locations. The algorithm
// is applied to 7 benchmark functions and has implemented 4 topologies. 

// Please use the following bib files to cite the papers relevant to this algorithm

/* 
 * 
 * @article{mason2017multi,
  title={Multi-objective dynamic economic emission dispatch using particle swarm optimisation variants},
  author={Mason, Karl and Duggan, Jim and Howley, Enda},
  journal={Neurocomputing},
  volume={270},
  pages={188--197},
  year={2017},
  publisher={Elsevier}
}
 * 
 * @article{mason2016exploring,
  title={Exploring avoidance strategies and neighbourhood topologies in particle swarm optimisation},
  author={Mason, Karl and Howley, Enda},
  journal={International Journal of Swarm Intelligence},
  volume={2},
  number={2-4},
  pages={188--207},
  year={2016},
  publisher={Inderscience Publishers (IEL)}
}

@article{mason2018meta,
  title={A meta optimisation analysis of particle swarm optimisation velocity update equations for watershed management learning},
  author={Mason, Karl and Duggan, Jim and Howley, Enda},
  journal={Applied Soft Computing},
  volume={62},
  pages={148--161},
  year={2018},
  publisher={Elsevier}
}

 * @incollection{mason2015avoidance,
  title={Avoidance strategies in particle swarm optimisation},
  author={Mason, Karl and Howley, Enda},
  booktitle={Mendel 2015},
  pages={3--15},
  year={2015},
  publisher={Springer}
}

 */

package PSO;
import java.util.ArrayList;

public class Particle {
	Function fn;
	public double bFitness; // best personal fitness evaluation
	public double wFitness;
	public double cFitness; // current personal fitness evaluation
	public double lBestFitness; // best local fitness ***** not used for global topology
	public double lWorstFitness;
	
	public double vmax;
	
	public int p;
	public double c2;
	public double c1;
	public double c3;
	public double c4;
	public double w = 1; // inertia
	public double con = 0.7298437881;
//	public double con = 1;

	ArrayList<Double> UpperBoundaries = new ArrayList<Double>(); // boundaries
	ArrayList<Double> LowerBoundaries = new ArrayList<Double>();
	ArrayList<Double> Positions = new ArrayList<Double>(); // array of positions 
	ArrayList<Double> Velocities = new ArrayList<Double>(); // array of velocities
	ArrayList<Double> bestPositions = new ArrayList<Double>(); // array of best positions
	ArrayList<Double> worstPositions = new ArrayList<Double>();
	ArrayList<Double> localBestPositions = new ArrayList<Double>(); // array of best positions in neighbourhood
	ArrayList<Double> localWorstPositions = new ArrayList<Double>();
																	
	public boolean local = false;
	public boolean Boundary; 

	public int Fast = 0; // number of max velocity violations
	public int D; // number of dimensions
	
	public double curI = 0;
	public double Imax = 10000;
	double n;
	
	ArrayList<Integer> particlesInNeighbourhood = new ArrayList<Integer>();
	ArrayList<Integer> particlesNotInNeighbourhood = new ArrayList<Integer>();
	public double H;

	public Particle(int p, int dimensions, Function fn, ArrayList<Double> UpperBoundaries, ArrayList<Double> LowerBoundaries, boolean Boundary) { // initialize particle
		this.p = p;
		this.D = dimensions;
		this.fn = fn;
		this.UpperBoundaries = UpperBoundaries;
		this.LowerBoundaries = LowerBoundaries;
		this.Boundary = Boundary;
		vmax = (UpperBoundaries.get(0)-LowerBoundaries.get(0)) ;
		for (int i = 0; i < D; i++) {	
			double startPos = (double) (Math.random() *( UpperBoundaries.get(i) - LowerBoundaries.get(i)) ) +LowerBoundaries.get(i);
			Positions.add(startPos);			
			double startVel = (double) (Math.random() *( UpperBoundaries.get(i) - LowerBoundaries.get(i)) ) +LowerBoundaries.get(i);
			startVel = (startVel-startPos)/2;			
			Velocities.add(startVel);
			bestPositions.add(Positions.get(i));
			worstPositions.add(Positions.get(i));
			localBestPositions.add(bestPositions.get(i));
			localWorstPositions.add(bestPositions.get(i));
		}
		
		n = (double)p/4.0;
	
		bFitness = fn.FitnessCheck(Positions);
		wFitness = bFitness;
		c1 = 1.845;
		c2 = 1.845;
		c3 = 0.205;
		c4 = 0.205;
		
		for(int i=0;i<50;i++){
			particlesNotInNeighbourhood.add(i);
		}
		for(int i=0;i<3;i++){
			double temp = Math.random()*(particlesNotInNeighbourhood.size());
			int t = (int) Math.floor(temp);
			int n = particlesNotInNeighbourhood.get(t);
			particlesInNeighbourhood.add(n);
			particlesNotInNeighbourhood.remove(t);
		}
		
	}


	public void setInertia(double i, double itter) {
		w = 0.9 - ((i / itter) * 0.8);
	}

	public void setC(double i, double itter) {
		//c1 = 1.64 - ((i / itter) * 0.615);
		//c2 = 1.64 - ((i / itter) * 0.615);
		//c3 = 3.075 - ((i / itter) * 2.46);
		//c4 = 3.075 - ((i / itter) * 2.46);
	}
	
	public void evaulate() { // evaluates position
		local = false;
		cFitness = fn.FitnessCheck(Positions);
		if (cFitness < bFitness) {
			bFitness = cFitness;
			for(int i=0;i<D;i++){
				bestPositions.set(i,Positions.get(i));
			}
		}
		if (cFitness > wFitness) {
			wFitness = cFitness;
			for(int i=0;i<D;i++){
				worstPositions.set(i,Positions.get(i));
			}
		}

	}

	public void evaulate(ArrayList<ArrayList<Double>> lPositions, ArrayList<Double> lFitness) { // evaluates position
		local = true;
		cFitness = fn.FitnessCheck(Positions);
		if (cFitness < bFitness) {
			bFitness = cFitness;
			for(int i=0;i<D;i++){
				bestPositions.set(i,Positions.get(i));
			}
		}
		if (cFitness > wFitness) {
			wFitness = cFitness;
			for(int i=0;i<D;i++){
				worstPositions.set(i,Positions.get(i));
			}
		}		
		
	}

	public void update(ArrayList<Double> gBestPositions,ArrayList<Double> gWorstPositions) {

		ArrayList<Double> gBestPos = gBestPositions;
		ArrayList<Double> gWorstPos = gBestPositions;
		if (gBestPos.size() == 0) {
			gBestPos = Positions;
		}
		if (gWorstPos.size() == 0) {
			gWorstPos = Positions;
		}

		if (local == false) {
			for (int i = 0; i < D; i++) { // updates velocities
				double vPrev = Velocities.get(i);
				double t1=(Math.random() * c1 * (bestPositions.get(i) - Positions.get(i)));
				double t2=(Math.random() * c2 * (gBestPos.get(i) - Positions.get(i)));
				
				double t3=(Math.random() * c3 * (t1/(1 + Math.abs(Positions.get(i) - worstPositions.get(i)))));
				double t4=(Math.random() * c4 * (t2/(1 + Math.abs(Positions.get(i) - gWorstPos.get(i)))));
		//		double newVel = (w*vPrev+ t1 + t2 + t3 + t4);
				double newVel = con* (vPrev+ t1 + t2 + t3 + t4);
			
				Velocities.set(i, newVel);
			}
		} 
		else {
			for (int i = 0; i < D; i++) { // updates velocities
				double vPrev = Velocities.get(i);
				double t1=(Math.random() * c1 * (bestPositions.get(i) - Positions.get(i)));
				double t2=(Math.random() * c2 * (localBestPositions.get(i) - Positions.get(i)));
				
				double t3=(Math.random() * c3 * (t1/(1 + Math.abs(Positions.get(i) - worstPositions.get(i)))));
				double t4=(Math.random() * c4 * (t2/(1 + Math.abs(Positions.get(i) - localWorstPositions.get(i)))));
				
				
//				double newVel = (w*vPrev+ t1 + t2 + t3 + t4);
				double newVel = con* (vPrev+ t1 + t2 + t3 + t4);
		
				Velocities.set(i, newVel);
			}
		}

		for (int i = 0; i < D; i++) { // ensures velocities aren't bigger than vmax
			if (Velocities.get(i) > vmax) {
				Velocities.set(i, vmax);
				Fast++;
			}

			else if (Velocities.get(i) < 0 - vmax) {
				Velocities.set(i, 0 - vmax);
				Fast++;
			}
		}

		for (int i = 0; i < D; i++) { // updates position
			if ((Positions.get(i) + Velocities.get(i) > UpperBoundaries.get(i)) && Boundary) { // checks within upper boundary
				double newPos = UpperBoundaries.get(i)- (Positions.get(i) + Velocities.get(i) - UpperBoundaries.get(i));
				Positions.set(i, newPos);
				double newVel = 0 - Velocities.get(i);
				Velocities.set(i, newVel);
			}

			else if ((Positions.get(i) + Velocities.get(i) < LowerBoundaries.get(i)) && Boundary) { // checks within lower boundary
				double newPos = LowerBoundaries.get(i)+ (LowerBoundaries.get(i) - (Positions.get(i) + Velocities.get(i)));
				Positions.set(i, newPos);
				double newVel = 0 - Velocities.get(i);
				Velocities.set(i, newVel);
			} 
			else {
				double newPos = Positions.get(i) + Velocities.get(i); // particle is within acceptable region											
				Positions.set(i, newPos);
			}
		}
	}


	public void FindMax(ArrayList<Double> FitnessVal, ArrayList<ArrayList<Double>> lPositions) {
		lBestFitness = bFitness;
		int lBestIndex = -10;
		for (int i = 0; i < FitnessVal.size(); i++) {
			if (FitnessVal.get(i) < lBestFitness) {
				lBestFitness = FitnessVal.get(i);
				lBestIndex = i;
			}
		}

		if (lBestIndex >= 0) {
			while(localBestPositions.size()>0){
				localBestPositions.remove(0);
			}
			lBestFitness = FitnessVal.get(lBestIndex);
			for (int i = 0; i < lPositions.get(lBestIndex).size(); i++) {
				double temp = lPositions.get(lBestIndex).get(i);
				localBestPositions.add(temp);
			}
		}
		else {
			lBestFitness = bFitness;
			for(int i=0;i<D;i++){
				localBestPositions.set(i,bestPositions.get(i));
			}
		}
		
	}
	
	
	public void FindMin(ArrayList<Double> FitnessVal, ArrayList<ArrayList<Double>> lPositions) {
		lWorstFitness = wFitness;
		int lWorstIndex = -10;
		for (int i = 0; i < FitnessVal.size(); i++) {
			if (FitnessVal.get(i) > lWorstFitness) {
				lWorstFitness = FitnessVal.get(i);
				lWorstIndex = i;
			}
		}

		if (lWorstIndex >= 0) {
			while(localWorstPositions.size()>0){
				localWorstPositions.remove(0);
			}
			lWorstFitness = FitnessVal.get(lWorstIndex);
			for (int i = 0; i < lPositions.get(lWorstIndex).size(); i++) {
				double temp = lPositions.get(lWorstIndex).get(i);
				localWorstPositions.add(temp);
			}
		}
		else {
			lWorstFitness = wFitness;
			for(int i=0;i<D;i++){
				localWorstPositions.set(i,worstPositions.get(i));
			}
		}
		
	//	System.out.println("The best fitness in this neighbourhood is "+lBestFitness);

	}
	
	public void resetVelocity(ArrayList<Double> gBestPositions,double i, double iMax){
		 int ct=200;
		 ArrayList <Double> newPos = new ArrayList <Double>();
		 if (!local){
			 newPos = localBestPositions;
		 }
		 else{
			 newPos = gBestPositions;
		 }
		 
		  if(i%ct==0){ 
			  for(int j=0;j<D;j++){ 
				  double newVel = (double) (Math.random() *( UpperBoundaries.get(j) - LowerBoundaries.get(j)) ) +LowerBoundaries.get(j); 
				  newVel = (newVel-newPos.get(j))/2;
				  newVel = newVel*(1-(i/iMax));
				  Velocities.set(j, newVel);
			  }
		  }
	}
	
	public void setI(double i, double Imax) {
		this.curI = i;
		this.Imax = Imax;
		
	}


	public void setH(double h){
		this.H=Math.floor(h);
		updateParticlesInNeighbourhood();
	}
	
	public void updateParticlesInNeighbourhood(){
		if(H>particlesInNeighbourhood.size()){
			for(int i=particlesInNeighbourhood.size();i<H;i++){
				double temp = Math.random()*(particlesNotInNeighbourhood.size());
				int t = (int) Math.floor(temp);
				int n = particlesNotInNeighbourhood.get(t);
				particlesInNeighbourhood.add(n);
				particlesNotInNeighbourhood.remove(t);
			}
		}
	}



}




















