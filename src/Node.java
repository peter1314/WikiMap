import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Node {

    private Article article;
    private Point2D.Double point;
    private JLabel label;
    private boolean isTarget = false;

    public Node(Article article, int x, int y) {
        this.article = article;
        point = new Point2D.Double(x,y);
    }

    public Node(Article article) {
        this.article = article;
        point = new Point2D.Double(0,0);
    }

    public int distanceTo(Node other) throws Exception {
        return article.pathTo(other.getArticle()).size();
    }

    public double actualDistanceTo(Node other) {
        return point.distance(other.getPoint());
    }

    public void setX(double x) {point.x = x;}
    public void setY(double y) {point.y = y;}
    public void setXY(double x, double y) {point.setLocation(x,y);}
    public void setIsTarget(boolean isTarget) {this.isTarget = isTarget;}
    public void setLabel(JLabel label) {this.label = label;}

    public double getX() {return point.x;}
    public double getY() {return point.y;}
    public Point2D.Double getPoint() {return point;}
    public boolean isTarget() {return isTarget;}
    public Article getArticle() {return article;}
    public JLabel getLabel() {return label;}

    public String toString() {
        return article.title + " - X: " + point.x + ", Y: " + point.y;
    }

    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Node)) return false;
        Node n = (Node) other;
        return this.getArticle().equals(n.getArticle());
    }
}
