package com.example.project4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.MainModel;
import com.example.project4.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        holder.firstName.setText(model.getFirstName());
        holder.lastName.setText(model.getLastName());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
        holder.room.setText(model.getRoom());
        holder.price.setText(model.getPrice());
        holder.status.setText(model.getStatus());




        Glide.with(holder.img.getContext())
                .load(model.getUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_update))
                        .setExpanded(true, 1200)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText firstName = view.findViewById(R.id.txtfname);
                EditText lastName = view.findViewById(R.id.txtlname);
                EditText phone = view.findViewById(R.id.txtphone);
                EditText email = view.findViewById(R.id.txtemail);
                EditText room = view.findViewById(R.id.txtroom);
                EditText price = view.findViewById(R.id.txtprice);
                EditText status = view.findViewById(R.id.status);
                EditText url = view.findViewById(R.id.txturl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                firstName.setText(model.getFirstName());
                lastName.setText(model.getLastName());
                phone.setText(model.getPhone());
                email.setText(model.getEmail());
                room.setText(model.getRoom());
                price.setText(model.getPrice());
                status.setText(model.getStatus());
                url.setText(model.getUrl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("firstName",firstName.getText().toString());
                        map.put("lastName",lastName.getText().toString());
                        map.put("phone",phone.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("room",room.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("status",status.getText().toString());
                        map.put("url",url.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("user")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.firstName.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }

                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.firstName.getContext(), "Error Update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }

                                });

                    }

                });


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.firstName.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("user")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.firstName.getContext(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView firstName,lastName,phone,email,room,price,status;

        Button btnEdit,btnDelete;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            firstName = (TextView)itemView.findViewById(R.id.fnametext);
            lastName = (TextView)itemView.findViewById(R.id.lnametext);
            phone = (TextView)itemView.findViewById(R.id.phonetext);
            email = (TextView)itemView.findViewById(R.id.emailtext);
            room = (TextView)itemView.findViewById(R.id.roomtext);
            price = (TextView)itemView.findViewById(R.id.pricetext);
            status = (TextView)itemView.findViewById(R.id.statustext);
            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);

        }
    }
}