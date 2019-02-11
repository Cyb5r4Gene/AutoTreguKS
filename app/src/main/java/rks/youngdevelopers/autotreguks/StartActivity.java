package rks.youngdevelopers.autotreguks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StartActivity extends AppCompatActivity {

    Intent faqja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyrja);


        Thread timer = new Thread()
        {
          public void run()
          {
              try{
                  sleep(1000);
                  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                  FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                  if(firebaseUser==null) {
                      faqja = new Intent(getApplicationContext(), NonUserActivity.class);
                  }
                  else {
                      if(firebaseUser.isEmailVerified())
                          faqja = new Intent(getApplicationContext(), UserActivity.class);
                      else {
                          faqja = new Intent(getApplicationContext(), NonUserActivity.class);
                          firebaseAuth.signOut();
                      }
                  }
                  startActivity(faqja);
              }
              catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        };
        timer.start();
    }

}
