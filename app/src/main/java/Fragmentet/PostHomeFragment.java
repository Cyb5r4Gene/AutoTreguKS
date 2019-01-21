package Fragmentet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class PostHomeFragment extends Fragment {
    private LinearLayout postKryesore, postTeknike, postKarakteristikat, postPershkrimi;
    private RelativeLayout postFoto;
    TextView tvKryesore, tvKryesoreDesc;
    Button btnPosto;
    Fragment fragment;


    public static boolean clicked = false;

    private int gabime1, gabime2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_home, container, false);

        ((PostActivity) getActivity()).getSupportActionBar().setTitle("Posto shpallje");
        ((PostActivity) getActivity()).getSupportActionBar().show();
        postKryesore = (LinearLayout)v.findViewById(R.id.postKryesore);
        postFoto = (RelativeLayout) v.findViewById(R.id.postFoto);
        postTeknike = (LinearLayout) v.findViewById(R.id.postTeknike);
        postKarakteristikat = (LinearLayout) v.findViewById(R.id.postKarakteristika);
        postPershkrimi = (LinearLayout) v.findViewById(R.id.postPershkrimi);
        btnPosto = (Button) v.findViewById(R.id.btnPosto);
        tvKryesore = (TextView) v.findViewById(R.id.tvKryesore);
        tvKryesoreDesc = (TextView) v.findViewById(R.id.tvKryesoreDesc);

        postKryesore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nderroFragmentin(1);
            }
        });

//        postFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nderroFragmentin(2);
//            }
//        });

        postTeknike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nderroFragmentin(3);
            }
        });

        postKarakteristikat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nderroFragmentin(4);
            }
        });

        postPershkrimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nderroFragmentin(5);
            }
        });

        btnPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                if(PostActivity.post.getMarkaID()==0 || PostActivity.post.getModeliID()==0 || PostActivity.post.getKarburantiID()==0 || PostActivity.post.getKarroceriaID()==0
                        || PostActivity.post.getNrDyer()==null || PostActivity.post.getRegjistrimiPare()==null || PostActivity.post.getKm()==null
                        || PostActivity.post.getTransmisioni()==null || PostActivity.post.getCmimi()==0) {
                    tvKryesore.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvKryesoreDesc.setTextColor(getResources().getColor(R.color.colorAccent));
                    Toast.makeText(getContext(), "Ju lutem plotësoni fushat me të kuqe!", Toast.LENGTH_LONG).show();
                    gabime1++;
                } else {
                    tvKryesore.setTextColor(getResources().getColor(R.color.prapavija));
                    tvKryesoreDesc.setTextColor(getResources().getColor(R.color.hiri));
                    PostActivity.post.setPronariID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    PostActivity.karakteristikat.setAirbag(PostActivity.airbag);
                    PostActivity.post.setKarakteristikat(PostActivity.karakteristikat);

                    nderroFragmentin(2);
                    //ndryshime

                    //
                    //
                    //
                    //
                    //

//                    if(RemoteDatabase.addPost(post)) {
//                        Toast.makeText(getContext(), "Postimi u shtua me sukses!", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getContext(), "Postimi i shpalljes dështoi!", Toast.LENGTH_LONG).show();
//                    }

                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // function to navigate through activity fragments
    private void nderroFragmentin(int i)
    {
        switch (i){
            case 1:
                fragment = new PostKryesoreFragment();
                break;
            case 2:
                //hapet dialogu per shtim te fotografive
                fragment = new PostFotoFragment();
                break;
            case 3:
                fragment = new PostTeknikeFragment();
                break;
            case 4:
                fragment = new PostKarakteristikatFragment();
                break;
            case 5:
                fragment = new PostPershkrimiFragment();
                break;
        }
        if(fragment!=null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_post, fragment);
            ft.commit();
        }
    }

}
