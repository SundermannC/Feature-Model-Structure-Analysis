package analyses;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
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


    private String getCleanName(File file, String inputPath) {
        String[] split = file.getAbsolutePath().split(inputPath);
        return split[split.length -1];
    }

    public String evaluateFeatureModel(IFeatureModel model, int timeout) {
        String csvRow = "";
        for (IFMAnalysis analysis: analyses) {
            csvRow += analysis.getResult(model) + ";";
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
