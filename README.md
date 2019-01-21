# Particle-Swarm-Optimisation-with-Avoidance-Strategies
This is a Java implementation of the Particle Swarm Optimisation with Avoidance of Worst Locations (PSO AWL) Algorithm. The algorithm is applied to 7 benchmark functions and has 4 implmented topologies. 

The class myTest.java creates an instance of the PSO AWL algorithm. There are 4 subclasses of the PSO abstract class relating to a global, ring, von Neumann and GIDN topology. Each PSO variant creates a number of instances of the particle.java class which have positions, velocities and methods to update this information. The function class contains the objective functions that will evaluate candidate solutions. I have included 7 standard functions here but more can be added as desired.

Please cite the following papers when publishing work relating this code :

@article{mason2017multi,   
title={Multi-objective dynamic economic emission dispatch using particle swarm optimisation variants},   
author={Mason, Karl and Duggan, Jim and Howley, Enda},   
journal={Neurocomputing},   
volume={270},   
pages={188--197},   
year={2017},   
publisher={Elsevier} }   

@article{mason2016exploring,   
title={Exploring avoidance strategies and neighbourhood topologies in particle swarm optimisation},   
author={Mason, Karl and Howley, Enda},   
journal={International Journal of Swarm Intelligence},   
volume={2},   
number={2-4},   
pages={188--207},   
year={2016},   
publisher={Inderscience Publishers (IEL)} } 

@incollection{mason2015avoidance,   
title={Avoidance strategies in particle swarm optimisation},   
author={Mason, Karl and Howley, Enda},   
booktitle={Mendel 2015},  
pages={3--15},   
year={2015},   
publisher={Springer} }  

@article{mason2018meta,   
title={A meta optimisation analysis of particle swarm optimisation velocity update equations for watershed management learning},   
author={Mason, Karl and Duggan, Jim and Howley, Enda},   
journal={Applied Soft Computing},   
volume={62},  
pages={148--161},   
year={2018},   
publisher={Elsevier} }

@article{mason2015avoidance,
  title={Avoidance techniques \& neighbourhood topologies in particle swarm optimisation},
  author={Mason, Karl and Howley, Enda},
  journal={Master's thesis, National University of Ireland Galway},
  year={2015}
}
