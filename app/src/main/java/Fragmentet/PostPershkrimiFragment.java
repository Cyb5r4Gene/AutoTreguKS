package Fragmentet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class PostPershkrimiFragment extends Fragment {

    EditText postPershkrimi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_pershkrimi, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().setTitle("Përshkrimi i veturës");

        postPershkrimi = (EditText)v.findViewById(R.id.editPostPershkrimi);
        postPershkrimi.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                PostActivity.post.setPershkrimi(postPershkrimi.getText().toString());
                return false;
            }
        });

        if(PostActivity.post.getPershkrimi()!=null)
            postPershkrimi.setText(PostActivity.post.getPershkrimi());


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
