package main;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The FamilyTree class represents a family tree of Wikipedia articles in which parents link to children
 */
public class FamilyTree {

    //List of the generations in the family tree, each of which is a list of articles in the generation
    private ArrayList<ArrayList<Article>> generations;
    //Set of all articles that already have children
    private Set<String> roster = new LinkedHashSet<String>();
    //Max number of generations allowed in this family tree
    public final int MAX_GENERATIONS;

    /**
     * Creates a family tree and adds the 0th generation
     * @param maxGenerations
     */
    public FamilyTree(int maxGenerations) {
        generations = new ArrayList<ArrayList<Article>>();
        generations.add(new ArrayList<Article>());
        MAX_GENERATIONS = maxGenerations;
    }

    /**
     * Adds an article to a given generation, creates that generation if necessary
     * @param article Article to add
     * @param generation Generation to add it to
     */
    public void addFamilyMember(Article article, int generation) {
        if(generation > generations.size()-1) generations.add(new ArrayList<Article>());
        generations.get(generation).add(article);
    }

    /**
     * Returns the articles in a given generation
     * @param generation The generation to get articles from
     * @return An ArrayList containing the articles in that generation
     */
    public ArrayList<Article> getGeneration(int generation) {
        if(generation >= generations.size()) return null;
        return generations.get(generation);
    }

    /**
     * Adds an article to the set of articles that have already had children
     * @param article The article to add
     */
    public void addToRoster(Article article) { roster.add(article.title); }

    /**
     * Checks if an article is in the roster
     * @param article Article to check
     * @return If that article is in the roster
     */
    public boolean inRoster(Article article) {
        return roster.contains(article.title);
    }
}
