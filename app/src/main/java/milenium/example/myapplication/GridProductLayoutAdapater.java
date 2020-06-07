package milenium.example.myapplication;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

public class GridProductLayoutAdapater extends BaseAdapter {

    List<HorizontalProductScrollModel> horizontalProductScrollModels;

    public GridProductLayoutAdapater(List<HorizontalProductScrollModel> horizontalProductScrollModels) {
        this.horizontalProductScrollModels = horizontalProductScrollModels;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView== null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);


            //view.setBackgroundColor(Color.parseColor("#ffffff"));

            ImageView productImage= view.findViewById(R.id.horizontal_product_image);
            TextView productTitle = view.findViewById(R.id.horizontal_productTitle);
            TextView productDescription = view.findViewById(R.id.horizontal_productdescripcion);
            TextView productPrice = view.findViewById(R.id.horizontal_productPrecio);
            productImage.setImageResource(horizontalProductScrollModels.get(position).getProductImage());
            productTitle.setText(horizontalProductScrollModels.get(position).getProductTitle());
            productDescription.setText(horizontalProductScrollModels.get(position).getProductDescription());
            productPrice.setText(horizontalProductScrollModels.get(position).getProductPrice());

        }else{

        view= convertView;
        }


        return view;
    }
}
