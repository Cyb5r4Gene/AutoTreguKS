package Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

import static android.support.constraint.Constraints.TAG;

public class PostTitleFragment extends Fragment {

    EditText postTitulli;
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_titulli, container, false);
        PostActivity.setTitle("Titulli i shpalljes");
        ((PostActivity) getActivity()).getSupportActionBar().show();

        postTitulli = (EditText)v.findViewById(R.id.editPostTitulli);
        postTitulli.setText(PostActivity.post.getTitulli());

        btnNext = (Button)v.findViewById(R.id.btnTitulliNext);

        postTitulli.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                PostActivity.post.setTitulli(postTitulli.getText().toString());
                return false;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Database.RemoteDatabase.addPost(PostActivity.post);
                if(id!=null)
                {
                    if(PostActivity.postFotos.size()>0) {
                        for(int i=0;i<PostActivity.postFotos.size();i++){
                            final ProgressDialog progressDialog = new ProgressDialog(getContext());
                            final int nr = i;
                            progressDialog.setTitle("Duke ngarkuar fotografitë "+String.valueOf(i));
                            progressDialog.show();
                            UploadTask uploadTask = Database.RemoteDatabase.postImages(id, PostActivity.postFotos.get(i),i);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Foto "+String.valueOf((nr+1))+" u ngarkua!", Toast.LENGTH_SHORT).show();
                                    ((PostActivity)getActivity()).finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Ngarkimi i fotografisë "+String.valueOf((1+nr))+" dështoi!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.setMessage("Ju lutem prisni deri sa të ngarkohen fotografitë");
                                }
                            });
                        }
                    }

                } else {
                    Log.e(TAG, "addNewPost: failed");
                    Toast.makeText(getContext(), "Shpallja nuk mund të ruhet në databazë!", Toast.LENGTH_LONG).show();
                }



            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
