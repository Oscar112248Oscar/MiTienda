package milenium.oscar.mitienda;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;


    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new  ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        int resource= myOrderItemModelList.get(position).getProductImage();
        int rating = myOrderItemModelList.get(position).getRating();
        String title= myOrderItemModelList.get(position).getProducTitle();
        String deliveredDate = myOrderItemModelList.get(position).getDeliveryStatus();
        holder.setData(resource,title,deliveredDate,rating);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

        class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNowContainer;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productImage= itemView.findViewById(R.id.product_image);
                productTitle= itemView.findViewById(R.id.product_title);
                orderIndicator = itemView.findViewById(R.id.order_indicator);
                deliveryStatus= itemView.findViewById(R.id.order_delivered_date);
                rateNowContainer= itemView.findViewById(R.id.rate_now_container);
            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void setData(int resource, String title, String deliveyDate, int rating){
                productImage.setImageResource(resource);
                productTitle.setText(title);
                if(deliveyDate.equals("Cancelado")) {
                    orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.errorRojo)));
                }else {
                    orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.verdeConfirmacion)));

                }
                deliveryStatus.setText(deliveyDate);

                ////////// rating layout
                setRating(rating);
                for(int i=0; i<rateNowContainer.getChildCount();i++){
                    final  int starPosition=i;
                    rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            setRating(starPosition);
                        }
                    });


                }

                ////////// rating layout
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void setRating(int starPosition) {

                for(int i=0; i<rateNowContainer.getChildCount();i++){
                    ImageView starBtn= (ImageView)rateNowContainer.getChildAt(i);
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                    if(i<= starPosition){
                        starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00") ));

                    }

                }
            }



        }

}
