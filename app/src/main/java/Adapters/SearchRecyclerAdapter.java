package Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.LocalDatabase;
import Models.Adresa;
import Models.Autosallon;
import Models.Post;
import Models.User;
import rks.youngdevelopers.autotreguks.R;
import Other.UniversalImageLoader;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    private List<Post> listItems;
    private List<String> listIDs;
    private OnPostListener onPostListener;
    Context context;
    String imageUrl;

    public SearchRecyclerAdapter(List<Post> listItems, List<String> listIDs, Context context, OnPostListener onPostListener) {
        this.listItems = listItems;
        this.context = context;
        this.onPostListener = onPostListener;
        this.listIDs = listIDs;
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_recycler_item, parent, false);

        return new ViewHolder(v, onPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchRecyclerAdapter.ViewHolder holder, final int position) {
        holder.tvTitle.setText(listItems.get(position).getTitulli());
        holder.tvPrice.setText(String.valueOf(listItems.get(position).getCmimi()) + " €");

        if (listItems.get(position).getAksident() == 1)
            holder.tvAccident.setText("I Aksidentuar -");
        else if (listItems.get(position).getAksident() == 2)
            holder.tvAccident.setText("I Pa Aksidentuar -");
        else
            holder.tvAccident.setText("");

        holder.tvProduction.setText(listItems.get(position).getRegjistrimiPare() + " -");

        holder.tvKm.setText(listItems.get(position).getKm() + " Km");

        List<String> items = Arrays.asList(context.getResources().getStringArray(R.array.karburanti));
        holder.tvFuel.setText(items.get(listItems.get(position).getKarburantiID()-1) + " -");


        if (listItems.get(position).getFuqia() != null) {
            holder.tvPower.setText(listItems.get(position).getFuqia() + " KW  ");
        } else {
            holder.tvPower.setText("");
        }

        items = Arrays.asList(context.getResources().getStringArray(R.array.transmisioni));
        holder.tvTransmission.setText(items.get(listItems.get(position).getTransmisioni()) + " -");

        if (!listItems.get(position).getTarga().equals("Pa përcaktuar")) {
            holder.tvPlate.setText(listItems.get(position).getTarga() + " -");
            if (listItems.get(position).getRegjistrim() != null) {
                holder.tvPlateDesc.setText("Regjistrim? :" + listItems.get(position).getRegjistrim());
            } else if (listItems.get(position).getDoganuar() != null) {
                holder.tvPlateDesc.setText("I doganuar? :" + listItems.get(position).getDoganuar());
            }
        } else {
            holder.tvPlate.setText("");
            holder.tvPlateDesc.setText("");
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child("users").child(listItems.get(position).getPronariID()).getValue(User.class);
                Adresa address = dataSnapshot.child("addresses").child(user.getAdresaID()).getValue(Adresa.class);
                if (user.getTipiLlogarise().equals("1")) {
                    holder.tvOwner.setText(user.getEmri() + " " + user.getMbiemri());
                } else {
                    Autosallon carDealer = dataSnapshot.child("carDealers").child(listItems.get(position).getPronariID()).getValue(Autosallon.class);
                    holder.tvOwner.setText(carDealer.getEmri());
                }
                final List<String> items = Arrays.asList(context.getResources().getStringArray(R.array.qytetet));
                holder.tvAddress.setText("Rr." + address.getRruga() + " Nr." + address.getNr() + ", " + address.getKodi() + ", " + items.get(Integer.parseInt(address.getQyteti())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null && listItems.get(position).getPronariID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.imgSave.setImageResource(R.drawable.ic_clear);
        } else {
            SQLiteDatabase objDb = (new LocalDatabase(context)).getReadableDatabase();
            Cursor c = null;
            List<String> savedPosts = new ArrayList<>();
            c = objDb.rawQuery("Select * from tblSaved", null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        savedPosts.add(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }

            /*NESE EKZISTON POSTIMI I RUAJTUR FSHIJE, PERNDRYSHE RUAJE*/
            int exist = 0;
            if (savedPosts != null) {
                for (int i = 0; i < savedPosts.size(); i++) {
                    if (savedPosts.get(i).equals(listIDs.get(position))) {
                        exist++;
                    }
                }
            }

            if (exist == 0)
                holder.imgSave.setImageResource(R.drawable.ic_parking_red);
            else
                holder.imgSave.setImageResource(R.drawable.ic_parking);
        }

        holder.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null && listItems.get(position).getPronariID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                    onPostListener.onPostClick(position, 2);
                } else {

                    SQLiteDatabase objDb = (new LocalDatabase(context)).getReadableDatabase();
                    Cursor c = null;
                    List<String> savedPosts = new ArrayList<>();
                    c = objDb.rawQuery("Select * from tblSaved", null);
                    if (c != null) {
                        c.moveToFirst();
                        if (c.getCount() > 0) {
                            for (int j = 0; j < c.getCount(); j++) {
                                savedPosts.add(c.getString(1));
                                c.moveToNext();
                            }
                            c.close();
                        }
                    }

                    /*NESE EKZISTON POSTIMI I RUAJTUR FSHIJE, PERNDRYSHE RUAJE*/
                    int exist = 0;
                    if (savedPosts != null) {
                        for (int i = 0; i < savedPosts.size(); i++) {
                            if (savedPosts.get(i).equals(listIDs.get(position))) {
                                exist++;
                            }
                        }
                    }


                    //NESE POSTIMI NUK EKZITON NE DB ATEHERE RUAJE
                    if (exist == 0) {
                        objDb.execSQL("Insert into tblSaved (postID) values ('" + listIDs.get(position) + "');");
                        holder.imgSave.setImageResource(R.drawable.ic_parking);
                    } else {
                        objDb.execSQL("Delete from tblSaved where postID='" + listIDs.get(position)+"'");
                        holder.imgSave.setImageResource(R.drawable.ic_parking_red);
                    }
                }

            }
        });

        loadImage(listIDs.get(position), holder.imageView, holder.progressBar);


    }

    private void loadImage(String postID, final ImageView postImage, final ProgressBar postProgressBar) {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("postImages/" + postID + "/Foto1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageUrl = uri.toString();
                UniversalImageLoader.setImage(imageUrl, postImage, postProgressBar, "");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView, imgSave;
        public TextView tvPrice, tvAccident, tvPlate, tvPlateDesc, tvProduction,
                tvKm, tvPower, tvFuel, tvTransmission, tvOwner, tvAddress;
        EditText tvTitle;
        OnPostListener onPostListener;
        ProgressBar progressBar;

        public ViewHolder(View itemView, OnPostListener onPostListener) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.postImage);
            tvTitle = (EditText) itemView.findViewById(R.id.postTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.postPrice);
            tvAccident = (TextView) itemView.findViewById(R.id.postAksident);
            tvPlate = (TextView) itemView.findViewById(R.id.postPlate);
            tvPlateDesc = (TextView) itemView.findViewById(R.id.plateDesc);
            tvProduction = (TextView) itemView.findViewById(R.id.postProduction);
            tvKm = (TextView) itemView.findViewById(R.id.postKm);
            tvPower = (TextView) itemView.findViewById(R.id.postPower);
            tvFuel = (TextView) itemView.findViewById(R.id.postFuel);
            tvTransmission = (TextView) itemView.findViewById(R.id.postTransmision);
            tvOwner = (TextView) itemView.findViewById(R.id.postOwner);
            tvAddress = (TextView) itemView.findViewById(R.id.postAdresa);
            imgSave = (ImageView) itemView.findViewById(R.id.imgPostSave);
            progressBar = (ProgressBar) itemView.findViewById(R.id.searchResultProgressBar);

            this.onPostListener = onPostListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPostListener.onPostClick(getAdapterPosition(), 1);
        }
    }

    public interface OnPostListener {
        void onPostClick(int position, int id);
    }
}
