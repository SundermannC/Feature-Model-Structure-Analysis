package analyses;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import formulagraph.ConnectivityGraph;
import org.prop4j.Node;

public class IndependentCyclomaticComplexity implements IFMAnalysis {

    private static final String LABEL = "IndependentCyclomaticComplexity";

    @Override
    public String getLabel() {
        return LABEL;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getResult(IFeatureModel featureModel) {
        ConnectivityGraph graph = new ConnectivityGraph(featureModel);
        return Integer.toString(graph.getNumberOfIndependentCycles());
    }

    @Override
    public String getResult(Node node) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean supportsFormat(Format format) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
