package app.nevvea.cuddly_fibula;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Anna on 10/2/15.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<SearchResult> resultList;
    private Context context;
    MainActivity mainActivity;
    private static final int DETAIL = 1;

    public RestaurantListAdapter (List<SearchResult> list, Context con) {
        resultList = list;
        context = con;
        mainActivity = (MainActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_result_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SearchResult sr = resultList.get(position);
        holder.nameTV.setText(sr.getName());
        holder.categoryTV.setText(sr.getCategory());
        Picasso.with(context)
                .load(sr.getSnippetImg())
                .into(holder.snippetIV);
        Picasso.with(context)
                .load(sr.getRatingImg())
                .into(holder.ratingIV);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString(DetailFragment.REST_ID_TAG, sr.getId());
                mainActivity.showFragment(DETAIL, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView nameTV;
        public TextView categoryTV;
        public ImageView snippetIV;
        public ImageView ratingIV;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameTV = (TextView) mView.findViewById(R.id.search_list_restaurant_name);
            categoryTV = (TextView) mView.findViewById(R.id.search_list_restaurant_category);
            snippetIV = (ImageView) mView.findViewById(R.id.search_list_restaurant_pic);
            ratingIV = (ImageView) mView.findViewById(R.id.search_list_restaurant_rating);
        }
    }
}
