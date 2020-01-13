import javax.swing.*;
import java.awt.*;

/**
 * Class used to generate a graphical representation of the node map
 * Extends JPanel
 */
public class GraphicalDisplay extends JPanel {

    //Whether this is the first time drawing the map
    //Needed to prevent text stacking as window is minimized and reopened
    private boolean first = true;

    /**
     * Creates a GraphicalDisplay with a white background
     */
    public GraphicalDisplay() {
        this.setBackground(Color.WHITE);
    }

    @Override
    /**
     * Used to paint all of the nodes and their connections
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draws the nodes, coloring the Adam node red, targets green, and path nodes yellow
        g.setColor(Color.YELLOW);
        for(Node n: NodeMap.getNodes()) {
            if(n.getArticle().getGeneration() == 0) g.setColor(Color.RED);
            if(n.isTarget()) g.setColor(Color.GREEN);
            g.fillOval((int) (n.getX()), (int) (n.getY()), GlobalVariables.nodeSize, GlobalVariables.nodeSize);
            g.setColor(Color.YELLOW);
        }
        //Draws black lines to connect nodes as needed
        g.setColor(Color.BLACK);
        for(Node n: NodeMap.getNodes()) {
            if(n.getArticle().getGeneration() != 0) {
                Node parent = NodeMap.getNodes().get(NodeMap.getNodes().indexOf(new Node(n.getArticle().getParent())));
                g.drawLine((int) n.getX() + (GlobalVariables.nodeSize / 2), (int) n.getY() + (GlobalVariables.nodeSize / 2),
                        (int) parent.getX() + (GlobalVariables.nodeSize / 2), (int) parent.getY() + (GlobalVariables.nodeSize / 2));
            }
        }
        //Draws text to label nodes, but only if this is the intial draw
        if(first) {
            for(Node n: NodeMap.getNodes()) {
                JLabel jlabel = new JLabel();
                jlabel.setText(n.getArticle().getTitle());
                jlabel.setFont(new Font("Verdana",1,10));
                jlabel.setBounds((int) n.getX(), (int) n.getY(), 100, 15);
                n.setLabel(jlabel);
                add(jlabel);
            }
            first = false;
        }
        else {
            for(Node n: NodeMap.getNodes()) {
                n.getLabel().setBounds((int) n.getX(), (int) n.getY(), 100, 15);
            }
        }
    }
}
