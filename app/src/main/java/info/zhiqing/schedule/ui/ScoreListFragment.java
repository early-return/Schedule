package info.zhiqing.schedule.ui;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import info.zhiqing.schedule.R;
import info.zhiqing.schedule.adapter.ScoreListAdapter;
import info.zhiqing.schedule.models.Database;
import info.zhiqing.schedule.util.ScheduleFetchr;

public class ScoreListFragment extends Fragment {
    private RecyclerView recyclerView;

    private ScoreListAdapter adapter = null;

    public ScoreListAdapter getAdapter() {
        return adapter;
    }

    public ScoreListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database db = new Database(getActivity());
        adapter = new ScoreListAdapter(getActivity(), db.getScores());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score_list, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.scoreListRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        return v;
    }

}
