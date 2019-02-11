package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import rks.youngdevelopers.autotreguks.NonUserActivity;
import rks.youngdevelopers.autotreguks.R;
import rks.youngdevelopers.autotreguks.UserActivity;

public class SavedPostsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_postimet,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            ((NonUserActivity) getActivity()).getSupportActionBar().setTitle(R.string.postimet);
        else
            ((UserActivity) getActivity()).getSupportActionBar().setTitle(R.string.postimet);



    }
}
