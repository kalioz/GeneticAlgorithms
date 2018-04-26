package fr.kalioz.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;

public class Laboratory<T extends Individual> {
	
	private Individual[] population;
	private int generation;
	
	private final float minMutation;
	private static final float MAXMUTATION = 100;
	
	private int stagnation;
	private final int maxStagnation;
	
	private List<Float> bestFitnessHistory;
	private List<Float> meanFitnessHistory;
	private List<Float> mutationHistory;
	
	// constructor
	
	public Laboratory(Class<T> type, int populationSize, float minMutation, int maxStagnation) throws Exception {
		
		population = new Individual[populationSize];
		for (int i = 0; i < population.length; i++) {
			population[i] = type.getConstructor().newInstance();
			population[i].init();
			if (! population[i].isCorrectlyFormed()){
				throw new Exception("Erreur - an individual has been wrongly formed");
			}
		}

		this.generation = 0;

		this.minMutation = minMutation;

		this.maxStagnation = maxStagnation;
		
		bestFitnessHistory = new ArrayList<>();
		meanFitnessHistory = new ArrayList<>();
		mutationHistory = new ArrayList<>();
	}
	
	// accessors

	public int getGeneration(){
		return this.generation;
	}
	
	public Float getCurrentMutation() {
		return Math.min(minMutation + ((MAXMUTATION-minMutation)*(float)stagnation)/(float)maxStagnation,
				MAXMUTATION);
	}
	
	public Individual[] getPopulation() {
		return population;
	}

	public void setPopulation(Individual[] population) {
		this.population = population;
	}

	public float getMinMutation() {
		return minMutation;
	}

	public List<Float> getBestFitnessHistory() {
		return bestFitnessHistory;
	}

	public List<Float> getMeanFitnessHistory() {
		return meanFitnessHistory;
	}

	public List<Float> getMutationHistory() {
		return mutationHistory;
	}
	
	// functions
	public void nextGeneration(){
		float mutation = getCurrentMutation();
		
		this.population = Laboratory.nextGeneration(population, mutation);
		generation++;
		
		bestFitnessHistory.add(this.getBestFitness());
		meanFitnessHistory.add(this.getMeanFitness());
		mutationHistory.add(mutation);
		
		if (isStagnating()){
			stagnation++;
		}else{
			stagnation=0;
		}
		
	}
	
	public void run(int n){
		for (int i=0; i<n; i++){
			this.nextGeneration();
		}
	}
	
	public void run(){
		while (! shouldStop()){
			this.nextGeneration();
		}
	}
	
	public boolean shouldStop(){
		return this.stagnation >= maxStagnation;
	}

	/**
	 * determinate if the results are stagnating
	 * @return
	 */
	public boolean isStagnating(){
		if (bestFitnessHistory.size()<2){
			return false;
		}
		int size = bestFitnessHistory.size();
		return bestFitnessHistory.get(size-1).floatValue()== bestFitnessHistory.get(size-2).floatValue();
	}
	
	@SuppressWarnings("unchecked")
	public T getBest(){
		Individual best = Laboratory.getBest(population);
		if (best==null){
			return null;
		}
		return (T) best;
	}
	
	public float getBestFitness(){
		return Laboratory.getBestFitness(population);
	}
	
	public float getMeanFitness(){		
		return Laboratory.getMeanFitness(population);
	}
	
	// class functions
	
	public static Individual getBest(Individual[] population){
		Individual best = null;
		for (Individual individu:population){
			if (best == null || (individu !=null && individu.isCorrectlyFormed() && individu.isFittestThan(best))) {
				best = individu;
			}
		}
		return best;
	}
	
	public static float getBestFitness(Individual[] population){
		Individual best = Laboratory.getBest(population);
		if (best == null){
			return -1;
		}
		return best.fitness();
	}
	
	public static float getMeanFitness(Individual[] population){		
		float mean = 0;
		int number = 0;
		for (Individual i:population){
			if (i != null && i.isCorrectlyFormed()){
				number++;
				mean+=i.fitness();
			}
		}
		return (number !=0) ? mean / number :0;
	}
	
	
	/**
	 * select half of the population using a tournament type selection
	 * @return
	 */
	public static Individual[] selectionTurnament(Individual[] population){
		Individual[] output = new Individual[population.length/2];
		Individual[] clone = population.clone();
		
		Utils.shuffle(clone);
		
		for (int i=0; i<output.length; i++){
			output[i] = clone[2*i].getFittest(clone[2*i+1]);
		}		
		
		return output;
	}
	
	/**
	 * select half of the population by their best score
	 * @param population
	 * @return
	 */
	public static Individual[] selectionBetterHalf(Individual[] population){
		Individual[] output = new Individual[population.length/2];
		Individual[] clone = population.clone();
		
		for (int i=0; i<output.length; i++){
			Individual best = Laboratory.getBest(clone);
			output[i] = best;
			for (int j =0; j<clone.length; j++){
				if (clone[j] == best){
					clone[j] = null;
				}
			}
		}
		return output;
	}
	
	/**
	 * creates children from the parents
	 * @param population
	 * @return an array of the same size of the parents
	 */
	public static Individual[] crossover(Individual[] parents){
		Individual[] children = new Individual[parents.length];
		Individual[] parentsClone = parents.clone();
		
		Utils.shuffle(parentsClone);
		
		for (int i=0; i < parentsClone.length-1; i+=2){
			Individual[] children2 = parentsClone[i].createChildren(parentsClone[i+1]);
			children[i] = children2[0];
			children[i+1] = children2[1];
		}
		
		return children;
	}
	
	public static void mutate(Individual[] population, float mutation){
		for (Individual individu: population){
			if (Utils.randomMutate(mutation))
				individu.mutate();
		}
	}
	
	public static Individual[] nextGeneration(Individual[] population, float mutation){
		
		//selection
		Individual[] parents = selectionTurnament(population);
		//crossover
		Individual[] children = crossover(parents);
		//mutation
		mutate(children, mutation);
		
		Individual[] output = new Individual[population.length];
		for (int i=0; i<parents.length; i++){
			output[i]=parents[i];
			output[parents.length+i]=children[i];
		}
		
		return output;		
	}
}