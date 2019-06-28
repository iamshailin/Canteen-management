package com.example.readdata;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<listItem> listItems;
    private Context context;
    public String ItemClickName;
    public PopupWindow mPopupWindow;
    public RelativeLayout mRelativeLayout;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TextView category;
    private TextView description;
    private TextView veg;


    public MyAdapter(List<listItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        listItem listItem = listItems.get(i);

        viewHolder.heading.setText(listItem.getName());
        viewHolder.desc.setText(listItem.getPrice());


        Picasso.get()
                .load(listItem.getImageUrl())
                .resize(65,65)
                .centerCrop()
                .into(viewHolder.imageView);
        //Remove from below
        viewHolder.checkBox.setOnClickListener(null);
        viewHolder.checkBox.setChecked(listItems.get(i).isSelected());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listItems.get(viewHolder.getAdapterPosition()).setSelected(isChecked);
            }
        });
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow!=null) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }
                mFirebaseDatabase=FirebaseDatabase.getInstance();
                myRef=mFirebaseDatabase.getReference().child("Category");
                Log.e("TAG", "onClick: "+ viewHolder.heading.getText().toString() );
                ItemClickName=viewHolder.heading.getText().toString();
                LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View customView=inflater.inflate(R.layout.popup_layout,null);

                mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setOutsideTouchable(false);
                category=customView.findViewById(R.id.category);
                description=customView.findViewById(R.id.Description);
                veg=customView.findViewById(R.id.veg);
                Log.e("TAG", "onClick: "+myRef.getKey() );
                Log.e("TAG", "onClick: "+myRef.child(ItemClickName).getKey() );
                //myRef.child(ItemClickName);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DataSnapshot ds=dataSnapshot.child(ItemClickName);
                        //Log.e("TAG", "onDataChange: "+ds.getKey()+ds.getValue());
                        String Category=ds.child("Category").getValue(String.class);
                        String Description=ds.child("Description").getValue(String.class);
                        String Veg=ds.child("Veg").getValue(String.class);
                        Log.e("TAG", "onDataChange: "+Category+Description+Veg );
                        category.setText(Category);
                        description.setText(Description);
                        veg.setText(Veg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (Build.VERSION.SDK_INT   >=21){
                    mPopupWindow.setElevation(5.0f);
                }
                ImageButton closeButton=(ImageButton) customView.findViewById(R.id.ib_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAtLocation((View) v.getParent(),Gravity.CENTER,0,0);
            }
        });


    }



    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public String ItemClickName(){
        return ItemClickName;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView heading;
        public TextView desc;
        public ImageView imageView;
        public CheckBox checkBox;
        public RelativeLayout relativeLayout;
        public RelativeLayout mRelativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = (TextView) itemView.findViewById(R.id.heading);
            desc = (TextView) itemView.findViewById(R.id.desc);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.CheckBox1);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.relative);
            mRelativeLayout=(RelativeLayout) itemView.findViewById((R.id.r1));
        }
    }
}

