package rks.youngdevelopers.autotreguks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DialogPostAdapter extends RecyclerView.Adapter<DialogPostAdapter.ViewHolder> {
    private List<String> listItems;
    private OnDialogListener mOnDialogListener;
    Context context;

    public DialogPostAdapter(List<String> listItems, Context context, OnDialogListener onDialogListener) {
        this.listItems = listItems;
        this.context = context;
        this.mOnDialogListener = onDialogListener;
    }

    @NonNull
    @Override
    public DialogPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_list_item, parent, false);

        return new ViewHolder(v, mOnDialogListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogPostAdapter.ViewHolder holder, int position) {
        holder.tvItem.setText(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvItem;
        OnDialogListener onDialogListener;

        public ViewHolder(View itemView, OnDialogListener onDialogListener) {
            super(itemView);
            tvItem = (TextView)itemView.findViewById(R.id.tvDialogEmri);
            this.onDialogListener = onDialogListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDialogListener.onDialogItemClick(getAdapterPosition());
        }
    }

    public interface OnDialogListener {
        void onDialogItemClick(int position);
    }
}
