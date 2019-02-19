package rks.youngdevelopers.autotreguks;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.SearchRecyclerAdapter;
import Fragments.SearchFragment;
import Fragments.UpdateProfileFragment;
import Models.SearchConditions;

public class SearchResultActivity extends AppCompatActivity implements SearchRecyclerAdapter.OnPostListener {
    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter1, adapter2, adapter3, adapter4;
    public static SearchConditions searchConditions;
    public static List<List> finalResult;
    private DataSnapshot mDataSnapshot;
    ImageView imgBack;

    List<List<List>> sortedData;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    int btnID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_result);
        setSupportActionBar(toolbar);

        sortedData = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        imgBack = (ImageView)findViewById(R.id.resultBack) ;
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        finalResult = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("posts").orderByChild("regjistrimiPare").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot;
                postData(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child("posts").orderByChild("cmimi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot;
                postData(2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("posts").orderByChild("km").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot;
                postData(3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("posts").orderByChild("fuqia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot;
                postData(4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_viti) {
            changeData(1);
            btnID=0;
        } else  if (item.getItemId() == R.id.sort_cmimi) {
            changeData(2);
            btnID=1;
        } else  if (item.getItemId() == R.id.sort_km) {
            changeData(3);
            btnID=2;
        } else  if (item.getItemId() == R.id.sort_power) {
            changeData(4);
            btnID=3;
        }
        return super.onOptionsItemSelected(item);
    }


    private void postData(int i) {
        if (i == 1) {
            finalResult = Database.RemoteDatabase.postSearch(mDataSnapshot, searchConditions);
            sortedData.add(0,finalResult);
            adapter1 = new SearchRecyclerAdapter(finalResult.get(0), finalResult.get(1), getApplicationContext(), this);
            recyclerView.setAdapter(adapter1);
        } else if (i == 2) {
            finalResult = Database.RemoteDatabase.postSearch(mDataSnapshot, searchConditions);
            sortedData.add(1,finalResult);
            adapter2 = new SearchRecyclerAdapter(finalResult.get(0), finalResult.get(1), getApplicationContext(), this);
        } else if (i == 3) {
            finalResult = Database.RemoteDatabase.postSearch(mDataSnapshot, searchConditions);
            sortedData.add(2,finalResult);
            adapter3 = new SearchRecyclerAdapter(finalResult.get(0), finalResult.get(1), getApplicationContext(), this);
        }else if (i == 4) {
            finalResult = Database.RemoteDatabase.postSearch(mDataSnapshot, searchConditions);
            sortedData.add(3,finalResult);
            adapter4 = new SearchRecyclerAdapter(finalResult.get(0), finalResult.get(1), getApplicationContext(), this);
        }
    }

    private void changeData(int i) {
        if (i == 1) {
            recyclerView.swapAdapter(adapter1, false);
        } else if (i == 2) {
            recyclerView.swapAdapter(adapter2, false);
        } else if (i == 3) {
            recyclerView.swapAdapter(adapter3, false);
        } else if (i == 4) {
            recyclerView.swapAdapter(adapter4, false);
        }
    }

    @Override
    public void onPostClick(int position, final int id) {
        if (id == 1) {
            /* u kliku postimi*/
            Toast.makeText(this, "U klikua POSTIMI " + (position + 1), Toast.LENGTH_LONG).show();
            Intent objIntent = new Intent(this, PostViewActivity.class);
            List<List> mFinalResult = sortedData.get(btnID);
            PostViewActivity.postID = mFinalResult.get(id).get(position).toString();
            startActivity(objIntent);
        } else if (id == 2) {
//            /* u kliku SAVE */
//            Toast.makeText(this, "U klikua SAVE te postimi me id: " + SearchFragment.finalResult.get(1).get(position), Toast.LENGTH_LONG).show();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference dRef = db.getReference();
            FirebaseUser useri = firebaseAuth.getCurrentUser();

            final Context context = this;
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_delete_post);
            dialog.setTitle("Test");
            Button btnExit = (Button)dialog.findViewById(R.id.btnExit);
            Button btnDeletePost = (Button)dialog.findViewById(R.id.btnDeletePost);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            List<List> mFinalResult = sortedData.get(btnID);
            final String pos = mFinalResult.get(1).get(position).toString();
            btnDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dRef.child("posts").child(pos).child("ttl").setValue(111111);
                    Toast.makeText(context,"Postimi u fshi me sukses", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
            dialog.show();



        }

    }
}
