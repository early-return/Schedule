package info.zhiqing.schedule.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.zhiqing.schedule.R;

public class ScoreListFragment extends Fragment {

    private RecyclerView recyclerView;

    public ScoreListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score_list, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.scoreListRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

}
