package app.nevvea.cuddly_fibula;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods that are repeatedly used
 *
 * Created by Anna on 10/2/15.
 */
public class Utility {
    public static List<SearchResult> processJson(String jsonStr) {
        List<SearchResult> res = new ArrayList<>();
        try {
            // turn json string to a jsonObject
            JSONObject json = new JSONObject(jsonStr);
            JSONArray businesses = json.getJSONArray("businesses");

            for (int i = 0; i < 10; i++) {
                JSONObject curRes = businesses.getJSONObject(i);
                String name = curRes.getString("name");
                String id = curRes.getString("id");
                String category = "holder";
                String snippet = curRes.getString("image_url");
                String rating = curRes.getString("rating_img_url");
                SearchResult sr = new SearchResult(name, category, id, snippet, rating);
                res.add(sr);

            }
        }  catch (JSONException e) {
            Log.d("json check", e.getMessage());
        }
        return res;
    }

}
