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

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pentaapp.com.pentaapp.Friends.Friend;
import pentaapp.com.pentaapp.Friends.FriendProfileActivity;
import pentaapp.com.pentaapp.Friends.FriendsAdapter;
import pentaapp.com.pentaapp.R;

import static android.R.attr.name;
import static android.R.attr.start;
import static android.media.CamcorderProfile.get;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendsFragment extends Fragment {

    private DatabaseReference databaseRefer;
    private FirebaseDatabase mFirebaseInstance;
    FriendsAdapter adapter;
    ArrayList<Friend> friends = new ArrayList<Friend>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String currentUserID = user.getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_fragment, container, false);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        databaseRefer = mFirebaseInstance.getReference("Users");

        final ListView listView = (ListView) rootView.findViewById(R.id.friends_list);

        friends.clear();

        //Add friends
        databaseRefer.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // pendingCount is the count for dataSnapshot children
                long[] pendingCount = { dataSnapshot.getChildrenCount() };

                //parsing all the node list data from dataSnapshot
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    String userID = userSnapshot.getKey();
                    String username = userSnapshot.child("Name").getValue().toString();

                    if(!(userID.equals(currentUserID))){
                        friends.add(new Friend(username, userID));
                    }





                    // we loaded a child, check if we're done
                    pendingCount[0] = pendingCount[0] - 1;
                    if (pendingCount[0] == 0) {
                        adapter = new FriendsAdapter(getActivity(), friends);
                        listView.setAdapter(null);
                        listView.setAdapter(adapter);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                Friend friend = friends.get(position);
                String friendID = friend.getFriendID();

//                Intent friendProfile = new Intent(getActivity(), FriendProfileActivity.class);
//                friendProfile.putExtra("FRIEND_ID", friendID);
//                startActivity(friendProfile);
                startActivity(new Intent(getActivity(), FriendProfileActivity.class));

            }
        });

        return rootView;
    }
}
