package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetallesPago extends AppCompatActivity {

    TextView id,estatus, monto;
    private Button continueShippingBtn;
    public static boolean successResponse= false ;
    public static boolean fromCart;
    private FirebaseFirestore firebaseFirestore;
    //public static List<CartItemModel> cartItemModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pago);

        id = findViewById(R.id.idTransaccion);
        continueShippingBtn = findViewById(R.id.continueShippingBtn);
        estatus = findViewById(R.id.estatus);

        firebaseFirestore = FirebaseFirestore.getInstance();
    //    monto= findViewById(R.id.Monto);

        //Recibo los datos



        if(OTPconfirmationActivity.envia){
            Intent otp= getIntent();
            int id_random= otp.getIntExtra("envia",0);
            id.setText(""+id_random);

        }else{
            Intent intent= getIntent();

            try {
                JSONObject  jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));

                verDetalles(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        continueShippingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         ///   Intent intent1 = new Intent(getApplicationContext(),navegacionMenu.class);
          //  startActivity(intent1);

                finish();
            }
        });


    }

    private void verDetalles(JSONObject response, String paymentAmount) {

        try {



            if(navegacionMenu.navegacionActivity != null){
                navegacionMenu.navegacionActivity.finish();
                navegacionMenu.navegacionActivity = null;
                navegacionMenu.showCart = false;
            }else {
                navegacionMenu.resetNavegacionMenu = true;
            }

            if(ProductDetailsActivity.productDetailsActivity != null){
                ProductDetailsActivity.productDetailsActivity.finish();
                ProductDetailsActivity.productDetailsActivity = null;
            }

            if(DeliveryActivity.deliveryActivity != null){
                DeliveryActivity.deliveryActivity.finish();
                DeliveryActivity.deliveryActivity = null;
            }

            //// esta parte conrresponde a deliveriActicity pero yo hice otra actividad que deberia contener lo mismo
            // poe eso llamo a las variables de delivery aca




            if(fromCart){
                DeliveryActivity.loadingDialog.show();

                Map<String,Object> updateCartList = new HashMap<>();
                long cartListSize =0;
                final List<Integer> indexList = new ArrayList<>();

                for (int x=0; x< DBqueries.cartList.size();x++){
                   if( !DeliveryActivity.cartItemModelList.get(x).isInStock()){
                       updateCartList.put("product_ID_"+ cartListSize,DeliveryActivity.cartItemModelList.get(x).getProductID());
                       cartListSize++;
                   }else {
                       indexList.add(x);
                   }
                }

                updateCartList.put("list_size",cartListSize);

                FirebaseFirestore.getInstance().collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").
                        document("MY_CART").set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            for(int x=0; x < indexList.size();x++){
                                DBqueries.cartList.remove(indexList.get(x).intValue());
                                DBqueries.cartItemModelList.remove(indexList.get(x).intValue());
                                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size()-1);

                            }

                        }else{
                            String error =task.getException().getMessage();
                            Toast.makeText(DetallesPago.this,error,Toast.LENGTH_SHORT).show();

                        }
                       // DeliveryActivity.loadingDialog.dismiss();

                    }
                });

            }

            successResponse = true;
            DeliveryActivity.getQtyIds = false;


           id.setText(response.getString("id"));
            //estatus.setText(response.getString("state"));
//monto.setText(response.getString("$"+ paymentAmount));
            //monto.setText("$"+paymentAmount.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if(successResponse){
            if(DeliveryActivity.deliveryActivity != null){
                DeliveryActivity.deliveryActivity.finish();
                DeliveryActivity.deliveryActivity = null;
            }
            finish();
            return;
        }
        super.onBackPressed();
    }
}
