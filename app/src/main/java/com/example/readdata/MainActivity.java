package com.example.readdata;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
/*import com.google.firebase.database.core.Tag;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    public static final String OrderDetails = "com.example.MainActivity.Data";
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private DatabaseReference myRef3;
    private String ItemName = "Item";
    private TextView TodaySpecial;
    private TextView Item_name;
    private CardView specialCard;
    private TextView specialItemName;
    private CardView customCard;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;
    private TextView userId;
    public String UserID;
    public LinearLayout specialLinear;
    public ImageView specialImage;
    public String uid="ndTxwkUcu0NPsZbPssHLglk4Y6y1";

    private List<listItem> listItems;
   // private List<listItem> selectedItem;
    private ArrayList<String> selectedItem = new ArrayList<>();

    private int Total;
    private int k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Item_name=findViewById(R.id.Head1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TodaySpecial =findViewById(R.id.itemName);
        specialItemName=findViewById(R.id.itemName);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Item");
        myRef2=mFirebaseDatabase.getReference().child("Order");
        myRef3=mFirebaseDatabase.getReference().child("TodaySpecial");
        specialCard=findViewById(R.id.TodaySpecial);
        customCard=(CardView) findViewById(R.id.CustomCard);
       // specialLinear=(LinearLayout) findViewById(R.id.SpecialLinear);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerVIew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userId=findViewById(R.id.user_id);
        listItems = new ArrayList<>();
        specialImage=findViewById(R.id.imageView);
        //    for (int i=0;i<10;i++){
        //        listItem listItem = new listItem("Heading " +(i+1), "Rs");
        //        listItems.add(listItem);
        //    }

        //    adapter = new MyAdapter(listItems, this);

        //    recyclerView.setAdapter(adapter);





        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );



        final NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView USERID=(TextView) headerView.findViewById(R.id.user_id);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        int id = menuItem.getItemId();
                        // set item as selected to persist highlight

                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if (id==R.id.nav_home){
                            Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        }
//                        if (id==R.id.nav_order){
//                            startActivity(new Intent(getApplicationContext(), OrderMainPage.class));
//                            Toast.makeText(getApplicationContext(),"Order",Toast.LENGTH_SHORT).show();
//                        }
                        if (id==R.id.nav_history){
                            startActivity(new Intent(getApplicationContext(), OrderPage.class));
                            Toast.makeText(getApplicationContext(),"Order History",Toast.LENGTH_SHORT).show();

                        }

                        if (id==R.id.nav_about){
                            Toast.makeText(getApplicationContext(),"About us",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AboutUs.class));
                        }

                        if (id==R.id.nav_contact){
                            Toast.makeText(getApplicationContext(),"Contact us",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ContactUs.class));
                        }

                        if(id==R.id.nav_logout){
                            Toast.makeText(getApplicationContext(),"Signed out!",Toast.LENGTH_SHORT).show();

                            Menu m1=navigationView.getMenu();
                            m1.findItem(R.id.nav_admin).setVisible(false);
                            signOut();
                        }
                        if (id==R.id.nav_admin){
                            Toast.makeText(getApplicationContext(),"Admin page",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AdminPage.class));
                        }

                        return true;
                    }
                });

        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("TAG", "onDataChange: "+dataSnapshot.getKey() );
                Log.e("TAG", "onDataChange: "+dataSnapshot.child("name").getValue() );
                String TodaySpecial1 = dataSnapshot.child("name").getValue(String.class);
                String ImageUrl = dataSnapshot.child("ImageUrl").getValue(String.class);
                TodaySpecial.setText(TodaySpecial1);
                Picasso.get()
                        .load(ImageUrl)
                        .fit()
                        .into(specialImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        specialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Log.e("TAG", "onClick: ");
                String selectItem=specialItemName.getText().toString();
                Log.e("TAG", "onClick: "+selectItem );
                for (listItem listItem : listItems){
                    Log.e("TAG", "onClick: "+listItem.getName() );
                    if (listItem.getName().toString().equalsIgnoreCase(selectItem)){
                        Log.e("TAG", "onClick: " );
                        listItem.setSelected(true);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });



        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Total=0;
                StringBuilder stringBuilder = new StringBuilder();
                for (listItem listItem : listItems){
                    if (listItem.isSelected()){
                        if (stringBuilder.length()>0){
                            stringBuilder.append(", ");
                        }
                        Total = Total+ Integer.parseInt(listItem.getPrice().replaceAll("\\D",""));
                        selectedItem.add(listItem.getName());
                        stringBuilder.append(listItem.getName());
                    }
                }
                if (selectedItem.isEmpty()==true)
                    Toast.makeText(getApplicationContext(), "Please select an item", Toast.LENGTH_SHORT).show();
                else {
                    Log.e("TAG", "onClick: " + selectedItem);
                    Log.e("TAG", "" + Total);
                    stringBuilder.append("\nYour Total is: " + Total);
                    sendMessage(view);
                    selectedItem.clear();
                }

        //        Toast toast = Toast.makeText(getApplicationContext(), "order placed", Toast.LENGTH_LONG);
        //        toast.show();
            }
        });

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user!=null){
                    String user1=user.getUid().toString();
                    Log.e("TAG", "onAuthStateChanged: "+user1 );

                    //Toast.makeText(getApplicationContext(),"Already Signed in",Toast.LENGTH_LONG).show();
                    onSignedInInitialize(user.getDisplayName());
                    Log.e("TAG", "onAuthStateChanged: "+user.getDisplayName()+"\n"+user.getUid() );
                    /*userId.setText(user.getDisplayName().toString());*/
                    UserID=user.getDisplayName();
                    USERID.setText(UserID);
                    if (uid.equals(user.getUid())) {
                //        Toast.makeText(getApplicationContext(), "Admin login", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onAuthStateChanged: Admmin login");
                        Log.d("TAG", "onAuthStateChanged: ADMIN");
                        USERID.setText("ADMIN");
                        Menu m1=navigationView.getMenu();
                        m1.findItem(R.id.nav_admin).setVisible(true);
                    }
                }
                else{
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
//        userId.setText(UserID);

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            if(requestCode==RESULT_OK){
                Toast.makeText(getApplicationContext(),"Signed in!",Toast.LENGTH_SHORT).show();
            } else if (requestCode==RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Sign in cancelled",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(),"Signed In!",Toast.LENGTH_SHORT).show();
            }else if (resultCode==RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Sign in cancelled",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, OrderMainPage.class);
        intent.putExtra(OrderDetails,selectedItem);
        intent.putExtra("total1",Total);
        startActivity(intent);
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //    itemInformation iInfo = new itemInformation();
            //    iInfo.setName(ds.child(ItemName).getValue(itemInformation.class).getName());
            //    iInfo.setPrice(ds.child(ItemName).getValue(itemInformation.class).getPrice());
            //    listItem item= new listItem(
            //            iInfo.getName(),
            //            iInfo.getPrice()
            //    );
            //    listItems.add(item);
            listItem i = ds.getValue(listItem.class);
            listItems.add(i);
            DatabaseReference id = ds.getRef();
            Log.e("hi", String.valueOf(id));
        }
        adapter = new MyAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSignedInInitialize(String username){
        String mUsername=username;
    }

    public void onSignedOutCleanup(){

    }

    public void signOut(){
        AuthUI.getInstance().signOut(this);
    }
}