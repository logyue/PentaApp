package pentaapp.com.pentaapp.Friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;

import pentaapp.com.pentaapp.Profile.User;
import pentaapp.com.pentaapp.R;

import static android.R.attr.entries;
import static pentaapp.com.pentaapp.R.id.chart2;


/**
 * Created by Kevin on 4/30/2017.
 */

public class PhysicalStatsFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();


    private float[] physicalStats = new float[5];
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.physical_stats_fragment, container, false);

        DatabaseReference databaseRefer;
        FirebaseDatabase mFirebaseInstance;

        mFirebaseInstance = FirebaseDatabase.getInstance();

        databaseRefer = mFirebaseInstance.getReference("Physical Stats").child(userID);

        databaseRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                physicalStats[0]=Float.parseFloat(dataSnapshot.child("Str").getValue().toString());
                physicalStats[1]=Float.parseFloat(dataSnapshot.child("StrE").getValue().toString());
                physicalStats[2]=Float.parseFloat(dataSnapshot.child("Stm").getValue().toString());
                physicalStats[3]=Float.parseFloat(dataSnapshot.child("Spd").getValue().toString());
                physicalStats[4]=Float.parseFloat(dataSnapshot.child("Flx").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {

        //getPhysicalStats(physicalStats);


        List<RadarEntry> physicalEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<physicalStats.length; i++){
            physicalEntries.add(new RadarEntry(physicalStats[i]));
        }

        RadarDataSet dataSet = new RadarDataSet(physicalEntries, "My Physical Stats");
        dataSet.setLineWidth(1f);
        dataSet.setColor(Color.CYAN);
        dataSet.setFillColor(Color.CYAN);
        dataSet.setDrawFilled(true);

        RadarData data = new RadarData(dataSet);

        //Get the chart
        RadarChart chart = (RadarChart) rootView.findViewById(R.id.friends_physical_stats_comparison_chart);

        chart.setData(data);
        chart.setRotationEnabled(false);
        chart.getYAxis().setDrawLabels(false);
        chart.getYAxis().setAxisMinimum(0f);
        chart.getYAxis().setAxisMaximum(100f);
        chart.animateXY(1000, 1000);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));
        chart.getXAxis().setTextColor(Color.WHITE);

        Description desc = new Description();
        desc.setText("");
        chart.setDescription(desc);

        chart.invalidate();



    }

    public void getPhysicalStats(final float[] physicalStats){



    }

}
