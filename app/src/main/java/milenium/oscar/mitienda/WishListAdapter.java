package milenium.oscar.mitienda;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static milenium.oscar.mitienda.ProductDetailsActivity.running_wishlist_query;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {


   private List<WishListModel> wishListModelList;
   private Boolean wishlist;
   private int lastposition =-1;

    public WishListAdapter(List<WishListModel> wishListModelList, Boolean wishlist) {
        this.wishListModelList = wishListModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent,false);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        String productId = wishListModelList.get(position).getProductId();
        String resource = wishListModelList.get(position).getProductImage();
        String title= wishListModelList.get(position).getProductTitle();
        long freeCoupens= wishListModelList.get(position).getFreeCoupens();
        String rating= wishListModelList.get(position).getRating();
        long totalRating= wishListModelList.get(position).getTotalRatings();
        String productPrice= wishListModelList.get(position).getProductPrice();
        String cuttedPrice= wishListModelList.get(position).getCuttedPrice();
        boolean paymentMethod= wishListModelList.get(position).isCOD();
        boolean inStock = wishListModelList.get(position).isInStock();

        holder.setData(productId,resource,title,freeCoupens,rating,totalRating,productPrice,cuttedPrice,paymentMethod, position,inStock);


        if(lastposition < position){
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastposition = position;
        }


    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRating;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupen);
            coupenIcon = itemView.findViewById(R.id.coupen_icon);
            rating = itemView.findViewById(R.id.tinta_product_rating_miniview);
            totalRating = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_method);
            deleteBtn = itemView.findViewById(R.id.delet_btn);


        }

        private void setData(final String productId, String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean COD, final int index, boolean inStock) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.sinfondo)).into(productImage);
            productTitle.setText(title);

            if(freeCoupensNo !=0 && inStock){
                coupenIcon.setVisibility(View.VISIBLE);
                if(freeCoupensNo ==1){
                    freeCoupens.setText("Cupon: "+ freeCoupensNo + " Gratis");

                }else {
                    freeCoupens.setText("Cupones: "+ freeCoupensNo + " Gratis");

                }

            }else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);

            }

            LinearLayout linearLayout = (LinearLayout) rating.getParent();

            if (inStock){

                rating.setVisibility(View.VISIBLE);
                totalRating.setVisibility(View.VISIBLE);
                   productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);

                rating.setText(averageRate);
                totalRating.setText(""+totalRatingsNo+"(calificaciones)");
                productPrice.setText("$"+price);
                cuttedPrice.setText("$"+cuttedPriceValue);

                if(COD){
                    paymentMethod.setVisibility(View.VISIBLE);
                }else {
                    paymentMethod.setVisibility(View.INVISIBLE);

                }

            }else {
                linearLayout.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                totalRating.setVisibility(View.INVISIBLE);
                productPrice.setText("AGOTADO");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.errorRojo));
                cuttedPrice.setVisibility(View.INVISIBLE);
                paymentMethod.setVisibility(View.INVISIBLE);
            }



        if(wishlist){
            deleteBtn.setVisibility(View.VISIBLE);
        }else {
            deleteBtn.setVisibility(View.GONE);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running_wishlist_query) {
                    running_wishlist_query = true;
                    DBqueries.removeFromWishList(index, itemView.getContext());
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent productDetailsIntent= new Intent(itemView.getContext(),ProductDetailsActivity.class);
                productDetailsIntent.putExtra("PRODUCT_ID",productId);
                itemView.getContext().startActivity(productDetailsIntent);

            }
        });

        }


    }
}
