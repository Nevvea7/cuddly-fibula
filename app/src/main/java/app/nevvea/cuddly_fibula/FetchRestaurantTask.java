package app.nevvea.cuddly_fibula;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
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
        Log.d("json check", businesses);
        return processJson(businesses);
    }

    @Override
    protected void onPostExecute(List<SearchResult> results) {
        Log.d("json check in post", results.get(0).getName());
        ((MainActivity) mContext).onTaskFinished(results);
    }

    public List<SearchResult> processJson(String jsonStr) {
        List<SearchResult> res = new ArrayList<>();
        try {
            // turn json string to a jsonObject
            JSONObject json = new JSONObject(jsonStr);
            JSONArray businesses = json.getJSONArray("businesses");

            for (int i = 0; i < 10; i++) {
                JSONObject curRes = businesses.getJSONObject(i);
                String name = curRes.getString("name");
                String id = curRes.getString("id");
                String restImg = curRes.getString("image_url");
                String snippetImg = curRes.getString("snippet_image_url");
                String snippetTxt = curRes.getString("snippet_text");
                String yelp = curRes.getString("mobile_url");
                String rating = curRes.getString("rating_img_url");
                String phone = curRes.getString("phone");
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
