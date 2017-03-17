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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pentaapp.com.pentaapp.R;

public class RegisterActivity extends AppCompatActivity {


    @Bind(R.id.textPersonName) EditText textPersonName;
    @Bind(R.id.textEmailAddress) EditText editTextEmail;
    @Bind(R.id.editTextPassword) EditText editTextPassword;
    @Bind(R.id.editTextPasswordCompare) EditText editTextPasswordCompare;
    @Bind(R.id.buttonRegister) Button buttonRegister;
    @Bind(R.id.textViewSignin) TextView textViewSignup;
    @Bind(R.id.radioGender) RadioGroup radioGroup;
    private RadioButton radioButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton=(RadioButton)findViewById(checkedId);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }

    private void registerUser() {//register method
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
            return;
        }
        buttonRegister.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //main activity here
                    String name=textPersonName.getText().toString();
                    String gender=radioButton.getText().toString();
                    String user_id=firebaseAuth.getCurrentUser().getUid();

                    DatabaseReference current_user_db=firebaseDatabase.getReference().child("Users").child(user_id);
                    current_user_db.child("Name").setValue(name);
                    current_user_db.child("Gender").setValue(gender);
                } else {
                    Toast.makeText(RegisterActivity.this, "Could not reigster. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    public void onSignupSuccess() {
        buttonRegister.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public boolean validate() {
        boolean valid = true;
        String name = textPersonName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String passwordCompare = editTextPasswordCompare.getText().toString();
        if (name.isEmpty() || name.length() < 3) {
            //email is empty
            textPersonName.setError("at least 3 characters");
            valid = false;
        }
        else {
            textPersonName.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        if(passwordCompare.isEmpty() || passwordCompare.length() < 4 || passwordCompare.length() > 10 || !(passwordCompare.equals(password))) {
            editTextPasswordCompare.setError("Password Do not match");
            valid = false;
        } else {
            editTextPasswordCompare.setError(null);
        }
        return valid;
    }
}
