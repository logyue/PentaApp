package pentaapp.com.pentaapp.Friends;

import android.view.View;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pentaapp.com.pentaapp.R;

/**
 * Created by Kevin on 4/30/2017.
 */

public class FriendInfoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friend_info_fragment, container, false);
        return rootView;
    }
}
