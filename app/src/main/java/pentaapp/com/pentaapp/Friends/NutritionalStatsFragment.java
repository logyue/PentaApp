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



/**
 * Created by Kevin on 4/30/2017.
 */

public class NutritionalStatsFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();

    DatabaseReference mNutritionalStatsRef = mDatabase.child("Nutritional Stats").child(userID);

    private float nutritionalStats[] = new float[5];
    String nutriNames[] = {"Pro", "Vit", "Wtr", "Crb", "Fat"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nutritional_stats_fragment, container, false);

        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {

        mNutritionalStatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nutritionalStats[0]=Float.parseFloat(dataSnapshot.child("Pro").getValue().toString());
                nutritionalStats[1]=Float.parseFloat(dataSnapshot.child("Vit").getValue().toString());
                nutritionalStats[2]=Float.parseFloat(dataSnapshot.child("Wtr").getValue().toString());
                nutritionalStats[3]=Float.parseFloat(dataSnapshot.child("Crb").getValue().toString());
                nutritionalStats[4]=Float.parseFloat(dataSnapshot.child("Fat").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        List<RadarEntry> nutritionalEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<nutritionalStats.length; i++){
            nutritionalEntries.add(new RadarEntry(nutritionalStats[i]));
        }


        RadarDataSet dataSet2 = new RadarDataSet(nutritionalEntries, "Friend's Physical Stats");
        dataSet2.setLineWidth(1);
        dataSet2.setColor(Color.CYAN);
        dataSet2.setFillColor(Color.CYAN);
        dataSet2.setDrawFilled(true);



        List<IRadarDataSet> dataSets = new ArrayList<IRadarDataSet>();
        dataSets.add(dataSet2);


        RadarData data2 = new RadarData(dataSet2);

        //Get the chart
        RadarChart chart2 = (RadarChart) rootView.findViewById(R.id.friends_nutritional_stats_comparison_chart);

        chart2.setData(data2);
        //chart2.setBackgroundColor(getResources().getColor(R.color.chartBackgroundColor));
        chart2.setRotationEnabled(false);
        chart2.getYAxis().setDrawLabels(false);
        chart2.getYAxis().setAxisMinimum(0f);
        chart2.getYAxis().setAxisMaximum(100f);
        chart2.animateXY(1000,1000);
        chart2.getLegend().setEnabled(false);

        Description desc = new Description();
        desc.setText("");
        chart2.setDescription(desc);

        //Set value labels
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nutriNames));
        chart2.getXAxis().setTextColor(Color.WHITE);
        chart2.invalidate();
    }


    public void getNutritionalStats(final float[] physicalStats){

        mNutritionalStatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nutritionalStats[0]=Float.parseFloat(dataSnapshot.child("Pro").getValue().toString());
                nutritionalStats[1]=Float.parseFloat(dataSnapshot.child("Vit").getValue().toString());
                nutritionalStats[2]=Float.parseFloat(dataSnapshot.child("Wtr").getValue().toString());
                nutritionalStats[3]=Float.parseFloat(dataSnapshot.child("Crb").getValue().toString());
                nutritionalStats[4]=Float.parseFloat(dataSnapshot.child("Fat").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
