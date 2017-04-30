package pentaapp.com.pentaapp.Profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jili on 17-03-31.
 */

public class User {
    public String   username;
    public String gender;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    float stats[]=new float[5];
    float str,strE,stm,spd,flx=0;
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
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        //getStats();
    }
    public User(String username, String gender) {
        this.username = username;
        this.gender = gender;
    }
    public void writeNewUser(String userId, String name, String gender) {
        databaseReference=firebaseDatabase.getReference().child("Users");
        databaseReference.child("Users").child(userId).child("Name").setValue(name);
        databaseReference.child("Users").child(userId).child("Gender").setValue(gender);

    }

    public float[] getStats(){
        String uid=firebaseAuth.getCurrentUser().getUid();
        databaseReference=firebaseDatabase.getReference().child("Physical Stats").child(uid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stats[0]=Float.parseFloat(dataSnapshot.child("Str").getValue().toString());
                stats[1]=Float.parseFloat(dataSnapshot.child("StrE").getValue().toString());
                stats[2]=Float.parseFloat(dataSnapshot.child("Stm").getValue().toString());
                stats[3]=Float.parseFloat(dataSnapshot.child("Spd").getValue().toString());
                stats[4]=Float.parseFloat(dataSnapshot.child("Flx").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return stats;
    }

}
