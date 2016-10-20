package info.zhiqing.schedule.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.zhiqing.schedule.MainActivity;
import info.zhiqing.schedule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    TextView infoNameTextView;
    TextView infoNumberTextView;
    TextView infoCollegeTextView;
    TextView infoMajorTextView;
    TextView infoClassTextView;
    TextView infoProspectTextView;
    TextView infoQualificationTextView;
    TextView infoMiddleSchoolTextView;
    TextView infoFromTextView;
    TextView infoNationTextView;
    TextView infoPoliticalTextView;



    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        infoNameTextView = (TextView)v.findViewById(R.id.infoNameTextView);
        infoNumberTextView = (TextView)v.findViewById(R.id.infoNumberTextView);
        infoCollegeTextView = (TextView)v.findViewById(R.id.infoCollegeTextView);
        infoMajorTextView = (TextView)v.findViewById(R.id.infoMajorTextView);
        infoClassTextView = (TextView)v.findViewById(R.id.infoClassTextView);
        infoFromTextView = (TextView)v.findViewById(R.id.infoFromTextView);
        infoProspectTextView = (TextView)v.findViewById(R.id.infoProspectTextView);
        infoPoliticalTextView = (TextView)v.findViewById(R.id.infoPoliticalTextView);
        infoQualificationTextView = (TextView)v.findViewById(R.id.infoQualificationTextView);
        infoNationTextView = (TextView)v.findViewById(R.id.infoNationTextView);
        infoMiddleSchoolTextView = (TextView)v.findViewById(R.id.infoMiddleSchoolTextView);

        update();

        return v;
    }

    void update(){
        SharedPreferences sp = getActivity().getSharedPreferences(MainActivity.SHARED_PREFER, Context.MODE_PRIVATE);
        infoNameTextView.setText(sp.getString(MainActivity.SHARED_PREFER_NAME, "姓名"));
        infoNumberTextView.setText(sp.getString(MainActivity.SHARED_PREFER_NUMBER, "学号"));
        infoCollegeTextView.setText(sp.getString(MainActivity.SHARED_PREFER_COLLEGE, "学号"));
        infoMajorTextView.setText(sp.getString(MainActivity.SHARED_PREFER_MAJOR, "学号"));
        infoClassTextView.setText(sp.getString(MainActivity.SHARED_PREFER_CLASS, "学号"));
        infoFromTextView.setText(sp.getString(MainActivity.SHARED_PREFER_FROM, "学号"));
        infoProspectTextView.setText(sp.getString(MainActivity.SHARED_PREFER_PROSPECT, "学号"));
        infoPoliticalTextView.setText(sp.getString(MainActivity.SHARED_PREFER_POLITICAL, "学号"));
        infoQualificationTextView.setText(sp.getString(MainActivity.SHARED_PREFER_QUALIFICATION, "学号"));
        infoNationTextView.setText(sp.getString(MainActivity.SHARED_PREFER_NATION, "学号"));
        infoMiddleSchoolTextView.setText(sp.getString(MainActivity.SHARED_PREFER_MIDDLE_SCHOOL, "学号"));
    }

}
