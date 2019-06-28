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
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderMainPage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private Button orderButton;
    private EditText Details;
    private ArrayList<String> selectedItem = new ArrayList<>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private int k=0;
    private int Total=0;
    private CheckBox cheese;
    private CheckBox butter;
    private CheckBox jain;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;
    public String UserID;
    public String uid="ndTxwkUcu0NPsZbPssHLglk4Y6y1";
    public String USER;

    //Date of order
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c = Calendar.getInstance();
    String date = sdf.format(c.getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Order");
        mFirebaseAuth=FirebaseAuth.getInstance();
        cheese=findViewById(R.id.cheese);
        butter=findViewById(R.id.butter);
        jain=findViewById(R.id.Jain);

        mDrawerLayout=findViewById(R.id.drawer_layout);
        orderButton=findViewById(R.id.OrderButton);
        Details = findViewById(R.id.editText);

        Intent intent =  getIntent();
        if (intent.getStringArrayListExtra(MainActivity.OrderDetails)!=null) {
            selectedItem = intent.getStringArrayListExtra(MainActivity.OrderDetails);
            Total = getIntent().getExtras().getInt("total1");
            for (int i = 0; i < selectedItem.size(); i++) {
                Log.e("TAG", "Data" + selectedItem.get(i).toString());
                //    myRef2.child("User1").child("Order " + k++).setValue(selectedItem.get(i));
            }
        }

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


        final ArrayAdapter<String> myArrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,selectedItem);
        mListView = (ListView) findViewById(R.id.List);
        mListView.setAdapter(myArrayAdapter);

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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                            finish();
                        }
//                        if (id==R.id.nav_order){
//                         //   startActivity(new Intent(getApplicationContext(), OrderPage.class));
//                            Toast.makeText(getApplicationContext(),"Order",Toast.LENGTH_SHORT).show();
//                        }
                        if (id==R.id.nav_history){
                            startActivity(new Intent(getApplicationContext(), OrderPage.class));
                            Toast.makeText(getApplicationContext(),"Order History",Toast.LENGTH_SHORT).show();
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
                        if (id==R.id.nav_admin){
                            Toast.makeText(getApplicationContext(),"Admin page",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AdminPage.class));
                            finish();
                        }
                        return true;
                    }
                });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Long Press to delete selected item",Toast.LENGTH_LONG).show();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray positionChecker = mListView.getCheckedItemPositions();
                int count = mListView.getCount();
                for (int item=count-1;item>=0;item--){
                    if (positionChecker.get(item)){
                        myArrayAdapter.remove(selectedItem.get(item));
                    }
                }
                positionChecker.clear();
                myArrayAdapter.notifyDataSetChanged();
                return false;
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem.isEmpty())
                    Toast.makeText(getApplicationContext(),"Please select order",Toast.LENGTH_SHORT).show();
                else {
                    String details=Details.getText().toString();
                    if (cheese.isChecked()) {
                        Log.e("TAG", "onClick: Cheese");
                        Total=Total+15;
                        details=details+" Cheese ";
                    }
                    if (butter.isChecked()) {
                        Log.e("TAG", "onClick: butter");
                        Total=Total+10;
                        details=details+" Butter ";
                    }
                    if (jain.isChecked()){
                        Total=Total+10;
                        details=details+" Jain ";
                    }
                    myRef=myRef.child(USER);
                    myRef=myRef.push();
                    k=0;
                    for (int i = 0; i < selectedItem.size(); i++) {
                        myRef.child("Order" + k++).setValue(selectedItem.get(i));
                        myRef.child("Special Requirement").setValue(details);
                        myRef.child("Date").setValue(date);
                        myRef.child("Total").setValue("Rs "+Total);
                        myRef.child("Status").setValue("Not confirm");
                        myRef.child("User").setValue(UserID);
                        myRef.child("UserId").setValue(myRef.getKey());
                        myRef.child("UUID").setValue(myRef.getParent().getKey());
                        Log.e("TAG", "onClick: "+UserID );
                        Toast.makeText(getApplicationContext(),"Order placed. \nTotal is "+Total,Toast.LENGTH_SHORT).show();
                    }
                    selectedItem.clear();
                    finish();
                }
            }
        });
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user!=null){
                    //Toast.makeText(getApplicationContext(),"Already Signed in",Toast.LENGTH_LONG).show();
                    onSignedInInitialize(user.getDisplayName());
                    UserID=user.getDisplayName();
                    USER=user.getUid();
                    USERID.setText(UserID);
                    if (uid.equals(user.getUid())) {
       //                 Toast.makeText(getApplicationContext(), "Admin login", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onAuthStateChanged: Admmin login");
                        Log.d("TAG", "onAuthStateChanged: ADMIN");
                        USERID.setText("ADMIN");
                        Menu m1=navigationView.getMenu();
                        m1.findItem(R.id.nav_admin).setVisible(true);
                    }
                }
                else{
                    onSignedOutCleanup();
              /*      startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
              */  }
            }
        };

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode==RESULT_OK){
      //          Toast.makeText(getApplicationContext(),"Signed In!",Toast.LENGTH_SHORT).show();
            }else if (resultCode==RESULT_CANCELED){
      //          Toast.makeText(getApplicationContext(),"Sign in cancelled",Toast.LENGTH_SHORT).show();
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
