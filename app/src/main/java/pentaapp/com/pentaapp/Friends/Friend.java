package pentaapp.com.pentaapp.Friends;

import static android.R.attr.name;

/**
 * Created by Kevin on 10/16/2016.
 */

public class Friend {

    private String friendName;
    private String friendID;
    private int friendAge;
    private int friendHeight;
    private int friendWeight;

    public Friend(String friendName, String friendID) {
        this.friendName = friendName;
        this.friendID = friendID;
    }

    public Friend(String friendName, String friendID, int friendAge, int friendHeight, int friendWeight) {
        this.friendName = friendName;
        this.friendID = friendID;
        this.friendAge = friendAge;
        this.friendHeight = friendHeight;
        this.friendWeight = friendWeight;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendID() {
        return friendID;
    }

    public int getFriendAge() {
        return friendAge;
    }

    public int getFriendHeight() {
        return friendHeight;
    }

    public int getFriendWeight() {
        return friendWeight;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public void setFriendAge(int friendAge) {
        this.friendAge = friendAge;
    }

    public void setFriendHeight(int friendHeight) {
        this.friendHeight = friendHeight;
    }

    public void setFriendWeight(int friendWeight) {
        this.friendWeight = friendWeight;
    }
}

