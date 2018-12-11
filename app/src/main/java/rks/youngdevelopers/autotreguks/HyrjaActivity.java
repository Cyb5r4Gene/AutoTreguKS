package rks.youngdevelopers.autotreguks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class HyrjaActivity extends AppCompatActivity {

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
                  if(FirebaseAuth.getInstance().getCurrentUser()==null) {
                      faqja = new Intent(getApplicationContext(), KryefaqjaActivity.class);
                  }
                  else {
                      faqja = new Intent(getApplicationContext(), UserActivity.class);
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
