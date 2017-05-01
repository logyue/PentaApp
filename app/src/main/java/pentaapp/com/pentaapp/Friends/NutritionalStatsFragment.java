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

import static pentaapp.com.pentaapp.R.id.chart;


/**
 * Created by Kevin on 4/30/2017.
 */

public class NutritionalStatsFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();

    private float myNutritionalStats[] = new float[5];
    private float friendNutritionalStats[] = new float[5];
    String nutriNames[] = {"Pro", "Vit", "Wtr", "Crb", "Fat"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friend_stats_comparison_fragment, container, false);

        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mNutritionalStatsRef = mDatabase.child("Nutritional Stats");

        mNutritionalStatsRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myNutritionalStats[0]=Float.parseFloat(dataSnapshot.child("Pro").getValue().toString());
                myNutritionalStats[1]=Float.parseFloat(dataSnapshot.child("Vit").getValue().toString());
                myNutritionalStats[2]=Float.parseFloat(dataSnapshot.child("Wtr").getValue().toString());
                myNutritionalStats[3]=Float.parseFloat(dataSnapshot.child("Crb").getValue().toString());
                myNutritionalStats[4]=Float.parseFloat(dataSnapshot.child("Fat").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //get reference to friend's physical stats
        String friendUserId = getActivity().getIntent().getStringExtra("FRIEND_ID");

        mNutritionalStatsRef.child(friendUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendNutritionalStats[0]=Float.parseFloat(dataSnapshot.child("Pro").getValue().toString());
                friendNutritionalStats[1]=Float.parseFloat(dataSnapshot.child("Vit").getValue().toString());
                friendNutritionalStats[2]=Float.parseFloat(dataSnapshot.child("Wtr").getValue().toString());
                friendNutritionalStats[3]=Float.parseFloat(dataSnapshot.child("Crb").getValue().toString());
                friendNutritionalStats[4]=Float.parseFloat(dataSnapshot.child("Fat").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        List<RadarEntry> nutritionalEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<myNutritionalStats.length; i++){
            nutritionalEntries.add(new RadarEntry(myNutritionalStats[i]));
        }

        List<RadarEntry> friendEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<friendNutritionalStats.length; i++){
            friendEntries.add(new RadarEntry(friendNutritionalStats[i]));
        }


        RadarDataSet dataSet1 = new RadarDataSet(nutritionalEntries, "My Nutritional Stats");
        dataSet1.setLineWidth(1f);
        dataSet1.setColor(Color.CYAN);
        dataSet1.setFillColor(Color.CYAN);
        dataSet1.setDrawFilled(true);

        RadarDataSet dataSet2 = new RadarDataSet(friendEntries, "Friend's Nutritional Stats");
        dataSet2.setLineWidth(1f);
        dataSet2.setColor(Color.RED);
        dataSet2.setFillColor(Color.RED);
        dataSet2.setDrawFilled(true);

        List<IRadarDataSet> dataSets = new ArrayList<IRadarDataSet>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);

        RadarData data2 = new RadarData(dataSets);

        //Get the chart
        RadarChart chart2 = (RadarChart) rootView.findViewById(R.id.friends_stats_comparison_chart);

        chart2.setData(data2);
        //chart2.setBackgroundColor(getResources().getColor(R.color.chartBackgroundColor));
        chart2.setRotationEnabled(false);
        chart2.getYAxis().setDrawLabels(false);
        chart2.getYAxis().setAxisMinimum(0f);
        chart2.getYAxis().setAxisMaximum(100f);
        chart2.animateXY(500,500);
        chart2.getLegend().setTextColor(Color.WHITE);
        chart2.getLegend().setTextSize(14f);
        chart2.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        Description desc = new Description();
        desc.setText("");
        chart2.setDescription(desc);

        //Set value labels
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nutriNames));
        chart2.getXAxis().setTextColor(Color.WHITE);
        chart2.getXAxis().setTextSize(18f);


        chart2.invalidate();
    }
}
