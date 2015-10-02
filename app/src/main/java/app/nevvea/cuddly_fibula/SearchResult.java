package app.nevvea.cuddly_fibula;

/**
 * Created by Anna on 10/2/15.
 */
public class SearchResult {
    private String name;
    private String category;
    private String id;
    private String snippetImg;

    public SearchResult(String n, String c, String i, String s) {
        name = n;
        category = c;
        id = i;
        snippetImg = s;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSnippetImg() {
        return snippetImg;
    }
}
