package milenium.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
    int resource= horizontalProductScrollModels.get(position).getProductImage();
    String title= horizontalProductScrollModels.get(position).getProductTitle();
    String description  = horizontalProductScrollModels.get(position).getProductDescription();
        String price  = horizontalProductScrollModels.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductDescription(description);
        holder.setProductPrice(price);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.horizontal_product_image);
            productTitle= itemView.findViewById(R.id.horizontal_productTitle);
            productDescription= itemView.findViewById(R.id.horizontal_productdescripcion);
            productPrice= itemView.findViewById(R.id.horizontal_productPrecio);

        }

        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }

        private void setProductTitle(String title){
            productTitle.setText(title);

        }

        private void setProductDescription(String description){
            productDescription.setText(description);

        }


        private void setProductPrice(String price){
            productPrice.setText(price);

        }

    }
}
