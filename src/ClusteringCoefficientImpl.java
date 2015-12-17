import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

public class ClusteringCoefficientImpl {
	
	public double getClusteringCoefficient(CyNetwork network, CyNode node,
			boolean directed) {

		List<CyNode> neighborList;
		neighborList = network.getNeighborList(node, CyEdge.Type.ANY);
		double edgeCount = 0.0;
		Set<CyEdge> edgeSet = new HashSet<CyEdge>();
		for (CyNode neighbor1 : neighborList) {

			for (CyNode neighbor2 : neighborList) {
				if (neighbor1 == neighbor2)
					continue;
				if (directed) {
					for (CyEdge edge : network.getConnectingEdgeList(neighbor1,
							neighbor2, CyEdge.Type.ANY)) {
						edgeSet.add(edge);
					}
				} else {
					for (CyEdge edge : network.getConnectingEdgeList(neighbor1,
							neighbor2, CyEdge.Type.ANY)) {
						edgeCount++;
						break;
					}
				}
			}
		}
		if (directed)
			return ((double) edgeSet.size() / (neighborList.size() * (neighborList
					.size() - 1)));
		else
			return edgeCount
					/ (neighborList.size() * (neighborList.size() - 1));
	}
}