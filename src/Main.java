import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        //String[] articles = requestArticles();
        //String[] articles = {"Rope", "Chicken", "Water", "Firefighter", "Liver", "Rip_tide", "Harry_Potter", "Wagon"};
        //String[] articles = {"United_States", "George_Washington", "Raven", "Crow", "Salt", "River", "Game_Boy", "Wallace_and_Gromit"};
        //String[] articles = {"Farm", "George_Washington", "Raven", "Crow", "Salt", "River", "Game_Boy", "Wallace_and_Gromit"};
        String[] articles = {"Farm", "George_Washington", "Raven"};
        System.out.println("Creating map for articles");
        createNodeMap(articles);

        GraphicalDisplay map = new GraphicalDisplay();
        JFrame display = new JFrame();
        display.setBounds(10, 10, GlobalVariables.dispayWidth, GlobalVariables.dispayHeight);
        display.setTitle("WikiMap");
        display.setResizable(false);
        display.setVisible(true);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.add(map);
    }

    public static String[] requestArticles() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please specify a starting article.");
        String start = in.next();
        System.out.println("Please specify target articles, separated by a comma.");
        String targets = in.next();
        targets = start + "," + targets;
        System.out.println("Generating Map:");
        String[] articles = targets.split(",");
        boolean first = true;
        for(String s: articles) {
            if(first) System.out.println("\t" + s + " -->");
            else System.out.println("\t\t" + s);
            first = false;
        }
        System.out.println();
        return articles;
    }

    public static void printPath(String article1, String article2) throws Exception {
        Article start = new Article(article1, new FamilyTree(GlobalVariables.MAX_GENERATIONS));
        Article target = new Article(article2, new FamilyTree(GlobalVariables.MAX_GENERATIONS));

        for(Article a: start.pathTo(target)) {
            System.out.println(a.toString());
        }
    }

    public static void createNodeMap(String[] articles) throws Exception {
        Article start = new Article(articles[0], new FamilyTree(GlobalVariables.MAX_GENERATIONS));
        NodeMap.addNode(start);
        for(int i = 1; i < articles.length; i++) {
            Article target = new Article(articles[i], new FamilyTree(GlobalVariables.MAX_GENERATIONS));
            for(Article  a: start.pathTo(target)) {
                NodeMap.addNode(a);
                if(a.equals(target))
                    NodeMap.getNodes().get(NodeMap.getNodes().size()-1).setIsTarget(true);
            }
        }
        NodeMap.arrangeNodes();
        System.out.println(NodeMap.myToString());
    }
}
