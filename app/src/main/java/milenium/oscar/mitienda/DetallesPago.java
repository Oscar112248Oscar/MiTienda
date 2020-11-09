package milenium.oscar.mitienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallesPago extends AppCompatActivity {

    TextView id,estatus, monto;
    private Button continueShippingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pago);

        id = findViewById(R.id.idTransaccion);
        continueShippingBtn = findViewById(R.id.continueShippingBtn);
      //  estatus = findViewById(R.id.estatus);
    //    monto= findViewById(R.id.Monto);

        //Recibo los datos

        Intent intent= getIntent();

        try {
            JSONObject  jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));

            verDetalles(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(navegacionMenu.navegacionActivity != null){
            navegacionMenu.navegacionActivity.finish();
            navegacionMenu.navegacionActivity = null;
            navegacionMenu.showCart = false;
        }

        if(ProductDetailsActivity.productDetailsActivity != null){
            ProductDetailsActivity.productDetailsActivity.finish();
            ProductDetailsActivity.productDetailsActivity = null;
        }

        if(DeliveryActivity.deliveryActivity != null){
            DeliveryActivity.deliveryActivity.finish();
            DeliveryActivity.deliveryActivity = null;
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
            id.setText(response.getString("id"));
            //estatus.setText(response.getString("state"));
//monto.setText(response.getString("$"+ paymentAmount));
            //monto.setText("$"+paymentAmount.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
