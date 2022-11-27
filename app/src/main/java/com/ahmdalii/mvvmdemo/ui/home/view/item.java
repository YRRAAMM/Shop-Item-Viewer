package com.ahmdalii.mvvmdemo.ui.home.view;

import static com.ahmdalii.mvvmdemo.utils.AppConstants.ITEM;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmdalii.mvvmdemo.R;
import com.ahmdalii.mvvmdemo.model.Products;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class item extends AppCompatActivity {

    private ArrayList<SlideModel> mImageList;
    private Products mSelectedProduct;

    private ImageSlider mImageSlider;
    private TextView txtViewTitle, txtViewPrice, txtViewReview, txtViewDescription;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        getProduct();
        setImageSlider();
        setItemData();

        Log.d("TAG2", mSelectedProduct.getImages().toString());

    }

    private void setItemData() {
        getTxtViewTitle().setText(mSelectedProduct.getTitle());
        getTxtViewPrice().setText(this.getString(R.string.dollar).concat(String.valueOf(mSelectedProduct.getPrice())));
        getTxtViewDescription().setText(mSelectedProduct.getDescription());
        getTxtViewReviews().setText(String.valueOf(mSelectedProduct.getRating()).concat(" ").concat(this.getString(R.string.Rating)));

        getRatingBar().setStepSize(0.01f);
        getRatingBar().setRating(mSelectedProduct.getRating());
        getRatingBar().setEnabled(false);
    }

    private void getProduct() {
        Intent intent = getIntent();
        mSelectedProduct = intent.getParcelableExtra(ITEM);
    }

    private void setImageSlider() {
        mImageSlider = findViewById(R.id.image_slider);
        mImageList = new ArrayList<>();
        for (String image : mSelectedProduct.getImages()) {
            mImageList.add(new SlideModel(image, null));
        }
        mImageSlider.setImageList(mImageList, ScaleTypes.FIT);
    }

    public TextView getTxtViewTitle() {
        if (txtViewTitle == null) {
            txtViewTitle = findViewById(R.id.txtViewTitle);
        }
        return txtViewTitle;
    }

    public TextView getTxtViewPrice() {
        if (txtViewPrice == null) {
            txtViewPrice = findViewById(R.id.txtViewPrice);
        }
        return txtViewPrice;
    }

    public TextView getTxtViewReviews() {

        if (txtViewReview == null) {
            txtViewReview = findViewById(R.id.txtViewReview);
        }
        return txtViewReview;
    }

    public TextView getTxtViewDescription() {
        if (txtViewDescription == null) {
            txtViewDescription = findViewById(R.id.txtViewDescription);
        }
        return txtViewDescription;
    }

    public RatingBar getRatingBar() {
        if (ratingBar == null) {
            ratingBar = findViewById(R.id.ratingBar);
        }
        return ratingBar;
    }
    

    public double calculateDiscount() {
        double price = mSelectedProduct.getPrice();
        double discount = mSelectedProduct.getDiscountPercentage();
        double total = price - (price * discount/100);
        return Math.round(total);
    }

}