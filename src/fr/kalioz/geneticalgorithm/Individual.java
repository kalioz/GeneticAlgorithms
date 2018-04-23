package fr.kalioz.geneticalgorithm;

public abstract class Individual {
	
	
	/**
	 * initialise the individual for tests - this method should be used only for the 1st generation to create subjects from thin air.
	 * The class constructor should implement all other initialisations.
	 */
	public abstract void init();
	
	/**
	 * @return the fitness score of this individual.
	 */
	public abstract float fitness();
	
	public abstract void mutate();
	
	public abstract Individual[] createChildren(Individual other);
	
	public abstract boolean isCorrectlyFormed();
	
	/**
	 *  verify if the individual is fittest than the other by comparing their fitness score.
	 *  the individual with the greatest fitness is the fittest
	 * @param other
	 * @return boolean
	 */
	public abstract boolean isFittestThan(Individual other);
	
	public Individual getFittest(Individual other){
		return (this.isFittestThan(other))?this:other;
	}
	
}
