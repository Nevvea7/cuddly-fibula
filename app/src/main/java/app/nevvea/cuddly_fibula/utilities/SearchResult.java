package app.nevvea.cuddly_fibula.utilities;

import java.io.Serializable;

/**
 * Object that holds the search result we get back from yelp api
 * It implements the Serializable interface so that it can be passed between activities
 * Created by Anna on 10/2/15.
 */
public class SearchResult implements Serializable {
    private String name;
    private String id;
    private String restImg;
    private String ratingImg;
    private String address;
    private String phone;
    private String snippetImg;
    private String snippetTxt;
    private String yelp;

    public SearchResult(String n, String i, String restI,
                        String ratingI, String a, String p, String sI, String sT, String y) {
        name = n;
        id = i;
        restImg = restI;
        ratingImg = ratingI;
        address = a;
        phone = p;
        snippetImg = sI;
        snippetTxt = sT;
        yelp = y;
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

    public String getRatingImg() {
        return ratingImg;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getRestImg() {
        return restImg;
    }

    public String getSnippetTxt() {
        return snippetTxt;
    }

    public String getYelp() {
        return yelp;
    }
}
