//
// * Copyright (C) 2016 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
//

package pentaapp.com.pentaapp.Fragments;

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

import pentaapp.com.pentaapp.Profile.User;
import pentaapp.com.pentaapp.R;

import static android.R.attr.entries;
import static pentaapp.com.pentaapp.R.id.chart2;

/**
 * Fragment that displays "Monday".
 */
public class AvatarFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();



    private float[] physicalStats = new float[5];
    private float nutritionalStats[] = new float[5];
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};
    String nutriNames[] = {"Pro", "Vit", "Wtr", "Crb", "Fat"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.avatar_fragment, container, false);

        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mPhysicalStatsRef = mDatabase.child("Physical Stats").child(userID);
        DatabaseReference mNutritionalStatsRef = mDatabase.child("Nutritional Stats").child(userID);

        mPhysicalStatsRef.addValueEventListener(new ValueEventListener() {
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

        List<RadarEntry> physicalEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<physicalStats.length; i++){
            physicalEntries.add(new RadarEntry(physicalStats[i]));
        }

        List<RadarEntry> nutritionalEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<nutritionalStats.length; i++){
            nutritionalEntries.add(new RadarEntry(nutritionalStats[i]));
        }

        RadarDataSet dataSet = new RadarDataSet(physicalEntries, "My Physical Stats");
        dataSet.setLineWidth(1f);
        dataSet.setColor(Color.CYAN);
        dataSet.setFillColor(Color.CYAN);
        dataSet.setDrawFilled(true);

        RadarDataSet dataSet2 = new RadarDataSet(nutritionalEntries, "Friend's Physical Stats");
        dataSet2.setLineWidth(1f);
        dataSet2.setColor(Color.RED);
        dataSet2.setFillColor(Color.RED);
        dataSet2.setDrawFilled(true);



        List<IRadarDataSet> dataSets = new ArrayList<IRadarDataSet>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);

        RadarData data = new RadarData(dataSet);

        RadarData data2 = new RadarData(dataSet2);

        //Get the chart
        RadarChart chart = (RadarChart) rootView.findViewById(R.id.chart);

        chart.setData(data);
        //chart.setBackgroundColor(getResources().getColor(R.color.BackgroundColor));
        chart.setRotationEnabled(false);
        chart.getYAxis().setDrawLabels(false);
        chart.getYAxis().setAxisMinimum(0f);
        chart.getYAxis().setAxisMaximum(100f);
        chart.animateXY(500, 500);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));
        chart.getXAxis().setTextColor(Color.WHITE);

        Description desc = new Description();
        desc.setText("");
        chart.setDescription(desc);
        chart.invalidate();

        //Get the chart
        RadarChart chart2 = (RadarChart) rootView.findViewById(R.id.chart2);

        chart2.setData(data2);
        //chart2.setBackgroundColor(getResources().getColor(R.color.chartBackgroundColor));
        chart2.setRotationEnabled(false);
        chart2.getYAxis().setDrawLabels(false);
        chart2.getYAxis().setAxisMinimum(0f);
        chart2.getYAxis().setAxisMaximum(100f);
        chart2.animateXY(500,500);
        chart2.getLegend().setEnabled(false);
        chart2.setDescription(desc);

        //Set value labels
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nutriNames));
        chart2.getXAxis().setTextColor(Color.WHITE);
        chart2.invalidate();


    }

}
