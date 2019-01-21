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


public class myTest {

	public static void main(String[] args) {
		
		int iterations = 10000;
		int dimensions=30;
		int function =1; 
		int particles=50;
		PSO a;
		PSO b;
		PSO c;
		ArrayList <Double> Origin = new ArrayList<>();
		for(int i=0; i<dimensions; i++){
			Origin.add(0.0);
		}
		
		// list of list of gbest values for vn topology
		ArrayList <ArrayList<Double>> vnGBest = new ArrayList<ArrayList<Double>>(); 
		// list of list of gbest values for ring topology
		ArrayList <ArrayList<Double>> ringGBest = new ArrayList<ArrayList<Double>>(); 
		// list of list of gbest values for global topology
		ArrayList <ArrayList<Double>> globalGBest = new ArrayList<ArrayList<Double>>(); 
		
		
//		for(int function=1;function<7;function++){
		System.out.println("\n\nFunction number = "+function);
			
		a = new vnPSO();
		a.initialize(2,iterations,particles,dimensions,function);
		System.out.println("AWL ____________");
		System.out.println("Time start");
		long startTime = System.nanoTime();
		double ans = a.run();
		long endTime = System.nanoTime();
		System.out.println("Time end");
		
		System.out.println("Time taken for "+iterations+" iterations = "+(endTime-startTime)/10E8);
		
		double check = a.Particles.get(0).fn.FitnessCheck(Origin);
		int fast = a.Particles.get(0).Fast;
		System.out.println("Max velocity exceded  : "+fast+" times");
		System.out.println("evaluation at origin = "+check);
		System.out.println("vn answer = "+ans);
		System.out.println("vn best position = "+a.gBestPositions.get(0)+", "+a.gBestPositions.get(1));
		System.out.println();
		for(int i=0; i<dimensions; i++){
	//		System.out.println("i = "+i+" :: vn best position = "+a.gBestPositions.get(i));
		}
		
			/*	
		b = new globalPSO();
		b.initialize(0,iterations,particles,dimensions,function);
		double ans = b.run();
	//	System.out.println("evaluation at origin = "+check);
		System.out.println("global answer = "+ans);
		System.out.println("global best position = "+b.gBestPositions.get(0)+", "+b.gBestPositions.get(1));
		for(int i =0; i<dimensions; i++){
	//		System.out.println("D = "+i+" - best position = "+b.gBestPositions.get(i));
		}
		
		System.out.println();
	
		c = new ringPSO();
		c.initialize(0,iterations,particles,dimensions,function);
		ans = c.run();
		System.out.println("evaluation at origin = "+check);
		System.out.println("ring answer = "+ans);
		System.out.println("ring best position = "+c.gBestPositions.get(0)+", "+c.gBestPositions.get(1));
*/
	
	}
//	}
}
