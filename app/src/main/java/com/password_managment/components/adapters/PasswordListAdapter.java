package com.password_managment.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.password_managment.R;
import com.password_managment.components.ButtonComponent;
import com.password_managment.models.Password;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PasswordListAdapter extends RecyclerView.Adapter<PasswordListAdapter.viewHolder> implements Filterable {
    private List<Password> passwordListFull;
    private List <Password> passwordList;
    private Context context;
    private OnPasswordClickListener listener;

    public PasswordListAdapter(List<Password> passwordList, Context context, OnPasswordClickListener listener) {
        this.passwordListFull = new ArrayList<>(passwordList);
        this.passwordList = passwordList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return FilterPassword;
    }

    public Filter FilterPassword = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            List<Password> templist = new ArrayList<>();

            if(searchText.isEmpty()) {
                templist.addAll(passwordListFull);
            } else {
                for(Password password: passwordListFull) {
                    if (password.getTitle().toLowerCase().contains(searchText)) {
                        templist.add(password);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = templist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            passwordList.clear();
            passwordList.addAll((Collection<? extends Password>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnPasswordClickListener {
        void onPasswordClick(Password password);
    }


    @NonNull
    @Override
    public PasswordListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_button, parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Password password = passwordList.get(position);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0 ,50);

        holder.button.setText(password.getTitle());
        holder.button.setLayoutParams(params);
        holder.button.setPadding(25,50,50,50);
        holder.button.setTextSize(20);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPasswordClick(password);
            }
        });

    }

    public class viewHolder extends RecyclerView.ViewHolder{

        Button button;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }

    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }
}
