package org.yoneco.ict.tithandizane;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yoneco.ict.tithandizane.R;

public class RegisterUser extends AppCompatActivity {
    EditText name;
    EditText phone1;
    EditText phone2;
    EditText phone3;
    EditText phone4;
    EditText userEmail;

    Button registerUser;

    public final static String NAME = "name";
    public final static String PHONE1 = "phone1";
    public final static String PHONE2 = "phone2";
    public final static String PHONE3 = "phone3";
    public final static String PHONE4 = "phone4";
    public final static String mypref ="mypref";
    public final static String EMAIL = "email";

    SharedPreferences userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name =(EditText)findViewById(R.id.username);
        phone1 =(EditText)findViewById(R.id.user_phone);
        phone2 =(EditText)findViewById(R.id.phone_num1);
        phone3 =(EditText)findViewById(R.id.phone_num2);
        phone4 =(EditText)findViewById(R.id.phone_num3);
        userEmail  =(EditText)findViewById(R.id.user_email);
        registerUser =(Button)findViewById(R.id.register_button);

        userProfile = getSharedPreferences(mypref, Context.MODE_PRIVATE);
        if (userProfile.contains(NAME)) {
            name.setText(userProfile.getString(NAME, ""));
        }if (userProfile.contains(PHONE1)) {
            phone1.setText(userProfile.getString(PHONE1, ""));
        }if (userProfile.contains(PHONE2)) {
            phone2.setText(userProfile.getString(PHONE2, ""));
        }if (userProfile.contains(PHONE3)) {
            phone3.setText(userProfile.getString(PHONE3, ""));
        }if (userProfile.contains(PHONE4)) {
            phone4.setText(userProfile.getString(PHONE4, ""));
        }if (userProfile.contains(EMAIL)) {
            userEmail.setText(userProfile.getString(EMAIL, ""));
        }

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userPhone = phone1.getText().toString();
                String phone_1 = phone2.getText().toString();
                String phone_2 = phone3.getText().toString();
                String phone_3 = phone4.getText().toString();
                String email =   userEmail.getText().toString();

                SharedPreferences.Editor editor = userProfile.edit();
                editor.putString(NAME, userName);
                editor.putString(PHONE1, userPhone);
                editor.putString(PHONE2, phone_1);
                editor.putString(PHONE3, phone_2);
                editor.putString(PHONE4, phone_3);
                editor.putString(EMAIL, email);
                editor.commit();

                Toast.makeText(RegisterUser.this, "Your profile was successfully saved. ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
