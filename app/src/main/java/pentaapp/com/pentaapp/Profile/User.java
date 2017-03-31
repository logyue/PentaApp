package pentaapp.com.pentaapp.Profile;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by jili on 17-03-31.
 */

public class User {
    public String   username;
    public String gender;
    private DatabaseReference mDatabase;

    public String getUsername(){
        return  username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public User(){

    }
    public User(String username, String gender) {
        this.username = username;
        this.gender = gender;
    }
    public void writeNewUser(String userId, String name, String gender) {
        mDatabase.child("Users").child(userId).child("Name").setValue(name);
        mDatabase.child("Users").child(userId).child("Gender").setValue(gender);

    }

}
