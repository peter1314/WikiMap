import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * The NodeMap class represents a map or graph of nodes
 * The class cannot be instantiated and relies on static methods
 */
public class NodeMap {

    private static ArrayList<Node> nodes = new ArrayList<>();
    private static int maxGeneration = 0;

    /**
     * Adds a node to the NodeMap, given an article
     * @param article Article from which the node will be created
     */
    public static void addNode(Article article) {
        Node n = new Node(article);
        if(!nodes.contains(n)) {
            maxGeneration = Math.max(maxGeneration, article.getGeneration());
            nodes.add(n);
        }
    }

    /**
     * Arranges the nodes in the map with even spacing
     */
    public static void arrangeNodes() {
        for(int i = 0; i <= maxGeneration; i++) {
            int count = 0;
            for(Node n: nodes) {
                if(n.getArticle().getGeneration() == i) count++;
            }
            double p = -1 * ((count-1) / 2.0);
            for(Node n: nodes) {
                if(n.getArticle().getGeneration() == i) {
                    n.setY(50 * (i+1));
                    n.setX(375 + (100*p++));
                }
            }
        }
    }

    /**
     * @return All the nodes in the map
     */
    public static ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Static version of teh toString() method
     * @return A String representing the NodeMap
     */
    public static String myToString() {
        String output = "";
        for(Node n: nodes) {
            output += n.toString() + "\n";
        }
        return output;
    }

    /**
     * Helpful method for rounding numbers to a specified number of decimal places
     * @param input Number to round
     * @param places Places to round it to
     * @return Rounded number
     */
    public static double roundTo(double input, int places) {
        return Math.round(input * Math.pow(10, places)) / Math.pow(10, places);
    }
}
