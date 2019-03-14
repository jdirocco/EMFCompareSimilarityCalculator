package it.disim.univaq.mm_clustering.similariti_calculator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.google.common.collect.Lists;

public class SimilarityCalculator {
	private static final Logger log = LogManager.getFormatterLogger(SimilarityCalculator.class);
	public static double calculateSimilarity(String art1, String art2) {
		log.info(String.format("Compute the similarity between %s %s",art1,art2));
		URI uri1 = URI.createFileURI(art1);
		URI uri2 = URI.createFileURI(art2);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		ResourceSet resourceSet1 = new ResourceSetImpl();
		ResourceSet resourceSet2 = new ResourceSetImpl();
		resourceSet1.getResource(uri1, true);
		resourceSet2.getResource(uri2, true);
		IComparisonScope scope = new DefaultComparisonScope(resourceSet1, resourceSet2, null);
		int total = 0;
		try {
			Comparison comparisonDef = EMFCompare.builder().build().compare(scope);
			List<Match> matchesDef = comparisonDef.getMatches();
			int counterDef = 0;
			
			for (Match match : matchesDef) {
				List<Match> lm = Lists.newArrayList(match.getAllSubmatches());
				total += lm.size();
				for (Match match2 : lm)
					if (match2.getLeft() != null && match2.getRight() != null)
						counterDef++;
				if (match.getLeft() != null && match.getRight() != null)
					counterDef++;
			}
			double val = ((counterDef * 1.0) / total);
			val = val>1?1:val;
			log.info(String.format("Compute the similarity between %s %s with value %f",art1,art2, val));
			return val;
		} catch (Exception e) {
			return 0;
		}
	}
}
