package pentaapp.com.pentaapp.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pentaapp.com.pentaapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordCompare;
    private TextView textViewSignup;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordCompare = (EditText) findViewById(R.id.editTextPasswordCompare);

        radioGroup=(RadioGroup)findViewById(R.id.radioGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton=(RadioButton)findViewById(checkedId);

            }
        });


        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewSignup = (TextView) findViewById(R.id.textViewSignin);
        buttonRegister.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

    }

    private void registerUser() {//register method
        String name=editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordCompare = editTextPasswordCompare.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            //email is empty
            Toast.makeText(this, "Please enter name...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordCompare)){
            Toast.makeText(this, "Password not match...", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //main activity here
                    String name=editTextName.getText().toString().trim();
                    String gender=radioButton.getText().toString().trim();

                    String user_id=firebaseAuth.getCurrentUser().getUid();

                    DatabaseReference current_user_db=firebaseDatabase.getReference().child("Users").child(user_id);
                    current_user_db.child("Name").setValue(name);
                    current_user_db.child("Gender").setValue(gender);
                } else {
                    Toast.makeText(RegisterActivity.this, "Could not reigster. Please try again.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }
        if (v == textViewSignup) {
            //will goto login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


}
