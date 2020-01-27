package com.example.stapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stapp.adapter.RecycleAdapter;
import com.example.stapp.models.Data;
import com.example.stapp.models.Model;
import com.example.stapp.netwotk.Api;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecycleAdapter.OnDeleteClickListener {
    //views
    private RecyclerView            mrecyclerView;
    private MainActivityViewModel   mainActivityViewModel;
    private LinearLayoutManager     mlinearLayoutManager;
    private RecycleAdapter          mrecycleAdapter;
    private List<Data> mdatalist=   new ArrayList<>();
    private static final String TAG = "MainActivity";
    private static final int        PICK_IMAGE=1;
    private String                  image_url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!IsinternetConnected()){
            Toast.makeText(this,"Please check internet connection",Toast.LENGTH_LONG).show();
        }else {
            mrecyclerView = findViewById(R.id.recycler_view);
            mrecycleAdapter = new RecycleAdapter(this,this);
            mlinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mrecyclerView.setLayoutManager(mlinearLayoutManager);
            mrecyclerView.setAdapter(mrecycleAdapter);
            mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

            if (mdatalist == null || mdatalist.size() == 0)
                callRetro();
            else
                getData();
        }
    }

    private boolean IsinternetConnected()
    {

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
            return true;
            }
        } else {
            return false;
            // not connected to the internet
        }
        return false;
    }
    private void getData() {
        mainActivityViewModel.getmAlldata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {

                mrecycleAdapter.setData(data);
            }
        });
    }

    private void callRetro() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.getdata(1);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response.body().toString());
                    mdatalist = response.body().getData();
                    for (int i=0;i<mdatalist.size();i++)
                    mainActivityViewModel.insert(mdatalist.get(i));
                    getData();
//                    insertData(mdatalist);
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                calldialog();
                break;
        }
        return true;
    }
    private void calldialog() {

        Rect displayRectangle = new Rect();
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle(R.string.str_add_user);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_view, viewGroup, false);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));
        dialog.setContentView(dialogView);
        // set the custom dialog components - text, image and button
        ImageButton ib_close;
        Button btn_adduser;
        final EditText edt_id, edt_fname, edtlname, edt_email;
        final CircleImageView image ;

        image= dialog.findViewById(R.id.profileimage);
        ib_close = dialog.findViewById(R.id.ib_close);
//        edt_id = dialog.findViewById(R.id.edt_id);
        edt_fname = dialog.findViewById(R.id.edt_fname);
        edtlname = dialog.findViewById(R.id.edt_lastname);
        edt_email = dialog.findViewById(R.id.edt_email);
        btn_adduser = dialog.findViewById(R.id.btn_adduser);
        // if button is clicked, close the custom dialog
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });
        if (!image_url.equals(""))
        {
            Glide.with(this).load(image_url).into( image);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: in image view");
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , PICK_IMAGE);
            }
        });
        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_fname.getText().toString().equals(""))
                    edt_fname.setError("Please enter first name");
                else if (edtlname.getText().toString().equals(""))
                    edtlname.setError("Please enter last name");
                else if (!isValidEmail(edt_email.getText().toString().trim())) {
                    edt_email.setError("{Please enter valid email address");
                }else if (image_url.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"select Image",Toast.LENGTH_LONG).show();
                }
                else {
                    Data d = new Data();
                    d.setFirstName(edt_fname.getText().toString().trim());
                    d.setLastName(edtlname.getText().toString().trim());
                    d.setEmail(edt_email.getText().toString().trim());
                    d.setAvatar(image_url);
                    Log.d(TAG, "onClick: " + mdatalist.toString());
                    mainActivityViewModel.insert(d);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    image_url= String.valueOf(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    image_url= String.valueOf(selectedImage);
                }
                break;
        }
    }

    @Override
    public void OnDeleteClickListener(Data data) {
        //code for delete
        mainActivityViewModel.delete(data);

    }
}
