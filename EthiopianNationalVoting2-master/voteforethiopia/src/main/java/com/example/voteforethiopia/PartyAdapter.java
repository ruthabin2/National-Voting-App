package com.example.voteforethiopia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PartyAdapter extends FirebaseRecyclerAdapter<PartyMode,PartyAdapter.ViewHolder> {

   private Context context;


    public PartyAdapter(FirebaseRecyclerOptions<PartyMode> options,Context context) {
        super(options);
this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row_for_recycleview,parent,false);

        return new ViewHolder(v);
    }



    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PartyMode model) {

        holder.t1.setText(model.getPartyName());
        holder.t2.setText(model.getObjective());

        String imageUri=null;
        imageUri=model.getSymbol();
        Picasso.get().load(imageUri).into(holder.iv);


    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        /// here is the design
        ImageView iv;
        TextView t1,t2;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.symbol);
            t1=itemView.findViewById(R.id.party_name);
            t2=itemView.findViewById(R.id.obj);


        }
    }
}
