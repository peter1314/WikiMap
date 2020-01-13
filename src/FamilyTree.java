import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class FamilyTree {

    private ArrayList<ArrayList<Article>> generations;
    private Set<String> roster = new LinkedHashSet<String>();
    public final int MAX_GENERATIONS;

    //creates a family tree with the zero generation
    public FamilyTree(int maxGenerations) {
        generations = new ArrayList<ArrayList<Article>>();
        generations.add(new ArrayList<Article>());
        MAX_GENERATIONS = maxGenerations;
    }

    //adds a family member to a given generation, if it is the first member of that generation it creates it
    public void addFamilyMember(Article article, int generation) {
        if(generation > generations.size()-1) generations.add(new ArrayList<Article>());
        generations.get(generation).add(article);
    }

    //returns an entire generation
    public ArrayList<Article> getGeneration(int generation) {
        if(generation >= generations.size()) return null;
        return generations.get(generation);
    }

    //adds a String represeting an Article to the list of Articles that have had children
    public void addToRoster(String s) { roster.add(s); }

    //returns if a String representing an Article has had children already
    public boolean inRoster(String s) {
        return roster.contains(s);
    }
}
