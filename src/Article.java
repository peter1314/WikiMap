import java.lang.reflect.Array;
import java.util.ArrayList;

public class Article {

    public final String title;
    public boolean fertile;
    public int generation;
    private ArrayList<Article> children;
    private Article parent;
    private FamilyTree tree;

    //creates empty article for use in contains() method
    public Article(String title) {
        this.title = title;
    }

    //creates and article off a parent
    public Article(String title, Article parent) {
        this.title = title;
        this.parent = parent;
        fertile = true;
        tree = parent.tree;
        generation = parent.getGeneration() + 1;
        if(generation >= tree.MAX_GENERATIONS) this.fertile = false;
        children = new ArrayList<>();
        tree.addFamilyMember(this, this.generation);


    }

    //creates and All Father article establishing a new tree
    public Article(String title, FamilyTree tree) {
        this.title = title;
        generation = 0;
        fertile = true;
        children = new ArrayList<>();
        parent = null;
        this.tree = tree;
        tree.addFamilyMember(this, this.generation);
    }

    //checks if this member has had children already in the family tree
    private boolean checkFertility(boolean original) {
        if(tree.inRoster(this.title)) return false;
        tree.addToRoster(this.title);
        return original;
    }

    //finds all wiki articles this article links to and adds them to the children array
    public int findChildren() throws Exception {
        fertile = checkFertility(fertile);
        if(fertile) children = WikiReader.getChildren(this);
        return children.size();
    }

    public String getTitle() {return title;}

    public int getGeneration() {return generation;}

    public ArrayList<Article> getChildren() {
        return children;
    }

    public Article getParent() {
        return parent;
    }

    public FamilyTree getFamilyTree() {return tree;}

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

    public ArrayList<Article> pathTo(Article targetArticle) throws Exception {
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

    public ArrayList<Article> pathToEither(Article targetArticle) throws Exception {
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
