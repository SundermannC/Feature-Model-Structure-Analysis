package analyses;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.prop4j.Node;

import analyses.IFMAnalysis.Format;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import util.CnfTranslator;
import util.FMUtils;

public class AnalysisHandler {
    
    private List<IFMAnalysis> analyses;

    public AnalysisHandler() {
        this.analyses = new ArrayList<>();
    }

    public void registerAnalysis(IFMAnalysis analysis) {
        analyses.add(analysis);
    }


    public String evaluateFmFile(File file, int timeout, String inputPath) {
        return getCleanName(file, inputPath) + ";" + evaluateFeatureModel(FMUtils.readFeatureModel(file.getPath()), timeout);
    }

    public String evaluateDimacsFile(File file, int timeout, String inputPath) {
        return getCleanName(file, inputPath) + ";" + evaluateCNF(CnfTranslator.readDimacs(file.getPath()), timeout);
    }


    private String getCleanName(File file, String inputPath) {
        String[] split = file.getAbsolutePath().split(inputPath);
        return split[split.length -1];
    }

    public String evaluateFeatureModel(IFeatureModel model, int timeout) {
        String csvRow = "";
        FeatureModelFormula formula = new FeatureModelFormula(model);
        for (IFMAnalysis analysis: analyses) {
            csvRow += analysis.getResult(model, formula) + ";";
        }
        return csvRow.substring(0, csvRow.length() - 1) + "\n";
    }

    public String evaluateCNF(Node node, int timeout) {
        String csvRow = "";
        for (IFMAnalysis analysis: analyses) {
            if (analysis.supportsFormat(Format.CNF)) {
                csvRow += analysis.getResult(node) + ";";
            }
        }
        return csvRow.substring(0, csvRow.length() - 1) + "\n";
    }

    public String getCsvHeader() {
        String headerRow = "model;";
        for (IFMAnalysis analysis : analyses) {
            headerRow += analysis.getLabel() + ";";
        }

        return headerRow.substring(0, headerRow.length() - 1) + "\n";
    }

}
