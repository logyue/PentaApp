package pentaapp.com.pentaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button signOut;
    private TextView textViewtEmail;
    private TextView textViewtName;
    private TextView textViewtGender;


    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            firebaseDatabase=FirebaseDatabase.getInstance();
            firebaseAuth=FirebaseAuth.getInstance();
            textViewtEmail=(TextView)findViewById(R.id.textViewtEmail);
            textViewtEmail.setText("Email: "+user.getEmail());
            textViewtName=(TextView)findViewById(R.id.textViewtName);
            textViewtGender=(TextView)findViewById(R.id.textViewtGender);
            firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name=(String)dataSnapshot.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Name").getValue();
                    textViewtName.setText(name);
                    String gender=(String)dataSnapshot.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Gender").getValue();
                    textViewtGender.setText(gender);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            signOut=(Button)findViewById(R.id.buttonSignOut);
            signOut.setOnClickListener(this);

        }else{
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    @Override
    public void onClick(View v) {

        if(v==signOut){
            firebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }


}
