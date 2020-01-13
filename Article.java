import java.io.IOException;
import java.util.ArrayList;

/**
 * The Article class represents a Wikipedia article in the context of a family tree of articles
 * In this family tree, parent articles are articles that contain links to children articles
 */
public class Article {

    //Title of the article
    public final String title;
    //Represents if this article has already had its children generated, if it did, its infertile
    public boolean fertile;
    //Represents how many levels down the family tree of articles this article lies
    public int generation;
    //The articles that can be reached from this article are its children
    private ArrayList<Article> children;
    //The article we reached this article from is its parent
    private Article parent;
    //The tree of articles this article is associated with
    private FamilyTree tree;

    /**
     * Creates an empty article for use in the contains() method
     * @param title Title of the article
     */
    public Article(String title) {
        this.title = title;
    }

    /**
     * Creates and article given its parent article
     * @param title Title of the article
     * @param parent Article from which this article was reached
     */
    public Article(String title, Article parent) {
        this.title = title;
        this.parent = parent;
        fertile = true;
        tree = parent.tree;
        generation = parent.getGeneration() + 1;
        //In order to prevent the program from running an unreasonably long time, we can cap the number of generations
        if(generation >= tree.MAX_GENERATIONS) this.fertile = false;
        children = new ArrayList<>();
        //Add this article to the family tree
        tree.addFamilyMember(this, this.generation);


    }

    /**
     * Creates an Adam article which has no parent and originates a family tree
     * @param title Title of the article
     * @param tree New family tree
     */
    public Article(String title, FamilyTree tree) {
        this.title = title;
        generation = 0;
        fertile = true;
        children = new ArrayList<>();
        parent = null;
        this.tree = tree;
        tree.addFamilyMember(this, this.generation);
    }

    /**
     * Returns if the article should be fertile
     * @param original Whether this is the first occurrence of this article, an unoriginal article will always be infertile
     * @return If the article should be fertile
     */
    private boolean checkFertility(boolean original) {
        //The roster is a set of visited articles, if its already in this list it already has children and should be infertile
        if(tree.inRoster(this)) return false;
        tree.addToRoster(this);
        return original;
    }

    /**
     * If the article is fertile, generates all its children and adds them to the children list
     * @return The number of children found
     * @throws IOException For disconnects
     */
    public int findChildren() throws IOException {
        fertile = checkFertility(fertile);
        if(fertile) children = WikiReader.getChildren(this);
        return children.size();
    }

    /**
     * @return The title of the article
     */
    public String getTitle() {return title;}

    /**
     * @return The generation of the article
     */
    public int getGeneration() {return generation;}

    /**
     * @return An ArrayList of the article's children
     */
    public ArrayList<Article> getChildren() { return children; }

    /**
     * @return The article's parent
     */
    public Article getParent() { return parent; }

    /**
     * @return The article's family tree
     */
    public FamilyTree getFamilyTree() {return tree;}

    /**
     * @return An ArrayList of articles which traces this article's lineage from the first article in its family tree
     */
    public ArrayList<Article> getLineage() {
        if(parent == null) {
            ArrayList<Article> lineage = new ArrayList<>();
            lineage.add(this);
            return lineage;
        }
        ArrayList<Article> lineage = parent.getLineage();
        lineage.add(this);
        return lineage;
    }

    /**
     * Finds the shortest possible path from this article to a target article
     * @param targetArticle The article to reach
     * @return An ArrayList of articles containing the path from this article to its target, or null of none was found
     * @throws IOException For disconnects
     */
    public ArrayList<Article> pathTo(Article targetArticle) throws IOException {
        Article start = new Article(this.title, new FamilyTree(this.getFamilyTree().MAX_GENERATIONS));
        Article finish = new Article(targetArticle.title, new FamilyTree(this.getFamilyTree().MAX_GENERATIONS));

        for(int g = 0; g < this.getFamilyTree().MAX_GENERATIONS+1; g++) {
            for(Article a: start.getFamilyTree().getGeneration(g)) {
                a.findChildren();
                if (a.getChildren().contains(finish)) {
                    return a.getChildren().get(a.getChildren().indexOf(finish)).getLineage();
                }
            }
        }
        return null;
    }

    /**
     * No longer used, finds a path of this article to a target article of visa versa, which ever is faster
     * @param targetArticle The article to reach or be reached from
     * @return An ArrayList of articles containing the generated path
     * @throws IOException For disconnects
     */
    public ArrayList<Article> pathToEither(Article targetArticle) throws IOException {
        Article start = new Article(this.title, new FamilyTree(this.getFamilyTree().MAX_GENERATIONS));
        Article finish = new Article(targetArticle.title, new FamilyTree(this.getFamilyTree().MAX_GENERATIONS));

        for(int g = 0; g < this.getFamilyTree().MAX_GENERATIONS+1; g++) {
            for(Article a: start.getFamilyTree().getGeneration(g)) {
                a.findChildren();
                if (a.getChildren().contains(finish)) {
                    return a.getChildren().get(a.getChildren().indexOf(finish)).getLineage();
                }
            }
            for(Article a: finish.getFamilyTree().getGeneration(g)) {
                a.findChildren();
                if (a.getChildren().contains(start)) {
                    return a.getChildren().get(a.getChildren().indexOf(start)).getLineage();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof  Article)) return false;
        Article o = (Article) other;
        return this.title.equals(o.title);
    }
}
