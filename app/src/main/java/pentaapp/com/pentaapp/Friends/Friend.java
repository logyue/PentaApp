package pentaapp.com.pentaapp.Friends;

import static android.R.attr.name;

/**
 * Created by Kevin on 10/16/2016.
 */

public class Friend {

    private String friendName;

    public Friend(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}

