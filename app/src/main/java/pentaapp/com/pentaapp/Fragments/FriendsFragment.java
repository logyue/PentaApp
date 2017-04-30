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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pentaapp.com.pentaapp.EmptyActivity;
import pentaapp.com.pentaapp.Friends.Friend;
import pentaapp.com.pentaapp.Friends.FriendProfileActivity;
import pentaapp.com.pentaapp.Friends.FriendsAdapter;
import pentaapp.com.pentaapp.R;
import pentaapp.com.pentaapp.Registration.LoginActivity;

import static android.media.CamcorderProfile.get;

public class FriendsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_fragment, container, false);

        //Create an array of words
        final ArrayList<Friend> friends = new ArrayList<Friend>();

        //Add wordsf
        friends.add(new Friend("Jili Chen"));
        friends.add(new Friend("Jeffrey Ma"));
        friends.add(new Friend("Kevin Chie"));

        FriendsAdapter adapter = new FriendsAdapter(getActivity(), friends);

        ListView listView = (ListView) rootView.findViewById(R.id.friends_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                startActivity(new Intent(view.getContext(), FriendProfileActivity.class));
            }
        });

        return rootView;
    }
}
