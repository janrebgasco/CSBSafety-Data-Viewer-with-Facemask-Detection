package com.ucc.csbsafetydataviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucc.csbsafetydataviewer.customview.AutoFitTextureView;

import java.nio.file.Files;

public class dataViewer extends AppCompatActivity {
    TextView fname,lname,status,studentNum,userType,textView3,textView5,textView2,textView4,textView;
    ImageView profilePic,imgQR;
    TextView txtTemp,textView6;
    AutoFitTextureView frameLayout2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_viewer);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        status = findViewById(R.id.status);
        profilePic = findViewById(R.id.userImg);
        studentNum = findViewById(R.id.txtStudentNum);
        userType = findViewById(R.id.txtUsertype);
        txtTemp = findViewById(R.id.txtTemp);
        textView6 = findViewById(R.id.textView6);
        imgQR = findViewById(R.id.imgQR);
        textView3 = findViewById(R.id.textView3);
        textView5 = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        textView = findViewById(R.id.textView);
        frameLayout2 = findViewById(R.id.texture);

         final MediaPlayer beep = MediaPlayer.create(dataViewer.this, R.raw.beep);
         final MediaPlayer TempTooHigh = MediaPlayer.create(dataViewer.this, R.raw.temptoohighvoice);
         final MediaPlayer NormalTemp = MediaPlayer.create(dataViewer.this, R.raw.normaltemp);

//        String esName = "UCC";
//        getUserThatScanned(esName,beep,TempTooHigh,NormalTemp);
//        checkUserStatus(beep,TempTooHigh,NormalTemp);
//
//        initializeData(esName);

    }
    private void checkUserStatus(MediaPlayer beep, MediaPlayer TempTooHigh,MediaPlayer NormalTemp) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("userTemp/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double userTemp = (Double) dataSnapshot.child("Temp").getValue();

                if(userTemp != null) {
                    beep.start();
                    if(userTemp >= 37.8) {
                        txtTemp.setTextColor(Color.parseColor("#FFFF0000"));

                        delaySound(TempTooHigh);


                    }else{
                        txtTemp.setTextColor(Color.parseColor("#FF018786"));

                        delaySound(NormalTemp);
                    }
                    txtTemp.setText(userTemp+"°");
                }else{
                    txtTemp.setVisibility(View.INVISIBLE);
                    textView6.setVisibility(View.INVISIBLE);
                }

                new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        removeUserData();
                    }
                }.start();

            }

            private void delaySound(MediaPlayer sound) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms

                        sound.start();
                    }
                }, 1000);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getUserThatScanned(String establishment,MediaPlayer beep, MediaPlayer TempTooHigh,MediaPlayer NormalTemp) {

        // Get a reference to your user
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("establishment/"+ establishment +"/userData");

        // Attach a listener to read the data at your profile reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Fname = String.valueOf(dataSnapshot.child("FName").getValue());
                String LName = String.valueOf(dataSnapshot.child("LName").getValue());
                String userStatus = String.valueOf(dataSnapshot.child("status").getValue());
                String img = String.valueOf(dataSnapshot.child("userImg").getValue());
                String StudentNum = String.valueOf(dataSnapshot.child("StudentNum").getValue());
                String Usertype = String.valueOf(dataSnapshot.child("Usertype").getValue());

                if (!Fname.equals("Empty")){
                    textView.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.VISIBLE);
                    fname.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    lname.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                    status.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    userType.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                    studentNum.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.VISIBLE);
                    txtTemp.setVisibility(View.VISIBLE);
                    profilePic.setVisibility(View.VISIBLE);
                    imgQR.setVisibility(View.INVISIBLE);
                    //frameLayout2.setVisibility(View.INVISIBLE);


                    fname.setText(Fname);
                    lname.setText(LName);
                    status.setText(userStatus);
                    studentNum.setText(StudentNum);
                    userType.setText(Usertype);
                    if (img != null) {
                        try {
                            Glide.with(dataViewer.this)
                                    .load(img)
                                    .into(profilePic);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (userStatus.equals("positive")){
                        fname.setTextColor(Color.parseColor("#FFFF0000"));
                        lname.setTextColor(Color.parseColor("#FFFF0000"));
                        status.setTextColor(Color.parseColor("#FFFF0000"));
                        studentNum.setTextColor(Color.parseColor("#FFFF0000"));
                        userType.setTextColor(Color.parseColor("#FFFF0000"));
                    }else{
                        fname.setTextColor(Color.parseColor("#FF018786"));
                        lname.setTextColor(Color.parseColor("#FF018786"));
                        status.setTextColor(Color.parseColor("#FF018786"));
                        studentNum.setTextColor(Color.parseColor("#FF018786"));
                        userType.setTextColor(Color.parseColor("#FF018786"));
                    }


                }else{
                    textView.setVisibility(View.INVISIBLE);
                    textView6.setVisibility(View.INVISIBLE);
                    fname.setVisibility(View.INVISIBLE);
                    textView3.setVisibility(View.INVISIBLE);
                    lname.setVisibility(View.INVISIBLE);
                    textView5.setVisibility(View.INVISIBLE);
                    status.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    userType.setVisibility(View.INVISIBLE);
                    textView4.setVisibility(View.INVISIBLE);
                    studentNum.setVisibility(View.INVISIBLE);
                    textView6.setVisibility(View.INVISIBLE);
                    txtTemp.setVisibility(View.INVISIBLE);
                    profilePic.setVisibility(View.INVISIBLE);
                    imgQR.setVisibility(View.VISIBLE);
                    //frameLayout2.setVisibility(View.VISIBLE);
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    private void initializeData(String establishment) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("establishment/"+ establishment);

        User user = new User("Empty", "Empty","Empty","","Empty","Empty");

        mDatabase.child("userData").setValue(user);


            textView.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
            fname.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            lname.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            userType.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            studentNum.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
            txtTemp.setVisibility(View.GONE);
            profilePic.setVisibility(View.GONE);
            imgQR.setVisibility(View.VISIBLE);
            //frameLayout2.setVisibility(View.VISIBLE);
    }
    private void removeUserData() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("establishment/UCC");

        User user = new User("Empty", "Empty","Empty","","Empty","Empty");

        mDatabase.child("userData").setValue(user);
    }


}