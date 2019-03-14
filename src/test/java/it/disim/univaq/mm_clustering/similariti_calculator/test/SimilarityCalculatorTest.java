package it.disim.univaq.mm_clustering.similariti_calculator.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.disim.univaq.mm_clustering.similariti_calculator.SimilarityCalculator;

public class SimilarityCalculatorTest {
	@Test
	public void calculateSimilarity() {
		double sim = SimilarityCalculator.calculateSimilarity("/home/juri/git/MDEForge/mdeforge/githubmetamodels/Projet.ecore", 
				"/home/juri/git/MDEForge/mdeforge/githubmetamodels/Projet.ecore");
		assertEquals(sim,1, 0.0);
	}
}
