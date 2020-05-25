package milenium.example.myapplication;

import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriaAdaptador extends RecyclerView.Adapter<CategoriaAdaptador.CategoriaViewholder> {
private List<CategoriaModelo> listaCategoriaModelo;

    public CategoriaAdaptador(List<CategoriaModelo> listaCategoriaModelo) {
        this.listaCategoriaModelo = listaCategoriaModelo;
    }

    @NonNull
    @Override
    public CategoriaViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.listaitems,parent,false);
        return new  CategoriaViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewholder holder, int position) {

String icono=listaCategoriaModelo.get(position).getIconoCategoriaLink();
        String nombre=listaCategoriaModelo.get(position).getCategoriaNombre();
        holder.setCategoriaIconoNombre(nombre);
        holder.setCategoriaIcono(icono);


    }

    @Override
    public int getItemCount() {
        return listaCategoriaModelo.size();
    }

    public class CategoriaViewholder extends RecyclerView.ViewHolder   {
        private ImageView iconoCategoria;
        private TextView categoriaNombre;

        public CategoriaViewholder(@NonNull View itemView) {
            super(itemView);
            iconoCategoria=itemView.findViewById(R.id.categoria_icono);
            categoriaNombre=itemView.findViewById(R.id.categoria_nombre);
        }

        private void setCategoriaIcono(String nombre){


        }


        private void setCategoriaIconoNombre(String nombre){
            categoriaNombre.setText(nombre);

        }

    }




}
