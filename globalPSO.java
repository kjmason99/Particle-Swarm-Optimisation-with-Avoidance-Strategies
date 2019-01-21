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
public  class globalPSO extends PSO{
	public String PSOtype = "global";
	public double run(){
		int i =0;
		gbestFitness= Double.POSITIVE_INFINITY ;
		gworstFitness= Double.NEGATIVE_INFINITY ;
		while(i<Imax){
			for(int n=0; n<NumParticles; n++){
				Particle cParticle = Particles.get(n); // current particle
			//	cParticle.setInertia((double)i, (double) Imax);
			//	cParticle.setI((double)i, (double) Imax);
				//cParticle.setC((double)i, (double) Imax);
				cParticle.evaulate();	
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
		}
		return gbestFitness;
	}
}

