import java.io.File;
import java.util.List;

import analyses.*;
import util.FMUtils;
import util.FileUtils;

public class FeatureModelStructureAnalysis {

	AnalysisHandler analysisHandler;
	int timeout;

	public FeatureModelStructureAnalysis() {
		initializeAnalyses();
		timeout = 30;
	}

	public static void main(String[] args) {
		FeatureModelStructureAnalysis analysis = new FeatureModelStructureAnalysis();

		FMUtils.installLibraries();
		if (args.length < 1) {
			System.out.println("Mandatory argument([0]): Input path\n" + "Optional Argument([1]): Output path");
			return;
		}

		List<File> files = FileUtils.getFileListWithExtension(args[0], "xml");

		analysis.handleFiles(files, args[0], args.length == 1 ? "result.csv" : args[1]);
	}

	private void handleFiles(List<File> files, String inputPath, String outputfile) {
		String csvContent = analysisHandler.getCsvHeader();
		for (File file : files) {
			csvContent += handleFile(file, inputPath);
		}
		FileUtils.writeContentToFile(outputfile, csvContent);

	}

	private String handleFile(File file, String inputPath) {
		System.out.println("Handling " + file.getName());
		return analysisHandler.evaluateFmFile(file, timeout, inputPath);
	}

	private void initializeAnalyses() {
		analysisHandler = new AnalysisHandler();
		// analysisHandler.registerAnalysis(new NumberOfFeatures());
		// analysisHandler.registerAnalysis(new NumberOfLeafFeatures());
		// analysisHandler.registerAnalysis(new NumberOfTopFeatures());

		// analysisHandler.registerAnalysis(new NumberOfConstraints());
		// analysisHandler.registerAnalysis(new AverageConstraintSize());
		// analysisHandler.registerAnalysis(new CtcDensity());
		// analysisHandler.registerAnalysis(new FeaturesInConstraintsDensity());
		analysisHandler.registerAnalysis(new RatioOfOptionalFeatures());

		// analysisHandler.registerAnalysis(new TreeDepth());
		// analysisHandler.registerAnalysis(new AverageNumberOfChilden());

		// analysisHandler.registerAnalysis(new NumberOfClauses());
		analysisHandler.registerAnalysis(new NumberOfLiterals());
		// analysisHandler.registerAnalysis(new ClauseDensity());

		// analysisHandler.registerAnalysis(new NumberOfValidConfigurations());
		analysisHandler.registerAnalysis(new ConnectivityDensity());

		analysisHandler.registerAnalysis(new SimpleCyclomaticComplexity());
		analysisHandler.registerAnalysis(new IndependentCyclomaticComplexity());
	}

}
