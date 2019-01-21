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



public class Function {
	public int D;
	public int f;
	ArrayList<Double> UpperBoundaries = new ArrayList<Double>(); // boundaries
	ArrayList<Double> LowerBoundaries = new ArrayList<Double>();
	double sum1 = 0;
	double sum2 = 0;
	double sum3 = 0;
	double x;
	double x1;
	double z;
	double z1;
	String fnName;
	ArrayList <Double> sum  = new ArrayList <Double>();
	ArrayList <Double> fbias  = new ArrayList <Double>();
	ArrayList <Double> bias  = new ArrayList <Double>();
	
	

	double PI = Math.PI;
	double Fitness = 0;
	
	public Function(int f,int D, ArrayList<Double> UpperBoundaries, ArrayList<Double> LowerBoundaries){
		setName(f); 
		this.D = D;
		this.f = f;
		this.UpperBoundaries = UpperBoundaries;
		this.LowerBoundaries = LowerBoundaries;
		setBias();
		
	}

	public double FitnessCheck(ArrayList <Double> Positions){ 
		Fitness = 0;
		double sum1 = 0;
		double sum2 = 0;
		
		ArrayList <Double> zArray  = new ArrayList <Double>();
		if(f==1){ // spherical function
			Fitness = sphere(Positions,D) + fbias.get(f);
			this.fnName = "Sphere";
		}
		
		else if(f==2){ // Rosenbrock function
			Fitness = rosenbrock(Positions,0,D) + fbias.get(f);
			this.fnName = "Rosenbrock";
		}
		
		else if(f==3){ // Ackley function
			Fitness = ackley(Positions,D)+ fbias.get(f);
			this.fnName = "Ackley";
		}
		
		else if(f==4){ // Griewank function
			Fitness = griewank(Positions,D)+ fbias.get(f);
			this.fnName = "Griewank";
		}
		
		else if(f==5){ // Rastrigin function
			Fitness = rastrigin(Positions,D)+ fbias.get(f);
			this.fnName = "Rastrigin";
		}
		
		else if(f==6){ // Schaffer (2D) function
			Fitness = schaffer(Positions.get(0), Positions.get(1)) + fbias.get(f);
			this.fnName = "Schaffer2D";
		}
		
		else if(f==7){ // Griewank 10D function 
			Fitness=griewank(Positions,10) + fbias.get(f);
			this.fnName = "Griewank10D";
		}
		
				
		else{
			Fitness = 0;
			System.out.println("Invalid function");
		}
		return Fitness;
	}

	
	
	
	
	
	
	
	

	
	
	
	
	public double sphere(ArrayList <Double> z, int D){
		double sum1 = 0.0;
		for(int i = 0; i<D; i++){
			x = z.get(i);
			sum1 = sum1 + ((x)*(x));
		}
		return sum1;
	}
	
	public double ackley(ArrayList <Double> z, int D){
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum3 = 0.0;
		for(int i=0;i<D;i++){ // 30 dimensions
			x = z.get(i);
			sum1 = sum1 + Math.pow(x, 2); 
			sum2 = sum2 + Math.cos(2*PI*x);
		}
		sum3=-(20*Math.exp(-0.2*Math.sqrt(sum1/D))) - (Math.exp(sum2/D)) + 20 + (Math.E);
		return sum3;
	}
	
	public double griewank(ArrayList <Double> z, int D){
		double sum1 = 0.0;
		double sum2=1.0;
		for(int i=0;i<D;i++){ // 30 dimensions
			x = z.get(i);
			sum1 = sum1 + (x*x); 
			sum2 = sum2 * Math.cos(x/Math.sqrt(i+1)); 
		}
		sum3=1+(sum1/4000)-(sum2);
		return sum3;
	}
	
	public double rastrigin(ArrayList <Double> z, int D){
		double sum3=0;
		for(int i=0;i<D;i++){ // 30 dimensions
			x = z.get(i);
			sum3 = sum3 + (x*x) - 10*Math.cos(2*PI*x) +10;
		}
		return sum3;
	}
	
	public double rosenbrock(ArrayList <Double> z,int s, int D){
		double sum1 =0;
		double sum2 = 0;
		double sum3 =0;
		for(int i=s;i<D-1;i++){ // 30 dimensions
			x = z.get(i);
			x1 = z.get(i+1);
			sum1 = Math.pow(    ((x*x)-(x1)), 2);
			sum2 = Math.pow((x-1), 2);
			sum3 = sum3 + (100*sum1) +sum2;
		}
		return sum3;
	}
	
	public double schaffer(double x, double y){
		double sum1 =0;
		double sum2 = 0;
		double sum3 =0;
		sum1 = Math.pow(  Math.sin(Math.sqrt((x*x)+(y*y))), 2)  - 0.5;
		sum2 = Math.pow((1+(0.0001*((x*x)+(y*y)))), 2);
		sum3 = 0.5 + (sum1/sum2);
		return sum3;
	}
	

	
	
	
	public void setBias(){
		for(int i = 0;i<8;i++){
			fbias.add(0.0);
		}
		for (int i = 0;i<4;i++){
			fbias.add(-450.0);
		}
		fbias.add(-310.0);
		fbias.add(390.0);
		fbias.add(-180.0);
		fbias.add(-140.0);
		fbias.add(-330.0);
		fbias.add(-330.0);
		fbias.add(90.0);
		fbias.add(-460.0);
		fbias.add(-130.0);
		fbias.add(-300.0);
		fbias.add(120.0);
		fbias.add(120.0);
		fbias.add(120.0);
		fbias.add(10.0);
		fbias.add(10.0);
		fbias.add(10.0);
		fbias.add(360.0);
		fbias.add(360.0);
		fbias.add(360.0);
		fbias.add(260.0);
		fbias.add(260.0);
	}
	
	public double rosenbrock2D(double x, double y) {
		double temp1 = (x * x) - y;
		double temp2 = x - 1.0;
		return ((100.0 * temp1 * temp1) + (temp2 * temp2));
	}
	
	public double griewank1D(double x) {
		return (((x * x) / 4000.0) - Math.cos(x) + 1.0);
	}
	
	
	
	public void setName(int f){
		if(f==1){ // spherical function
			this.fnName = "Sphere";
		}
		
		else if(f==2){ // Rosenbrock function
			this.fnName = "Rosenbrock";
		}
		
		else if(f==3){ // Ackley function
			this.fnName = "Ackley";
		}
		
		else if(f==4){ // Griewank function
			this.fnName = "Griewank";
		}
		
		else if(f==5){ // Rastrigin function
			this.fnName = "Rastrigin";
		}
		
		else if(f==6){ // Schaffer (2D) function
			this.fnName = "Schaffer2D";
		}
		
		else if(f==7){ // Griewank 10D function 
			this.fnName = "Griewank10D";
		}
		else{
			
		}
		
	}
}













