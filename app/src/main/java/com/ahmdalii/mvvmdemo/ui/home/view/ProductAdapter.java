package com.ahmdalii.mvvmdemo.ui.home.view;

import static com.ahmdalii.mvvmdemo.utils.AppConstants.ITEM;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmdalii.mvvmdemo.R;
import com.ahmdalii.mvvmdemo.model.Products;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    Context context;
    ArrayList<Products> products = new ArrayList<>();
    MoveInterface mMoveInterface;

    public ProductAdapter(Context context, MoveInterface moveInterface) {

        this.context = context;
        this.mMoveInterface = moveInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mMoveInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTxtViewTitle().setText(products.get(position).getTitle());

        if (products.get(position).getDiscountPercentage() == 0.0) {
            holder.getTxtViewPrice().setText(context.getString(R.string.dollar).concat(String.valueOf(products.get(position).getPrice())));
        } else {
            holder.getTxtViewPrice().setText(context.getString(R.string.dollar).concat(String.valueOf(products.get(position).getPrice())));
            holder.getTxtViewPrice().setPaintFlags(holder.getTxtViewPrice().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.getTxtViewDiscount().setVisibility(View.VISIBLE);
            holder.getTxtViewDiscount().setText(context.getString(R.string.dollar).concat(String.valueOf(holder.calculateDiscount())));
        }

        holder.getRatingBar().setStepSize(0.01f);
        holder.getRatingBar().setRating(products.get(position).getRating());
        holder.getRatingBar().setEnabled(false);

        Glide.with(context)
                .load(products.get(position).getProductImage())
                .into(holder.getIvPhoto());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataToAdapter(ArrayList<Products> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivPhoto;
        private TextView txtViewTitle, txtViewPrice, txtViewDiscount;
        private RatingBar ratingBar;
        MoveInterface mMoveInterface;

        public ViewHolder(@NonNull View itemView, MoveInterface moveInterface) {
            super(itemView);
            this.mMoveInterface = moveInterface;
            itemView.setOnClickListener(this);

        }



        public TextView getTxtViewTitle() {
            if (txtViewTitle == null) {
                txtViewTitle = itemView.findViewById(R.id.txtViewTitle);
            }
            return txtViewTitle;
        }

        public TextView getTxtViewDiscount() {
            if (txtViewDiscount == null) {
                txtViewDiscount = itemView.findViewById(R.id.txtViewDiscount);
            }
            return txtViewDiscount;
        }

        public TextView getTxtViewPrice() {
            if (txtViewPrice == null) {
                txtViewPrice = itemView.findViewById(R.id.txtViewPrice);
            }
            return txtViewPrice;
        }

        public ImageView getIvPhoto() {
            if (ivPhoto == null) {
                ivPhoto = itemView.findViewById(R.id.ivPhoto);
            }
            return ivPhoto;
        }

        public double calculateDiscount() {
            double price = products.get(getAdapterPosition()).getPrice();
            double discount = products.get(getAdapterPosition()).getDiscountPercentage();
            double total = price - (price * discount/100);
            return Math.round(total);
        }

        public RatingBar getRatingBar() {
            if (ratingBar == null) {
                ratingBar = itemView.findViewById(R.id.ratingBar);
            }
            return ratingBar;
        }

        @Override
        public void onClick(View v) {
            mMoveInterface.move(products.get(getAdapterPosition()));
        }
    }
}
