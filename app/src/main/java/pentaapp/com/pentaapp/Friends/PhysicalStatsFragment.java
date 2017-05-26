package pentaapp.com.pentaapp.Friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;

import pentaapp.com.pentaapp.R;

import static pentaapp.com.pentaapp.R.id.chart2;


/**
 * Created by Kevin on 4/30/2017.
 */

public class PhysicalStatsFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();



    private float[] myPhysicalStats = new float[5];
    private float[] friendPhysicalStats = new float[5];
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friend_stats_comparison_fragment, container, false);

        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {
        //get reference to firebase database
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        //get reference to "Physical Stats" node in database
        DatabaseReference mPhysicalStatsRef = mDatabase.child("Physical Stats");

        mPhysicalStatsRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myPhysicalStats[0]=Float.parseFloat(dataSnapshot.child("Str").getValue().toString());
                myPhysicalStats[1]=Float.parseFloat(dataSnapshot.child("StrE").getValue().toString());
                myPhysicalStats[2]=Float.parseFloat(dataSnapshot.child("Stm").getValue().toString());
                myPhysicalStats[3]=Float.parseFloat(dataSnapshot.child("Spd").getValue().toString());
                myPhysicalStats[4]=Float.parseFloat(dataSnapshot.child("Flx").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //get reference to friend's physical stats
        String friendUserId = getActivity().getIntent().getStringExtra("FRIEND_ID");

        mPhysicalStatsRef.child(friendUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendPhysicalStats[0]=Float.parseFloat(dataSnapshot.child("Str").getValue().toString());
                friendPhysicalStats[1]=Float.parseFloat(dataSnapshot.child("StrE").getValue().toString());
                friendPhysicalStats[2]=Float.parseFloat(dataSnapshot.child("Stm").getValue().toString());
                friendPhysicalStats[3]=Float.parseFloat(dataSnapshot.child("Spd").getValue().toString());
                friendPhysicalStats[4]=Float.parseFloat(dataSnapshot.child("Flx").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        List<RadarEntry> physicalEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<myPhysicalStats.length; i++){
            physicalEntries.add(new RadarEntry(myPhysicalStats[i]));
        }

        List<RadarEntry> friendEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<friendPhysicalStats.length; i++){
            friendEntries.add(new RadarEntry(friendPhysicalStats[i]));
        }

        RadarDataSet dataSet1 = new RadarDataSet(physicalEntries, "My Physical Stats");
        dataSet1.setLineWidth(1f);
        dataSet1.setColor(Color.CYAN);
        dataSet1.setFillColor(Color.CYAN);
        dataSet1.setDrawFilled(true);

        RadarDataSet dataSet2 = new RadarDataSet(friendEntries, "Friend's Physical Stats");
        dataSet2.setLineWidth(1f);
        dataSet2.setColor(Color.RED);
        dataSet2.setFillColor(Color.RED);
        dataSet2.setDrawFilled(true);

        List<IRadarDataSet> dataSets = new ArrayList<IRadarDataSet>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);

        RadarData data = new RadarData(dataSets);

        //Get the chart
        RadarChart chart = (RadarChart) rootView.findViewById(R.id.friends_stats_comparison_chart);

        chart.setData(data);
        chart.setRotationEnabled(false);
        chart.getYAxis().setDrawLabels(false);
        chart.getYAxis().setAxisMinimum(0f);
        chart.getYAxis().setAxisMaximum(100f);
        chart.animateXY(500, 500);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.getLegend().setTextSize(14f);
        chart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setTextSize(18f);

        Description desc = new Description();
        desc.setText("");
        chart.setDescription(desc);

        chart.invalidate();
    }
}
