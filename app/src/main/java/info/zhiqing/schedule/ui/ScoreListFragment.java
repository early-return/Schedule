package info.zhiqing.schedule.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import info.zhiqing.schedule.R;
import info.zhiqing.schedule.adapter.ScoreListAdapter;
import info.zhiqing.schedule.models.Database;

public class ScoreListFragment extends Fragment {
    private static final String TAG = "ScoreListFragment";

    private RecyclerView recyclerView;
    private Spinner yearSpinner;
    private Spinner semesterSpinner;

    private ScoreListAdapter adapter = null;
    private String[] years = null;
    private String[] semesters = null;

    private String currentYear = "*";
    private String currentSemester = "*";

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
        adapter = new ScoreListAdapter(getActivity());

        years = db.getYears();

        List<String> yearList = new ArrayList<>();
        yearList.add("*");

        for(String str : years){
            yearList.add(str);
        }

        years = yearList.toArray(new String[0]);

        semesters = getActivity().getResources().getStringArray(R.array.semesters);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score_list, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.scoreListRecyclerView);
        yearSpinner = (Spinner) v.findViewById(R.id.yearSpinner);
        semesterSpinner = (Spinner) v.findViewById(R.id.semesterSpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        yearSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, years));

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentYear = years[position];
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSemester = semesters[position];
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    void updateAdapter(){
        adapter.updateData(currentYear, currentSemester);
    }

}
