package pentaapp.com.pentaapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;
    private Button buttonPassword;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail=(EditText)findViewById(R.id.editTextSendEmail);
        buttonPassword=(Button)findViewById(R.id.buttonPassword);
        buttonPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        firebaseAuth=FirebaseAuth.getInstance();
        String emailAddress=editTextEmail.toString().trim();
        if(v==buttonPassword){
            firebaseAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this,"Email sent.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
