package milenium.oscar.mitienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallesPago extends AppCompatActivity {

    TextView id,estatus, monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pago);

        id = findViewById(R.id.idTransaccion);
        estatus = findViewById(R.id.estatus);
        monto= findViewById(R.id.Monto);

        //Recibo los datos

        Intent intent= getIntent();

        try {
            JSONObject  jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));

            verDetalles(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void verDetalles(JSONObject response, String paymentAmount) {

        try {
            id.setText(response.getString("id"));
            estatus.setText(response.getString("state"));
            //monto.setText(response.getString("$"+ paymentAmount));
            monto.setText("$"+paymentAmount.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
