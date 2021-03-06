package milenium.oscar.mitienda;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalProducScrollAdapter extends RecyclerView.Adapter<HorizontalProducScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModel> horizontalProductScrollModels;

    public HorizontalProducScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModels) {
        this.horizontalProductScrollModels = horizontalProductScrollModels;
    }

    @NonNull
    @Override
    public HorizontalProducScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProducScrollAdapter.ViewHolder holder, int position) {
    String resource= horizontalProductScrollModels.get(position).getProductImage();
    String title= horizontalProductScrollModels.get(position).getProductTitle();
    String description  = horizontalProductScrollModels.get(position).getProductDescription();
    String price  = horizontalProductScrollModels.get(position).getProductPrice();
    String productId = horizontalProductScrollModels.get(position).getProductID();
        holder.setData(productId,resource,title,description,price);

    }

    @Override
    public int getItemCount() {
        if(horizontalProductScrollModels.size()>8){
            return 8 ;
        }
        else {
            return horizontalProductScrollModels.size();

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.horizontal_product_image);
            productTitle= itemView.findViewById(R.id.horizontal_productTitle);
            productDescription= itemView.findViewById(R.id.horizontal_productdescripcion);
            productPrice= itemView.findViewById(R.id.horizontal_productPrecio);



        }

        private void setData(final String productId,String resource,String title,String description,String price){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.sinfondo)).into(productImage);// home
            //es el icono de casa en las categorias
            productPrice.setText("$"+price);
            productDescription.setText(description);
            productTitle.setText(title);


            if(!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("PRODUCT_ID",productId);
                        itemView.getContext().startActivity(productDetailsIntent);

                    }
                });
            }

        }



    }
}
