package Fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.STORAGE;

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

        PostActivity.setTitle("Posto shpallje");
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
                        || PostActivity.post.getNrDyer()==0 || PostActivity.post.getRegjistrimiPare()==null || PostActivity.post.getKm()==null
                        || PostActivity.post.getTransmisioni()==0 || PostActivity.post.getCmimi()==0) {
                    tvKryesore.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvKryesoreDesc.setTextColor(getResources().getColor(R.color.colorAccent));
                    Toast.makeText(getContext(), "Ju lutem plotësoni fushat me të kuqe!", Toast.LENGTH_LONG).show();
                    gabime1++;
                } else {
                    tvKryesore.setTextColor(getResources().getColor(R.color.prapavija));
                    tvKryesoreDesc.setTextColor(getResources().getColor(R.color.hiri));
                    PostActivity.post.setPronariID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    PostActivity.features.setAirbag(PostActivity.airbag);
                    PostActivity.post.setFeatures(PostActivity.features);

                    nderroFragmentin(2);
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
        fragment = null;
        switch (i){
            case 1:
                fragment = new PostMainFragment();
                break;
            case 2:
                //hapet dialogu per shtim te fotografive
                if(ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    fragment = new PostImagesFragment();
                    break;
                } else {
                    fragment = null;
                    ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE}, 200);

                    break;
                }



                

            case 3:
                fragment = new PostTechnicalFragment();
                break;
            case 4:
                fragment = new PostFeaturesFragment();
                break;
            case 5:
                fragment = new PostDescriptionFragment();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            fragment = new PostImagesFragment();
            Toast.makeText(getContext(), "Qasja në fotografi u lejua", Toast.LENGTH_LONG).show();
        }

//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
