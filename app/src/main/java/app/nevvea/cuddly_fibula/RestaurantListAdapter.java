package app.nevvea.cuddly_fibula;

import android.content.Context;
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

    public RestaurantListAdapter (List<SearchResult> list, Context con) {
        resultList = list;
        context = con;
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
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView nameTV;
        public TextView categoryTV;
        public ImageView snippetIV;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameTV = (TextView) mView.findViewById(R.id.search_list_restaurant_name);
            categoryTV = (TextView) mView.findViewById(R.id.search_list_restaurant_category);
            snippetIV = (ImageView) mView.findViewById(R.id.search_list_restaurant_pic);
        }
    }
}
