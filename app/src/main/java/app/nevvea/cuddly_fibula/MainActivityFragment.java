package app.nevvea.cuddly_fibula;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{

    RecyclerView resultListRV;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        resultListRV = (RecyclerView) rootView.findViewById(R.id.search_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        resultListRV.setLayoutManager(layoutManager);
        resultListRV.setAdapter(new RestaurantListAdapter(new ArrayList<SearchResult>(), getActivity()));
        return rootView;
    }



    public void onTaskFinished(List<SearchResult> result) {
        Log.d("json check in fagment", result.get(0).getName());
        resultListRV.setAdapter(new RestaurantListAdapter(result, getActivity()));
    }


}
