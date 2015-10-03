package app.nevvea.cuddly_fibula;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DetailActivity extends AppCompatActivity {
    public static final String REST_ID_TAG = "REST_ID";
    TableRow directionRow;
    TableRow phoneRow;
    TableRow yelpRow;
    SearchResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = (SearchResult) getIntent().getSerializableExtra(REST_ID_TAG);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(result.getName());

        directionRow = (TableRow) findViewById(R.id.detail_row_direction);
        phoneRow = (TableRow) findViewById(R.id.detail_row_phone);
        yelpRow = (TableRow) findViewById(R.id.detail_row_yelp);

        directionRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri geoLocation = Uri.parse("geo:0,0?q=" + getAddressQuery(result.getAddress()));
                intent.setData(geoLocation);
                startActivity(intent);
            }
        });

        phoneRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + result.getPhone()));

                try {
                    startActivity(callIntent);
                } catch (SecurityException e) {

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

    }

    String getAddressQuery(String address) {
        try {
            return URLEncoder.encode(address, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    void showAlert(String message) {
        
    }
}
