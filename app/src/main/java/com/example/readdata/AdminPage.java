package com.example.readdata;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;
    public String UserID;
    public String uid="ndTxwkUcu0NPsZbPssHLglk4Y6y1";
    public String USER;
    private List<AdminHistory> adminHistories;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Button confirm;
    private Button deny;
    private Button Delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        mFirebaseAuth=FirebaseAuth.getInstance();
        recyclerView=(RecyclerView) findViewById(R.id.AdminRecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView USERID=(TextView) headerView.findViewById(R.id.user_id);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference().child("Order");

        adminHistories=new ArrayList<>();

        confirm=findViewById(R.id.Confirm);
        deny=findViewById(R.id.Denied);
        Delete=findViewById(R.id.Delete);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
  //              adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
    //            adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
      //          adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*
        for (int i=0;i<10;i++){
            AdminHistory adminHistory=new AdminHistory(
                    "order0",
                    "hi",
                    "hello",
                    "text",
                    "fs",
                    "re",
                    "wwre",
                    true
            );
            adminHistories.add(adminHistory);
        }
*/

        adapter=new AdminAdapter(adminHistories,this);
        recyclerView.setAdapter(adapter);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String userID=mFirebaseAuth.getCurrentUser().getUid();*//*
                DatabaseReference MYREF=mFirebaseDatabase.getReference().child("Order");*/

             //   Log.e("TAG", "onClick: "+ref );
                //Log.e("hi", "onClick: "+MYREF );
                Toast.makeText(getApplicationContext(),"Order confirmed",Toast.LENGTH_SHORT).show();
                for (AdminHistory adminHistory:adminHistories){
                    if (adminHistory.isSelected()){
                        String UserUID=adminHistory.getUUID();
                        String ID=adminHistory.getUserId();
                        DatabaseReference MYREF=mFirebaseDatabase.getReference().child("Order").child(UserUID).child(ID);
                        MYREF.child("Status").setValue("Confirm");
                        Log.e("TAG", "onClick: "+MYREF );
                    }
                //    adapter.notifyDataSetChanged();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=mFirebaseAuth.getCurrentUser().getUid();
                //DatabaseReference MYREF=mFirebaseDatabase.getReference().child("Order").child(userID);
                //Log.e("hi", "onClick: "+MYREF );
                Toast.makeText(getApplicationContext(),"Order Denied",Toast.LENGTH_SHORT).show();
                for (AdminHistory adminHistory:adminHistories){
                    if (adminHistory.isSelected()){
                        String UserUID=adminHistory.getUUID();
                        String ID=adminHistory.getUserId();
                        DatabaseReference MYREF=mFirebaseDatabase.getReference().child("Order").child(UserUID).child(ID);
                        MYREF.child("Status").setValue("Denied");
                        Log.e("TAG", "onClick: "+MYREF );
                    }
                //    adapter.notifyDataSetChanged();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=mFirebaseAuth.getCurrentUser().getUid();
                //DatabaseReference MYREF=mFirebaseDatabase.getReference().child("Order").child(userID);
                //Log.e("hi", "onClick: "+MYREF );
                Toast.makeText(getApplicationContext(),"Food item deleted",Toast.LENGTH_SHORT).show();
                for (AdminHistory adminHistory:adminHistories){
                    if (adminHistory.isSelected()){
                        String UserUID=adminHistory.getUUID();
                        String ID=adminHistory.getUserId();
                        DatabaseReference MYREF=mFirebaseDatabase.getReference().child("Order").child(UserUID).child(ID);
                        MYREF.removeValue();
                        Log.e("TAG", "onClick: "+MYREF );
                    }
                //    adapter.notifyItemChanged(adapter.getItemCount(),adminHistory.getStatus());
                    finish();
                    startActivity(getIntent());
                }
            }
        });

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
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                            finish();
                        }
//                        if (id==R.id.nav_order){
//                            startActivity(new Intent(getApplicationContext(),OrderMainPage.class));
//                            Toast.makeText(getApplicationContext(),"Order",Toast.LENGTH_SHORT).show();
//                        }
                        if (id==R.id.nav_history){
                            Toast.makeText(getApplicationContext(),"Order history",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),OrderPage.class));
                            finish();
                        }


                        if (id==R.id.nav_about){
                            Toast.makeText(getApplicationContext(),"About us",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AboutUs.class));
                            finish();
                        }

                        if (id==R.id.nav_contact){
                            Toast.makeText(getApplicationContext(),"Contact us",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ContactUs.class));
                            finish();
                        }

                        if(id==R.id.nav_logout){
                            Toast.makeText(getApplicationContext(),"Signed out!",Toast.LENGTH_SHORT).show();
                            Menu m1=navigationView.getMenu();
                            m1.findItem(R.id.nav_admin).setVisible(false);
                            signOut();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }


                        return true;
                    }
                });

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user!=null){
                    //Toast.makeText(getApplicationContext(),"Already Signed in",Toast.LENGTH_LONG).show();

                    UserID=user.getDisplayName();
                    USERID.setText(UserID);
                    USER=user.getUid();
                    Log.e("TAG", "onAuthStateChanged: "+USER );
                    if (uid.equals(user.getUid())) {
         //               Toast.makeText(getApplicationContext(), "Admin login", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onAuthStateChanged: Admmin login");
                        Log.d("TAG", "onAuthStateChanged: ADMIN");
                        USERID.setText("ADMIN");
                        Menu m1=navigationView.getMenu();
                        m1.findItem(R.id.nav_admin).setVisible(true);
                    }
                    onSignedInInitialize(user.getUid());
                }
                else{
                    onSignedOutCleanup();
                /*    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                */}
            }
        };

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        mDrawerLayout = findViewById(R.id.drawer_layout_admin);
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode==RESULT_OK){
         //       Toast.makeText(getApplicationContext(),"Signed In!",Toast.LENGTH_SHORT).show();
            }else if (resultCode==RESULT_CANCELED){
         //       Toast.makeText(getApplicationContext(),"Sign in cancelled",Toast.LENGTH_SHORT).show();
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

    private void showData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Log.e("hi", "showData: "+ds.getKey());
            for (DataSnapshot ds1 : ds.getChildren()) {
                AdminHistory i = ds1.getValue(AdminHistory.class);
                adminHistories.add(i);
                DatabaseReference id = ds1.getRef();
                Log.e("hi", "showData: "+ds1.getValue());
                Log.e("hi", String.valueOf(id));
            }
        }
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
        final String mUID=username;


    }

    public void onSignedOutCleanup(){

    }

    public void signOut(){
        AuthUI.getInstance().signOut(this);
    }
}



