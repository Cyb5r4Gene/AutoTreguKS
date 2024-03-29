package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import rks.youngdevelopers.autotreguks.PostActivity;
import rks.youngdevelopers.autotreguks.R;

public class DialogCheckAdapter extends RecyclerView.Adapter<DialogCheckAdapter.ViewHolder> {
    private List<String> listItems;
    private OnCheckListener mOnCheckListener;
    Context context;
    public static boolean checkedItems[];

    public DialogCheckAdapter(List<String> listItems, Context context, OnCheckListener onCheckListener) {
        this.listItems = listItems;
        this.context = context;
        this.mOnCheckListener = onCheckListener;
        checkedItems = new boolean[listItems.size()];
    }

    @NonNull
    @Override
    public DialogCheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_check_airbag_item, parent, false);

        return new ViewHolder(v, mOnCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogCheckAdapter.ViewHolder holder, final int position) {
        holder.tvItem.setText(listItems.get(position));

        if(position==0)
            holder.check.setChecked(PostActivity.airbag.isAirbagShoferi());
        else if (position==1)
            holder.check.setChecked(PostActivity.airbag.isAirbagAnesor());
        else if (position==2)
            holder.check.setChecked(PostActivity.airbag.isAirbagIPrapme());
        else
            holder.check.setChecked(PostActivity.airbag.isAirbagTjere());

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                checkedItems[position] = isChecked;

                if(position==0)
                    PostActivity.airbag.setAirbagShoferi(isChecked);
                else if (position==1)
                    PostActivity.airbag.setAirbagAnesor(isChecked);
                else if (position==2)
                    PostActivity.airbag.setAirbagIPrapme(isChecked);
                else
                    PostActivity.airbag.setAirbagTjere(isChecked);
            }
        });



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvItem;
        public CheckBox check;
        OnCheckListener onCheckListener;

        public ViewHolder(View itemView, OnCheckListener onCheckListener) {
            super(itemView);
            tvItem = (TextView)itemView.findViewById(R.id.tvDialogEmri);
            check = (CheckBox)itemView.findViewById(R.id.checkDialog);
            this.onCheckListener = onCheckListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onCheckListener.onDialogCheckClick(getAdapterPosition());

        }
    }

    public interface OnCheckListener {
        void onDialogCheckClick(int position);
    }
}
