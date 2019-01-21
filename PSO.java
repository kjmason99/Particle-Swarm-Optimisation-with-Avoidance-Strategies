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


public abstract class PSO {
	public double gbestFitness;
	public double gworstFitness;
	public int NumParticles; 
	public int Imax; 
	ArrayList <Double> gBestPositions = new ArrayList<Double>();
	ArrayList <Double> gWorstPositions = new ArrayList<Double>();
	ArrayList <Particle> Particles = new ArrayList<Particle>();
	
	ArrayList <Double> gBestValues = new ArrayList<Double>();
	public int bestParticleI;
	public int worstParticleI;
	public int D;
	private ArrayList<Double> UBoundaries = new ArrayList<Double>();
	private ArrayList<Double> LBoundaries = new ArrayList<Double>();
	Function fn;
	public boolean Boundary = true;
	public double H;
	public double b;
	
	public void initialize(int p,int I,int P,int D,int F){
		Imax = I;
		this.b=3.0;
		this.H=b;
		int n =0;
		this.D=D;
		NumParticles = P;
		setBoundaries(F);
		Function fn = new Function(F,D,UBoundaries,LBoundaries);
		this.fn =fn;
		while (n<NumParticles){
			Particles.add(new Particle(p,D,fn,UBoundaries,LBoundaries,Boundary));
			n++;
		}
	}
	
	public void setBoundaries(int problem) {
		double limit = 0;
		double uLimit=0;
		double lLimit=0;
		if (problem == 1) { // spherical function
			limit = 5.12;
		}
		if (problem == 2) {
			limit = 2.048;
		}

		if (problem == 3) {
			limit = 32;
		}

		if (problem == 4) {
			limit = 600;
		}

		if (problem == 5) {
			limit = 5.12;
		}

		if (problem == 6 ||problem == 21) {
			limit = 100;
		}

		if (problem == 7) {
			limit = 600;
		}
		
		
		if (limit!=0) {
			for (int i = 0; i < D; i++) {
				UBoundaries.add(limit);
				LBoundaries.add(0 - limit);
			}
		}
		else{
			for (int i = 0; i < D; i++) {
				UBoundaries.add(uLimit);
				LBoundaries.add(lLimit);
			}
		}
	}
	
	public abstract double run();
	
}






