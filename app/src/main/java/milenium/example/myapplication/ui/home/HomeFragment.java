package milenium.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import milenium.example.myapplication.CategoriaAdaptador;
import milenium.example.myapplication.CategoriaModelo;
import milenium.example.myapplication.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    private RecyclerView recyclerViewCategoria;
    private CategoriaAdaptador categoriaAdaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_home,container,false);
            recyclerViewCategoria=view.findViewById(R.id.categoriasLista);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoria.setLayoutManager(layoutManager);

        List<CategoriaModelo> categoriaModelos= new ArrayList<CategoriaModelo>();
        categoriaModelos.add(new CategoriaModelo("link","Home"));
        categoriaModelos.add(new CategoriaModelo("link","Electronicos"));
        categoriaModelos.add(new CategoriaModelo("link","Moda"));
        categoriaModelos.add(new CategoriaModelo("link","Juguetes"));
        categoriaModelos.add(new CategoriaModelo("link","Sports"));
        categoriaModelos.add(new CategoriaModelo("link","Libros"));
        categoriaModelos.add(new CategoriaModelo("link","Zapatos"));
    categoriaAdaptador= new CategoriaAdaptador(categoriaModelos);
    recyclerViewCategoria.setAdapter(categoriaAdaptador);
    categoriaAdaptador.notifyDataSetChanged();
        return view;
    }
}
