package Fragmentet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class TitulliFragment extends Fragment {

    EditText postTitulli;
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_titulli, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().setTitle("Titulli i shpalljes");
        ((PostActivity) getActivity()).getSupportActionBar().show();

        postTitulli = (EditText)v.findViewById(R.id.editPostTitulli);

        postTitulli.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(postTitulli.getText().toString()!=null)
                    PostActivity.post.setTitulli(postTitulli.getText().toString());
                return false;
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
