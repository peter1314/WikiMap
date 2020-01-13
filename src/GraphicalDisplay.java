import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicalDisplay extends JPanel {

    private boolean first = true;

    public GraphicalDisplay() {
        this.setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.YELLOW);
        for(Node n: NodeMap.getNodes()) {
            if(n.getArticle().getGeneration() == 0) g.setColor(Color.RED);
            if(n.isTarget()) g.setColor(Color.GREEN);
            g.fillOval((int) (n.getX()), (int) (n.getY()), GlobalVariables.nodeSize, GlobalVariables.nodeSize);
            g.setColor(Color.YELLOW);
        }
        g.setColor(Color.BLACK);
        for(Node n: NodeMap.getNodes()) {
            if(n.getArticle().getGeneration() != 0) {
                Node parent = NodeMap.getNodes().get(NodeMap.getNodes().indexOf(new Node(n.getArticle().getParent())));
                g.drawLine((int) n.getX() + (GlobalVariables.nodeSize / 2), (int) n.getY() + (GlobalVariables.nodeSize / 2),
                        (int) parent.getX() + (GlobalVariables.nodeSize / 2), (int) parent.getY() + (GlobalVariables.nodeSize / 2));
            }
        }
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
