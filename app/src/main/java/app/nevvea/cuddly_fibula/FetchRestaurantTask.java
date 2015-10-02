package app.nevvea.cuddly_fibula;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Anna on 10/1/15.
 */
public class FetchRestaurantTask extends AsyncTask<String, Void, Void>{
    private Context mContext;
    MainActivityFragment mFragment;

    public FetchRestaurantTask(Context context, MainActivityFragment fragment) {
        mContext = context;
        mFragment = fragment;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Log.d("json check", strings[0]);
        Yelp yelp = Yelp.getYelp(mContext);
        String businesses = yelp.search(strings[0], strings[1]);
        Log.d("json check", businesses);
        return null;
    }
}
