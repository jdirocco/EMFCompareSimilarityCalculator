package it.disim.univaq.mm_clustering.similariti_calculator;

import static java.nio.file.StandardOpenOption.APPEND;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.SliderUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger log = LogManager.getFormatterLogger(App.class);
    public static void main( String[] args )
    {
        try {
			new App().run(args);
		} catch (Exception e) {
			log.error("The path parameter is missing");
		}
    }

	private void run(String[] args) throws Exception {
		if (args.length != 2)
			throw new Exception("");
		String path = args[0];
		String out = args[1];
		
		List<Path> paths1 = Files.list(Paths.get(path)).filter(Files::isRegularFile).collect(Collectors.toList());
		List<Path> paths2 = Files.list(Paths.get(path)).filter(Files::isRegularFile).collect(Collectors.toList());
		LocalDateTime start = LocalDateTime.now();
		for (Path path1 : paths1) {
			for (Path path2 : paths2) {

				try {
					double v = SimilarityCalculator.calculateSimilarity(path2.toString(),path1.toString());
					String lines = String.format("%s,%s,%f\n", path1,path2,v);
					Files.write(Paths.get(out), lines.getBytes(), APPEND);
				} catch (IOException e) {
					log.error("Problem occurs when deleting the directory : " + out);
					e.printStackTrace();
				}
						
				
			}
		}
		LocalDateTime end = LocalDateTime.now();
		Duration duration = Duration.between(start, end);
        System.out.println(duration.getSeconds() + " seconds");
	}
}
