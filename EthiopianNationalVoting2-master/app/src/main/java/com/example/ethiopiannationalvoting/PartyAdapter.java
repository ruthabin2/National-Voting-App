package com.example.ethiopiannationalvoting;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Parties")
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Deleted Succesfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog= DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.content))
                        .setExpanded(false)
                        .create();

                View holderView= dialog.getHolderView();

                EditText pnam= holderView.findViewById(R.id.pname);
                EditText objct= holderView.findViewById(R.id.objctv);
                Button btnup= holderView.findViewById(R.id.btnupdate);

                pnam.setText(model.getPartyName());
                objct.setText(model.getObjective());

                btnup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map=new HashMap<>();
                        map.put("PartyName", pnam.getText().toString());
                        map.put("Objective", objct.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Parties")
                                .child(getRef(position).getKey())
                                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

                dialog.show();
            }
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        /// here is the design
        ImageView iv,update,delete;
        TextView t1,t2;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.symbol);
            t1=itemView.findViewById(R.id.party_name);
            t2=itemView.findViewById(R.id.obj);

            update=itemView.findViewById(R.id.image_edit);
           delete=itemView.findViewById(R.id.image_delete);
        }
    }
}
