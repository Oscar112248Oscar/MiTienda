package milenium.oscar.mitienda;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoriaAdaptador extends RecyclerView.Adapter<CategoriaAdaptador.CategoriaViewholder> {
private List<CategoriaModelo> listaCategoriaModelo;
private  int lastposition = -1;

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
        holder.setCategoria(nombre,position);
        holder.setCategoriaIcono(icono);

        if(lastposition < position){
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastposition = position;
        }


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

        private void setCategoriaIcono(String iconUrl){

            if (!iconUrl.equals("null")){
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.sinfondo)).into(iconoCategoria);

            }else {

                iconoCategoria.setImageResource(R.drawable.home);
            }

        }


        private void setCategoria(final String nombre, final int position){
            categoriaNombre.setText(nombre);
            if(!nombre.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("CategoryName", nombre);
                            itemView.getContext().startActivity(categoryIntent);
                        }


                    }
                });
            }

        }

    }




}
