/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pentaapp.com.pentaapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

import pentaapp.com.pentaapp.R;

import static pentaapp.com.pentaapp.R.id.chart;

/**
 * Fragment that displays "Monday".
 */
public class HomeFragment extends Fragment {

    float stats[] = {70.0f, 90.3f, 80.0f, 70.0f, 70.0f};
    float friendStats[] = {90.0f, 80.3f, 60.0f, 50.0f, 70.0f};
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        setUpRadarChart(rootView);

        return rootView;
    }

    private void setUpRadarChart(View rootView) {
        //populate list with Data
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
        chart.animateXY(1000, 1000);
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
        chart2.animateXY(1000,1000);
        chart2.getLegend().setEnabled(false);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart2.setDescription(desc);

        //Set value labels
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));
        chart2.getXAxis().setTextColor(Color.WHITE);
        chart2.invalidate();


    }
}
