import javax.swing.*;
import java.awt.geom.Point2D;

/**
 * The Node class is used to represent the graphical interpretation of an article
 */
public class Node {

    //The article the node represents
    private Article article;
    //The coordinates of the node
    private Point2D.Double point;
    //The label of the node
    private JLabel label;
    //If this node is a target node
    private boolean isTarget = false;

    /**
     * Declares a node given an article
     * @param article Article which this node will represent
     */
    public Node(Article article) {
        this.article = article;
        point = new Point2D.Double(0,0);
    }

    /**
     * Sets the x coordinate of this node
     * @param x
     */
    public void setX(double x) {point.x = x;}

    /**
     * Sets the y coordinate of this node
     * @param y
     */
    public void setY(double y) {point.y = y;}

    /**
     * Sets of this node is a target node
     * @param isTarget
     */
    public void setIsTarget(boolean isTarget) {this.isTarget = isTarget;}

    /**
     * Sets the label for this node
     * @param label
     */
    public void setLabel(JLabel label) {this.label = label;}

    /**
     * @return X coordinate of this node
     */
    public double getX() {return point.x;}

    /**
     * @return Y coordinate of this node
     */
    public double getY() {return point.y;}

    /**
     * @return If this node is a target
     */
    public boolean isTarget() {return isTarget;}

    /**
     * @return The article associated with this node
     */
    public Article getArticle() {return article;}

    /**
     * @return The label of this node
     */
    public JLabel getLabel() {return label;}

    @Override
    public String toString() {
        return article.title + " - X: " + point.x + ", Y: " + point.y;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Node)) return false;
        Node n = (Node) other;
        return this.getArticle().equals(n.getArticle());
    }
}
