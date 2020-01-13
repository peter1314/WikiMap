package main;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        //These are potential article sets for demonstration purposes, known to run in a reasonable time

        //String[] articles = {"Rope", "Chicken", "Water", "Firefighter", "Liver", "Rip_tide", "Harry_Potter", "Wagon"};
        //String[] articles = {"United_States", "George_Washington", "Raven", "Crow", "Salt", "River", "Game_Boy", "Wallace_and_Gromit"};
        //String[] articles = {"Farm", "George_Washington", "Raven"};

        //Alternatively, you can input the articles yourself
        String[] articles = requestArticles();

        //Creates the NodeMap of the articles, this could take a long time
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

    /**
     * Uses the console to manually input articles from the user
     * Articles must be inputted with proper format
     * @return Array of the articles, with the starting article first
     */
    public static String[] requestArticles() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please specify a starting article");
        String start = in.next();
        System.out.println("Please specify target articles, separated by commas (No spaces)");
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

    /**
     * Not currently used, use to print out path from one article to another
     * @param article1 The starting article
     * @param article2 The target article
     * @throws IOException For disconnects
     */
    public static void printPath(String article1, String article2) throws IOException {
        Article start = new Article(article1, new FamilyTree(GlobalVariables.MAX_GENERATIONS));
        Article target = new Article(article2, new FamilyTree(GlobalVariables.MAX_GENERATIONS));

        for(Article a: start.pathTo(target)) {
            System.out.println(a.toString());
        }
    }

    /**
     * Creates a node map given a list of articles
     * @param articles List of articles with the starting article first and targets following
     * @throws IOException For disconnects
     */
    public static void createNodeMap(String[] articles) throws IOException {
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
