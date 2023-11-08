package com.yuanren.tvinteractions.view.x_ray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.model.XRayItem;
import com.yuanren.tvinteractions.view.movie_playback.PlaybackActivity;

public class XRayItemContentActivity extends Activity {
    private static final String TAG = "XRayItemContentActivity";

    public static final String SELECTED_MOVIE_ID = "selectionMovieId";
    public static final String SELECTED_XRAY_ITEM_ID = "selectionXRayItemId";

    private static final String TYPE_ITEM_ACTOR = "0";
    private static final String TYPE_ITEM_PRODUCT = "1";

    private static final String TYPE_MERCHANDISE_AMAZON = "amazon";
    private static final String TYPE_MERCHANDISE_APPLE= "apple";
    private static final String TYPE_MERCHANDISE_BESTBUY = "bestbuy";
    private static final String TYPE_MERCHANDISE_COSTCO = "costco";
    private static final String TYPE_MERCHANDISE_TARGET = "target";
    private static final String TYPE_MERCHANDISE_WALMART = "walmart";

    private ImageView image;
    private TextView title;
    private TextView price;
    private TextView description;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private TextView price1;
    private TextView price2;
    private TextView price3;

    private Movie movie;
    private XRayItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xray_item_content);

        movie = MovieList.findBy((int)getIntent().getLongExtra(SELECTED_MOVIE_ID, 0));
        item = movie.getXRayItems().get((int)getIntent().getLongExtra(SELECTED_XRAY_ITEM_ID, 0));

        image = findViewById(R.id.x_ray_image);
        title = findViewById(R.id.x_ray_title);
        price = findViewById(R.id.x_ray_price);
        description = findViewById(R.id.x_ray_description);
        btn1 = findViewById(R.id.x_ray_btn1);
        btn2 = findViewById(R.id.x_ray_btn2);
        btn3 = findViewById(R.id.x_ray_btn3);
        price1 = findViewById(R.id.price1);
        price2 = findViewById(R.id.price2);
        price3 = findViewById(R.id.price3);

        String[] content = item.getDescription().split("\\;"); //System.lineSeparator())
        title.setText(content[0]);
        Glide.with(getApplicationContext())
                .load(item.getImageUrl())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(image);
        price.setText(content[1]);
        description.setText(content[2]);

        if (item.getType() == TYPE_ITEM_PRODUCT) {
            // set up buttons for purchase link
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);

            String merchandises[] = item.getMerchandise().split(" ");
            btn1.setImageDrawable(getDrawable(getMerchandiseLogo(merchandises[0])));
            btn2.setImageDrawable(getDrawable(getMerchandiseLogo(merchandises[1])));
            btn3.setImageDrawable(getDrawable(getMerchandiseLogo(merchandises[2])));

            setUpButtons(btn1, getLink(merchandises[0]));
            setUpButtons(btn2, getLink(merchandises[1]));
            setUpButtons(btn3, getLink(merchandises[2]));

            // set up price text view
            price1.setVisibility(View.VISIBLE);
            price2.setVisibility(View.VISIBLE);
            price3.setVisibility(View.VISIBLE);

            int min = 20;
            int max = 40;
            double basePrice = Float.parseFloat(content[1].substring(1));
            double randomDelta1 = Math.random()*(max-min+1)+min;
            double randomDelta2 = Math.random()*(max-min+1)+min;
            price1.setText(content[1]);
            price2.setText("$" + String.format("%.2f", basePrice + randomDelta1));
            price3.setText("$" + String.format("%.2f", basePrice + randomDelta2));

        } else {
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);

            price1.setVisibility(View.GONE);
            price2.setVisibility(View.GONE);
            price3.setVisibility(View.GONE);

        }
    }

    private int getMerchandiseLogo(String name) {
        switch (name) {
            case TYPE_MERCHANDISE_AMAZON:
                return R.drawable.logo_amazon;
            case TYPE_MERCHANDISE_APPLE:
                return R.drawable.logo_apple;
            case TYPE_MERCHANDISE_BESTBUY:
                return R.drawable.logo_bestbuy;
            case TYPE_MERCHANDISE_COSTCO:
                return R.drawable.logo_costco;
            case TYPE_MERCHANDISE_TARGET:
                return R.drawable.logo_target;
            case TYPE_MERCHANDISE_WALMART:
                return R.drawable.logo_walmart;
            default:
                return R.drawable.logo_amazon;
        }
    }

    private String getLink(String name) {
        switch (name) {
            case TYPE_MERCHANDISE_AMAZON:
                return "Amazon";
            case TYPE_MERCHANDISE_APPLE:
                return "Apple";
            case TYPE_MERCHANDISE_BESTBUY:
                return "Best Buy";
            case TYPE_MERCHANDISE_COSTCO:
                return "Costco";
            case TYPE_MERCHANDISE_TARGET:
                return "Target";
            case TYPE_MERCHANDISE_WALMART:
                return "Walmart";
            default:
                return "default";
        }
    }

    private void setUpButtons(ImageButton button, String msg) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), msg + " link is clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        btn1.setImageAlpha(getResources().getInteger(R.integer.x_ray_button_alpha_unfocused));
        btn2.setImageAlpha(getResources().getInteger(R.integer.x_ray_button_alpha_unfocused));
        btn3.setImageAlpha(getResources().getInteger(R.integer.x_ray_button_alpha_unfocused));
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    button.setImageAlpha(getResources().getInteger(R.integer.x_ray_button_alpha_focused)); //alpha_value between 0...255
                } else {
                    button.setImageAlpha(getResources().getInteger(R.integer.x_ray_button_alpha_unfocused));
                }
            }
        });
    }
}
