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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import pentaapp.com.pentaapp.Profile.User;
import pentaapp.com.pentaapp.R;

<<<<<<< HEAD:app/src/main/java/pentaapp/com/pentaapp/Fragments/AvatarFragment.java
/**
 * Fragment that displays "Monday".
 */
public class AvatarFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();
    DatabaseReference mPhysicalStatsRef = mDatabase.child("Physical Stats").child(userID);

    float stats[];
    float friendStats[] = {90.0f, 80.3f, 60.0f, 50.0f, 70.0f};
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};
    String nutriNames[] = {"Pro", "Vit", "Wtr", "Crb", "Fat"};




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.avatar_fragment, container, false);

        mPhysicalStatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float Str = Float.parseFloat(dataSnapshot.child("Str").toString());
                float StrE = Float.parseFloat(dataSnapshot.child("StrE").toString());
                float Stm = Float.parseFloat(dataSnapshot.child("Stm").toString());
                float Spd = Float.parseFloat(dataSnapshot.child("Spd").toString());
                float Flx = Float.parseFloat(dataSnapshot.child("Flx").toString());
                stats = new float[]{Str, StrE, Stm, Spd, Flx};
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

=======


//*
// * Fragment that displays "Monday".


public class HomeFragment extends Fragment {


    User user=new User();
    float stats[]=user.getStats();
    float friendStats[] = {10.0f, 10.3f, 10.0f, 90.0f, 0.0f};
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};
    String nutriStats[] = {"Car", "Pro", "Wtr", "Min", "Fat"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        //populate list with Data
>>>>>>> refs/remotes/origin/Jili:app/src/main/java/pentaapp/com/pentaapp/Fragments/HomeFragment.java
        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {

        List<RadarEntry> entries = new ArrayList<RadarEntry>();

        for(int i=0; i<stats.length; i++){
            entries.add(new RadarEntry(stats[i]));
        }

        List<RadarEntry> friendEntries = new ArrayList<RadarEntry>();
        for(int i=0; i<friendStats.length; i++){
            friendEntries.add(new RadarEntry(friendStats[i]));
        }


        RadarDataSet dataSet = new RadarDataSet(entries, "My Physical Stats");
        dataSet.setLineWidth(1f);
        dataSet.setColor(Color.CYAN);
        dataSet.setFillColor(Color.CYAN);
        dataSet.setDrawFilled(true);


        RadarDataSet dataSet2 = new RadarDataSet(friendEntries, "Friend's Physical Stats");
        dataSet2.setLineWidth(1);
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
        chart.animateXY(1000, 1000);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));
        chart.getXAxis().setTextColor(Color.BLACK);

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
        chart2.animateXY(1000,1000);
        chart2.getLegend().setEnabled(false);
        chart2.setDescription(desc);

        //Set value labels
<<<<<<< HEAD:app/src/main/java/pentaapp/com/pentaapp/Fragments/AvatarFragment.java
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nutriNames));
        chart2.getXAxis().setTextColor(Color.BLACK);
=======
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nutriStats));
        chart2.getXAxis().setTextColor(Color.WHITE);
>>>>>>> refs/remotes/origin/Jili:app/src/main/java/pentaapp/com/pentaapp/Fragments/HomeFragment.java
        chart2.invalidate();


    }
}
