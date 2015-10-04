package app.nevvea.cuddly_fibula.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import app.nevvea.cuddly_fibula.R;
import app.nevvea.cuddly_fibula.utilities.SearchResult;

public class DetailActivity extends AppCompatActivity {
    public static final String REST_ID_TAG = "REST_ID";
    TableRow directionRow;
    TableRow phoneRow;
    TableRow yelpRow;
    TextView snippetTxt;
    SearchResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the info that needs to be displayed in this activity
        result = (SearchResult) getIntent().getSerializableExtra(REST_ID_TAG);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(result.getName());

        // setup the view and intents to other apps
        directionRow = (TableRow) findViewById(R.id.detail_row_direction);
        phoneRow = (TableRow) findViewById(R.id.detail_row_phone);
        yelpRow = (TableRow) findViewById(R.id.detail_row_yelp);
        snippetTxt = (TextView) findViewById(R.id.detail_review_text);

        directionRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri geoLocation = Uri.parse("geo:0,0?q=" + getAddressQuery(result.getAddress()));
                intent.setData(geoLocation);
                startActivity(intent);
            }
        });

        // if phone number isn't available, we don't display the row
        if (result.getPhone().equals("")) phoneRow.setVisibility(View.GONE);
        else phoneRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + result.getPhone()));
                try {
                    startActivity(callIntent);
                } catch (SecurityException e) {
                    showAlert("I don't have your permission to make a call!");
                }
            }
        });

        yelpRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(result.getYelp());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });

        snippetTxt.setText(result.getSnippetTxt());
    }


    /**
     * encode the address in utf-8 so that it can be used for google map
     * @param address the display address of the restaurant
     * @return a String that's ready to be used for calling google map
     */
    String getAddressQuery(String address) {
        try {
            return URLEncoder.encode(address, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Takes in a message and display an alert
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
}
