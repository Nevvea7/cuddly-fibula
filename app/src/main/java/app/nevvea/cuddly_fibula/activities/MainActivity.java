package app.nevvea.cuddly_fibula.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import app.nevvea.cuddly_fibula.utilities.FetchRestaurantTask;
import app.nevvea.cuddly_fibula.R;
import app.nevvea.cuddly_fibula.utilities.RestaurantListAdapter;
import app.nevvea.cuddly_fibula.utilities.SearchResult;

public class MainActivity extends AppCompatActivity {
    private Context context;
    RecyclerView resultListRV;
    List<SearchResult> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = MainActivity.this;
        searchResults = new ArrayList<>();

        // setup the recyclerview and the floatingaction button
        resultListRV = (RecyclerView) findViewById(R.id.search_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        resultListRV.setLayoutManager(layoutManager);
        resultListRV.setAdapter(new RestaurantListAdapter(searchResults, context));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });
    }

    /**
     * Called from the floating action button, this method displays a dialog for
     * the user to enter search terms and executes an AsyncTask to get results.
     */
    private void showSearchDialog() {
        View dialogContent = LayoutInflater.from(context)
                                            .inflate(R.layout.content_dialog, null);
        final EditText searchTermET = (EditText) dialogContent.findViewById(R.id.dialog_search_term_edittext);
        final EditText searchLocET = (EditText) dialogContent.findViewById(R.id.dialog_search_location_edittext);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find your favorite restaurant!")
                .setView(dialogContent)
                .setPositiveButton("Go!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // check against possible failures
                        if (!isConnectedToInternet(context)) {
                            showAlert("I need to access the internet to do the search!");
                            dialogInterface.cancel();
                            return;
                        }
                        FetchRestaurantTask task = new FetchRestaurantTask(context);
                        String term = searchTermET.getText().toString(), loc = searchLocET.getText().toString();
                        if (term.equals("") || loc.equals("")) {
                            showAlert("Both fields need to be filled!");
                            dialogInterface.cancel();
                            return;
                        }
                        // execute the async task
                        task.execute(term, loc);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * A list of results is passed back from the AsyncTask to be displayed
     * @param result
     */
    public void onTaskFinished(List<SearchResult> result) {
        // something's wrong, probably the search terms is not valid
        if (result.size() == 0) {
            showAlert("Oh crap, didn't find anything on that...try search something else!");
        }
        resultListRV.setAdapter(new RestaurantListAdapter(result, context));
    }

    /**
     * show an alert that contains the passed in message
     * @param message
     */
    void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    private boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

}
