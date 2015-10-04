package app.nevvea.cuddly_fibula.utilities;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.nevvea.cuddly_fibula.R;
import app.nevvea.cuddly_fibula.activities.DetailActivity;
import app.nevvea.cuddly_fibula.activities.MainActivity;

/**
 * The adapter for search results
 * Created by Anna on 10/2/15.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<SearchResult> resultList;
    private Context context;
    MainActivity mainActivity;
    TextView emptyTV;

    public RestaurantListAdapter (List<SearchResult> list, Context con) {
        resultList = list;
        context = con;
        mainActivity = (MainActivity) context;
        emptyTV = (TextView) ((MainActivity) context).findViewById(R.id.empty);
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
        Picasso.with(context)
                .load(sr.getRestImg())
                .into(holder.snippetIV);
        Picasso.with(context)
                .load(sr.getRatingImg())
                .into(holder.ratingIV);
        // recylcerview doesn't come with an onitemclicklistener, so here's a fix
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initiates the detail activity
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.REST_ID_TAG, sr);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // if the list is empty, display an empty message
        if (resultList.size() == 0) emptyTV.setVisibility(View.VISIBLE);
        else emptyTV.setVisibility(View.GONE);
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView nameTV;
        public ImageView snippetIV;
        public ImageView ratingIV;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameTV = (TextView) mView.findViewById(R.id.search_list_restaurant_name);
            snippetIV = (ImageView) mView.findViewById(R.id.search_list_restaurant_pic);
            ratingIV = (ImageView) mView.findViewById(R.id.search_list_restaurant_rating);
        }
    }
}
