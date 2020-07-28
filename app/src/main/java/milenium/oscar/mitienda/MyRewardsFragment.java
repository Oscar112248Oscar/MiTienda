package milenium.oscar.mitienda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardsFragment extends Fragment {

    public MyRewardsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=   inflater.inflate(R.layout.fragment_my_rewards, container, false);

        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Descuento","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Compre 1 reciba 1","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("CashBack","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Descuento","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Compre 1 reciba 1","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("CashBack","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Descuento","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Compre 1 reciba 1","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));


        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList);
        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();
        return view;

    }
}
