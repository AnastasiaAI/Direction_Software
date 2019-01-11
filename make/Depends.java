package make;

import graph.LabeledGraph;
import graph.Graph;

/** A directed, labeled subtype of Graph that describes dependencies between
 *  targets in a Makefile. The nodes correspond to Rules and edges out
 *  of rules are numbered to indicate the ordering of dependencies.
 *  @author A.
 */
class Depends extends LabeledGraph<Rule, Integer> {

    /** An empty dependency @param g graph. */
    Depends(Graph g) {
        super(g);
    }

}
