package pentaapp.com.pentaapp.Friends;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;


import pentaapp.com.pentaapp.R;

import static android.R.attr.data;

public class FriendProfileActivity extends AppCompatActivity {

    float stats[] = {30.0f, 90.3f, 80.0f, 70.0f, 70.0f};
    float friendStats[] = {50.0f, 80.3f, 30.0f, 50.0f, 30.0f};
    String statNames[] = {"Str", "StrE", "Stm", "Spd", "Flx"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        setUpRadarChart();
    }

    private void setUpRadarChart() {
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


        RadarData data = new RadarData(dataSets);


        //Get the chart
        RadarChart chart = (RadarChart) findViewById(R.id.chart);

        chart.setData(data);
        //chart.setBackgroundColor(getResources().getColor(R.color.BackgroundColor));
        chart.setRotationEnabled(false);
        chart.getYAxis().setDrawLabels(false);
        chart.getYAxis().setAxisMinimum(0f);
        chart.animateXY(1000, 1000);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));

        Description desc = new Description();
        desc.setText("Workout");
        chart.setDescription(desc);
        chart.invalidate();

        //Get the chart
        RadarChart chart2 = (RadarChart) findViewById(R.id.chart2);
        chart2.setData(data);
        //chart2.setBackgroundColor(getResources().getColor(R.color.chartBackgroundColor));
        chart2.setRotationEnabled(false);
        chart2.getYAxis().setDrawLabels(false);
        chart2.getYAxis().setAxisMinimum(0f);
        chart2.animateXY(1000,1000);
        chart2.getLegend().setEnabled(false);

        //Set value labels
        chart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statNames));
        chart2.invalidate();


    }
}
