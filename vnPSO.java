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


public  class vnPSO extends PSO{
	public String PSOtype = "vn";
	public int l = 5; // l is the length of the row in vn lattice ********(use 5?)
	// arrayList of best positions within particle n's neighbourhood 
	ArrayList <ArrayList<Double>> lBestPositions = new ArrayList<ArrayList<Double>>(); 
	ArrayList <ArrayList<Double>> lWorstPositions = new ArrayList<ArrayList<Double>>(); 
	// arrayList of best fitness values within particle n's neighbourhood 
	ArrayList <Double> lBestFitness = new ArrayList<Double>(); 
	ArrayList <Double> lWorstFitness = new ArrayList<Double>(); 

	public double run(){
		int i =0;
		gbestFitness= Double.POSITIVE_INFINITY ;
		gworstFitness= Double.NEGATIVE_INFINITY ;
		while(i<Imax){
		//	System.out.println("Iteration : "+i);
			for(int n=0; n<NumParticles; n++){
		//		System.out.println("Particle : "+n);
				Particle cParticle = Particles.get(n); // current particle
		//		cParticle.setInertia((double)i, (double) Imax);
			//	cParticle.setC((double)i, (double) Imax);
			//	cParticle.setI((double)i, (double) Imax);
				cParticle.evaulate(lBestPositions,lBestFitness);	
				updateNeighbourhood(n, l);
				cParticle.FindMax(lBestFitness, lBestPositions);
				cParticle.FindMin(lWorstFitness, lWorstPositions);
				if(cParticle.bFitness<gbestFitness){
					gbestFitness = cParticle.bFitness;
					bestParticleI=n;
					while(gBestPositions.size()>0){
						gBestPositions.remove(0);
					}
					for(int j=0;j<D;j++){
						double temp = cParticle.bestPositions.get(j);
						this.gBestPositions.add(temp);
					}
				}	
				if(cParticle.wFitness>gworstFitness){
					gworstFitness = cParticle.wFitness;
					worstParticleI=n;
					
					while(gWorstPositions.size()>0){
						gWorstPositions.remove(0);
					}
					for(int j=0;j<D;j++){
						double temp = cParticle.worstPositions.get(j);
						this.gWorstPositions.add(temp);
					}
				}
				cParticle.update(gBestPositions,gWorstPositions);
			}
			gBestValues.add(gbestFitness);
			i++;
		//	System.out.println(gbestFitness);
		}
		return gbestFitness;
		
	}
	
	public void updateNeighbourhood(int n, int l){
		boolean top = false;
		boolean bottom = false;
		boolean left = false;
		boolean right = false;
		while(lBestFitness.size()>0){
			lBestFitness.remove(0);
		}
		
		while(lBestPositions.size()>0){
			lBestPositions.remove(0);
		}
		while(lWorstFitness.size()>0){
			lWorstFitness.remove(0);
		}
		
		while(lWorstPositions.size()>0){
			lWorstPositions.remove(0);
		}
		/*
		for(int i=0;i<NumParticles; i++){
			lBestFitness.add(Particles.get(i).bFitness);
			lBestPositions.add(Particles.get(i).bestPositions);
		}
		*/
		
		if(n<l){
			top = true;
		}
		
		if(n>(NumParticles-l-1)){
			bottom = true;
		}
		
		if((n+l)%l==0){
			left = true;
		}
		if((n+1)%l==0){
			right = true;
		}
		
		if(top){
			lBestFitness.add(Particles.get(n+NumParticles-l).bFitness);
			lBestPositions.add(Particles.get(n+NumParticles-l).bestPositions);
			lWorstFitness.add(Particles.get(n+NumParticles-l).wFitness);
			lWorstPositions.add(Particles.get(n+NumParticles-l).worstPositions);
			
	//		System.out.println("Neighbour 1 is "+(n+NumParticles-l));
		}
		else{
			lBestFitness.add(Particles.get(n-l).bFitness);
			lBestPositions.add(Particles.get(n-l).bestPositions);
			lWorstFitness.add(Particles.get(n-l).wFitness);
			lWorstPositions.add(Particles.get(n-l).worstPositions);
			
	//		System.out.println("Neighbour 1 is "+(n-l));
		}
		
		if(bottom){
			lBestFitness.add(Particles.get(n-NumParticles+l).bFitness);
			lBestPositions.add(Particles.get(n-NumParticles+l).bestPositions);
			lWorstFitness.add(Particles.get(n-NumParticles+l).wFitness);
			lWorstPositions.add(Particles.get(n-NumParticles+l).worstPositions);
			
	//		System.out.println("Neighbour 2 is "+(n-NumParticles+l));
		}
		else{
			lBestFitness.add(Particles.get(n+l).bFitness);
			lBestPositions.add(Particles.get(n+l).bestPositions);
			lWorstFitness.add(Particles.get(n+l).wFitness);
			lWorstPositions.add(Particles.get(n+l).worstPositions);
			
//			System.out.println("Neighbour 2 is "+(n+l));
		}
		
		if(left){
			lBestFitness.add(Particles.get(n+l-1).bFitness);
			lBestPositions.add(Particles.get(n+l-1).bestPositions);
			lWorstFitness.add(Particles.get(n+l-1).wFitness);
			lWorstPositions.add(Particles.get(n+l-1).worstPositions);
			
	//		System.out.println("Neighbour 3 is "+(n+l-1));
		}
		else{
			lBestFitness.add(Particles.get(n-1).bFitness);
			lBestPositions.add(Particles.get(n-1).bestPositions);
			lWorstFitness.add(Particles.get(n-1).wFitness);
			lWorstPositions.add(Particles.get(n-1).worstPositions);
			
	//		System.out.println("Neighbour 3 is "+(n-1));
		}
		
		if(right){
			lBestFitness.add(Particles.get(n-l+1).bFitness);
			lBestPositions.add(Particles.get(n-l+1).bestPositions);
			lWorstFitness.add(Particles.get(n-l+1).wFitness);
			lWorstPositions.add(Particles.get(n-l+1).worstPositions);
			
	//		System.out.println("Neighbour 4 is "+(n-l+1));
		}
		else{
			lBestFitness.add(Particles.get(n+1).bFitness);
			lBestPositions.add(Particles.get(n+1).bestPositions);
			lWorstFitness.add(Particles.get(n+1).wFitness);
			lWorstPositions.add(Particles.get(n+1).worstPositions);
			
	//		System.out.println("Neighbour 4 is "+(n+1));
		}
		
	}
	
}