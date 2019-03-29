package com.klikeat.p2p.klikeat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.klikeat.p2p.klikeat.model.PopularModel;
import com.klikeat.p2p.klikeat.adapter.PopularAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    List<PopularModel> popularModels = new ArrayList<>();



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        popularModels.add(new PopularModel("Makaron Mini isi 5","Rp 14.000",R.drawable.analia_baggiano_776846_unsplash));
        popularModels.add(new PopularModel("Sambal Cumi","Rp 20.000",R.drawable.ella_olsson_1184054_unsplash));
        popularModels.add(new PopularModel("Sambal Cumi Besar","Rp 30.000",R.drawable.ernest_ojeh_1348807_unsplash));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rv_popular);
        PopularAdapter popularAdapter = new PopularAdapter(getActivity(), popularModels);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(popularAdapter);





    }
}
