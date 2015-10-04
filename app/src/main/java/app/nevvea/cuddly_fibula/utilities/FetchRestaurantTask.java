package app.nevvea.cuddly_fibula.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.nevvea.cuddly_fibula.activities.MainActivity;

/**
 * Calls the Yelp api and get results
 * Created by Anna on 10/1/15.
 */
public class FetchRestaurantTask extends AsyncTask<String, Void, List<SearchResult>>{
    private Context mContext;

    public FetchRestaurantTask(Context context) {
        mContext = context;
    }

    @Override
    protected List<SearchResult> doInBackground(String... strings) {
        Yelp yelp = Yelp.getYelp(mContext);
        String businesses = yelp.search(strings[0], strings[1]);
        return processJson(businesses);
    }

    /**
     * When the results are back, we call the onTaskFinished method in the MainActivity to
     * update the UI
     * @param results
     */
    @Override
    protected void onPostExecute(List<SearchResult> results) {
        ((MainActivity) mContext).onTaskFinished(results);
    }

    /**
     * extract info that I need from the json string passed back by Yelp api
     * @param jsonStr
     * @return
     */
    public List<SearchResult> processJson(String jsonStr) {
        List<SearchResult> res = new ArrayList<>();
        try {
            // turn json string to a jsonObject
            JSONObject json = new JSONObject(jsonStr);
            JSONArray businesses = json.getJSONArray("businesses");

            for (int i = 0; i < 40; i++) {
                JSONObject curRes = businesses.getJSONObject(i);
                String name = curRes.getString("name");
                String id = curRes.getString("id");
                String restImg = curRes.getString("image_url");
                String snippetImg = curRes.getString("snippet_image_url");
                String snippetTxt = curRes.getString("snippet_text");
                String yelp = curRes.getString("mobile_url");
                String rating = curRes.getString("rating_img_url");
                String phone;

                try {
                    phone = curRes.getString("phone");
                } catch (JSONException e) {
                    phone = "";
                }

                String address = formatAddress(curRes.getJSONObject("location"));
                SearchResult sr = new SearchResult(name, id, restImg,
                        rating, address, phone, snippetImg, snippetTxt, yelp);
                res.add(sr);

            }
        }  catch (JSONException e) {
            Log.d("json check", e.getMessage());
        }
        return res;
    }

    private String formatAddress(JSONObject location) throws JSONException {
        StringBuilder sb = new StringBuilder();
        JSONArray addr = location.getJSONArray("display_address");
        sb.append(addr.get(0));
        for (int i = 1; i < addr.length(); i++) {
            sb.append(",");
            sb.append(addr.get(i));
        }
        return sb.toString();
    }
}
