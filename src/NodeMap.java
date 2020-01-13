import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class NodeMap {

    private static ArrayList<Node> nodes = new ArrayList<>();
    private static int maxGeneration = 0;

    public static void addNode(Article article) throws Exception {
        Node n = new Node(article);
        if(!nodes.contains(n)) {
            maxGeneration = Math.max(maxGeneration, article.getGeneration());
            nodes.add(n);
        }
    }

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

    public static ArrayList<Node> getNodes() {
        return nodes;
    }

    public static String myToString() {
        String output = "";
        for(Node n: nodes) {
            output += n.toString() + "\n";
        }
        return output;
    }

    public static Point2D.Double[] circleIntersections(Point2D.Double c1, Point2D.Double c2, int r1, int r2) {
        double d = c1.distance(c2);
        double d1 = (r1*r1 - r2*r2 + d*d) / (2*d);
        double h = Math.sqrt(r1*r1 - d1*d1);
        double p3x = c1.x + (d1 * (c2.x - c1.x)) / d;
        double p3y = c1.y + (d1 * (c2.y - c1.y)) / d;
        double i1x = roundTo(p3x - (h * (c2.y - c1.y)) / d, 3);
        double i1y = roundTo(p3y + (h * (c2.x - c1.x)) / d, 3);
        double i2x = roundTo(p3x + (h * (c2.y - c1.y)) / d, 3);
        double i2y = roundTo(p3y - (h * (c2.x - c1.x)) / d, 3);


        Point2D.Double[] possibleIntersections = {new Point2D.Double(i1x,i1y), new Point2D.Double(i2x,i2y)};
        return possibleIntersections;
    }

    public static double roundTo(double input, int places) {
        return Math.round(input * Math.pow(10, places)) / Math.pow(10, places);
    }
}
