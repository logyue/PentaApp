package pentaapp.com.pentaapp.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pentaapp.com.pentaapp.MainActivity;
import pentaapp.com.pentaapp.R;
import pentaapp.com.pentaapp.Registration.LoginActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button signOut;
    private TextView textViewtEmail;
    private TextView textViewtName;
    private TextView textViewtGender;


    private FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            firebaseDatabase=FirebaseDatabase.getInstance();
            firebaseAuth=FirebaseAuth.getInstance();
            textViewtEmail=(TextView) rootView.findViewById(R.id.textViewtEmail);
            textViewtEmail.setText("Email: "+user.getEmail());


            firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {//Retrieve Data from Firebase(Name Gender)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    textViewtName=(TextView) rootView.findViewById(R.id.textViewtName);
                    textViewtGender=(TextView) rootView.findViewById(R.id.textViewtGender);
                    String name=(String)dataSnapshot.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Name").getValue();
                    textViewtName.setText(name);
                    String gender=(String)dataSnapshot.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Gender").getValue();
                    textViewtGender.setText(gender);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            signOut=(Button)rootView.findViewById(R.id.buttonSignOut);
            signOut.setOnClickListener(this);

        }else{
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if(v==signOut){
            firebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }
    }


}
