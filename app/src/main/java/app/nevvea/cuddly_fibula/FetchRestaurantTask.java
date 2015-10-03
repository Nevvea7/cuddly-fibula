package app.nevvea.cuddly_fibula;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Anna on 10/1/15.
 */
public class FetchRestaurantTask extends AsyncTask<String, Void,List<SearchResult>>{
    private Context mContext;
    MainActivityFragment mFragment;

    public FetchRestaurantTask(Context context, MainActivityFragment fragment) {
        mContext = context;
        mFragment = fragment;
    }

    @Override
    protected List<SearchResult> doInBackground(String... strings) {
        Yelp yelp = Yelp.getYelp(mContext);
        String businesses = yelp.search(strings[0], strings[1]);
        Log.d("json check", businesses);
        List<SearchResult> results = Utility.processJson(businesses);
        return results;
    }

    @Override
    protected void onPostExecute(List<SearchResult> results) {
        Log.d("json check in post", results.get(0).getName());
        mFragment.onTaskFinished(results);
    }


}
