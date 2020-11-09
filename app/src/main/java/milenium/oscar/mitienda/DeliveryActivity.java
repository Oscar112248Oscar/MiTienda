package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import milenium.oscar.mitienda.Config.Config;

public class DeliveryActivity extends AppCompatActivity {

    public static List<CartItemModel> cartItemModelList;
    private RecyclerView deliveryRecyclerView;
    private Button changeORaddNewAdrressBtn;
    public  static  final int SELECT_ADDRESS=0;
    private TextView totalAmount;
    private TextView fullname;
    private TextView fullAddress;
    private TextView pincode;
    private Button continueBtn;
    private  Dialog loadingDialog;
    private  Dialog paymentMethodDialog;
    private ImageView paytm;

    public static Activity deliveryActivity;

    ///PAYPAL
    private  static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(Config.PAYPAL_CLIENT_ID);//Esta es para usar la cuenta de Prueba

    ///PAYPAL


    @Override
    protected void onDestroy() {
        stopService( new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar  = findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setDisplayShowTitleEnabled(true);
           getSupportActionBar().setTitle("Entrega");

           ///INICIAR SERVICIO DE PAYPAL

        Intent intent = new Intent(DeliveryActivity.this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);


        deliveryRecyclerView= findViewById(R.id.delivery_reciclerview);
        changeORaddNewAdrressBtn= findViewById(R.id.change_or_add_address_btn);
        totalAmount = findViewById(R.id.total_cart_amount1);
        fullname = findViewById(R.id.fullname);
        fullAddress = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        continueBtn= findViewById(R.id.cart_continue_btn);


        //// loading dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //// loading dialog

        //// loading Paymentdialog
        paymentMethodDialog = new Dialog(DeliveryActivity.this);
        paymentMethodDialog.setContentView(R.layout.payment_method);
        paymentMethodDialog.setCancelable(true);
        paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paytm= paymentMethodDialog.findViewById(R.id.paymt);

        //// loading Paymentdialog


        LinearLayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);



       /* cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",2,"14$","18$",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",0,"14$","18$",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",2,"14$","18$",1,2,0));*/
       // cartItemModelList.add(new CartItemModel(1,"Precio (3 items)","20$","Gratis","20$","5$"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();


        changeORaddNewAdrressBtn.setVisibility(View.VISIBLE);

        changeORaddNewAdrressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.show();

            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDialog.dismiss();
                loadingDialog.show();

                String M_id="ARAu4Azp93pIgWj5wVx5W2ceaoDS8n7RMh68iCeWv45G7sDIXbainapZn95s1BVbzE89lWDhoJJ6Bp4s";
                String customer_id = FirebaseAuth.getInstance().getUid();
                String order_id = UUID.randomUUID().toString().substring(0,28);

                procesarPago();

            }
        });

       /* if(navegacionMenu.navegacionActivity != null){
            navegacionMenu.navegacionActivity.finish();
            navegacionMenu.navegacionActivity = null;
            navegacionMenu.showCart = false;
        }

        if(ProductDetailsActivity.productDetailsActivity != null){
            ProductDetailsActivity.productDetailsActivity.finish();
            ProductDetailsActivity.productDetailsActivity = null;
        }*/



            }


    @Override
    protected void onStart() {
        super.onStart();
        fullname.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullname());
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        if(id == android.R.id.home){
            finish();
            return  true;

        }

        return super.onOptionsItemSelected(item);
    }


    String monto ="";
    private void procesarPago(){
        deliveryActivity = this;
        loadingDialog.dismiss();
        // recibo el monto
        monto = totalAmount.getText().toString().substring(1,totalAmount.length());

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(monto)),"USD","PAGADO",PayPalPayment.PAYMENT_INTENT_SALE);


        // ENVIO LOS PARAMETROS QUE NECESITA PAYPAL

        Intent intent = new Intent(DeliveryActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);


                if(paymentConfirmation != null){

                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString(4);

                            startActivity(new Intent(this,DetallesPago.class).putExtra("PaymentDetails",paymentDetails).putExtra("PaymentAmount",monto));
                    }catch (JSONException e){
                        e.printStackTrace();

                    }

                }
            }else if(requestCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,"Transaccion Cancelada",Toast.LENGTH_SHORT).show();
            }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this,"Transaccion Invalida",Toast.LENGTH_SHORT).show();

            }


        }


    }





}
