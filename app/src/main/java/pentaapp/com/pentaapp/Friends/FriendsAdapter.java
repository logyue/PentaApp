package pentaapp.com.pentaapp.Friends;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pentaapp.com.pentaapp.R;

/**
 * Created by Kevin on 3/13/2017.
 */

public class FriendsAdapter extends ArrayAdapter<Friend> {

    public FriendsAdapter(Activity context, List<Friend> Friends) {
        super(context, 0, Friends);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the Friend object from the ArrayAdapter at the appropriate position
        Friend currentFriend = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_list_item, parent, false);
        }

        //sets initial of the friend name in the profile_placeholder TextView
        String friendInitial = "" + currentFriend.getFriendName().charAt(0);
        TextView profilePicturePlaceholderTextView = (TextView) convertView.findViewById(R.id.friend_initial);
        profilePicturePlaceholderTextView.setText(friendInitial);

        //sets the friend name in the friend_name TextView
        TextView friendNameTextView = (TextView) convertView.findViewById(R.id.friend_name);
        friendNameTextView.setText(currentFriend.getFriendName());

        return convertView;
    }
}
