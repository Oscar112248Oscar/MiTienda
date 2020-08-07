package milenium.oscar.mitienda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridProductLayoutAdapater extends BaseAdapter {

    List<HorizontalProductScrollModel> horizontalProductScrollModels;

    public GridProductLayoutAdapater(List<HorizontalProductScrollModel> horizontalProductScrollModels) {
        this.horizontalProductScrollModels = horizontalProductScrollModels;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModels.size();
    }/// podemos decir cuantos productos queremos que aparezcan

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView== null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
           view.setBackgroundColor(Color.parseColor("#ffffff"));

           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent productDetailsIntent= new Intent(parent.getContext(),ProductDetailsActivity.class);
                    parent.getContext().startActivity(productDetailsIntent);

               }
           });

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
