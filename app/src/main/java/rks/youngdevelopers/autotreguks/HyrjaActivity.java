package rks.youngdevelopers.autotreguks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class HyrjaActivity extends AppCompatActivity {

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
                  Intent objKryefaqja = new Intent(getApplicationContext(), KryefaqjaActivity.class);
                  startActivity(objKryefaqja);
              }
              catch (InterruptedException e) {
                  e.printStackTrace();
              }

          }
        };
        timer.start();
    }

}
