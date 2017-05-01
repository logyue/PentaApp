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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pentaapp.com.pentaapp.R;
import pentaapp.com.pentaapp.Registration.LoginActivity;

/**
 * Fragment that displays "Tuesday".
 */
public class GamesFragment extends Fragment {

    private EditText  pullup;
    private EditText  pushup;
    private EditText squat;
    private EditText sprint;
    private EditText stretch;

    private Button btnAdd;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseDatabase firebaseDatabase;


    DatabaseReference mPs= FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.games_fragment, container, false);
        pullup = (EditText) rootView.findViewById(R.id.textPullup);
        pushup = (EditText) rootView.findViewById(R.id.textPushup);
        squat = (EditText) rootView.findViewById(R.id.textSquat);
        sprint = (EditText) rootView.findViewById(R.id.textSprint);
        stretch = (EditText) rootView.findViewById(R.id.textStretch);
        btnAdd = (Button) rootView.findViewById(R.id.buttonAdd);
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (user != null) {

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pullup.getText().toString().equals("")) {
                        pullup.setText("0");
                    }
                    if (pushup.getText().toString().equals("")) {
                        pushup.setText("0");
                    }
                    if (squat.getText().toString().equals("")) {
                        squat.setText("0");
                    }
                    if (sprint.getText().toString().equals("")) {
                        sprint.setText("0");
                    }
                    if (stretch.getText().toString().equals("")) {
                        stretch.setText("0");
                    }

                    mPs.addListenerForSingleValueEvent(new ValueEventListener() {//Retrieve Data from Firebase(Name Gender)
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String userID = user.getUid();
                            float str = Float.parseFloat(dataSnapshot.child("Physical Stats").child(userID).child("Str").getValue().toString());
                            float strE = Float.parseFloat(dataSnapshot.child("Physical Stats").child(userID).child("StrE").getValue().toString());
                            float stm = Float.parseFloat(dataSnapshot.child("Physical Stats").child(userID).child("Stm").getValue().toString());
                            float spd = Float.parseFloat(dataSnapshot.child("Physical Stats").child(userID).child("Spd").getValue().toString());
                            float flx = Float.parseFloat(dataSnapshot.child("Physical Stats").child(userID).child("Flx").getValue().toString());
                            str = str + Float.parseFloat(pushup.getText().toString());
                            strE = strE + Float.parseFloat(pullup.getText().toString());
                            stm = stm + Float.parseFloat(squat.getText().toString());
                            spd = spd + Float.parseFloat(sprint.getText().toString());
                            flx = flx + Float.parseFloat(stretch.getText().toString());
                            DatabaseReference mPhysicalStats = firebaseDatabase.getReference().child("Physical Stats").child(user.getUid());
                            mPhysicalStats.child("Str").setValue(str);
                            mPhysicalStats.child("StrE").setValue(strE);
                            mPhysicalStats.child("Stm").setValue(stm);
                            mPhysicalStats.child("Spd").setValue(spd);
                            mPhysicalStats.child("Flx").setValue(flx);
                            Toast.makeText(getContext(), "Update complete!", Toast.LENGTH_SHORT).show();
                            pullup.setText(null);
                            pushup.setText(null);
                            squat.setText(null);
                            sprint.setText(null);
                            stretch.setText(null);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        return rootView;
    }
}
