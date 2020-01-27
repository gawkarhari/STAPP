package com.example.stapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.stapp.R;
import com.example.stapp.models.Data;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecyclerHolder> {
    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Data data);
    }

    //val declaration
    private Context mCtx;
    private List<Data> mdatalist;
    OnDeleteClickListener mOnDeleteClickListener;
    //tag
    private static final String TAG = "RecycleAdapter";
    private final LayoutInflater layoutInflater;
    private int mposition;

    //constructor
    public RecycleAdapter(Context ctx, OnDeleteClickListener OnDeleteClickListener) {
        layoutInflater = LayoutInflater.from(ctx);
        this.mCtx = ctx;
        this.mOnDeleteClickListener = OnDeleteClickListener;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.emp_layout, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, int position) {

        if (mdatalist != null) {

            Log.e(TAG, "onBindViewHolder: " + holder.getAdapterPosition());
//            Data d =mdatalist.get(position);
            Data p = mdatalist.get(holder.getAdapterPosition());
            holder.setData(p, position, holder.getAdapterPosition());
            holder.recordLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = holder.getAdapterPosition();
                    Log.e(TAG, "onClick: select" + i);
                    showRadioButtonDialog(i);
                }
            });
        } else {

            Toast.makeText(mCtx, R.string.no_record, Toast.LENGTH_LONG).show();
        }

    }

    public void setData(List<Data> data) {
        mdatalist = data;
        Log.e(TAG, "setData: " + mdatalist.toString());
        notifyDataSetChanged();
    }

    private void showRadioButtonDialog(final int mpos) {

        // custom dialog

        final Dialog dialog = new Dialog(mCtx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setTitle("Choose Operations");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        RadioButton radioButton;
        int id;
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ");
                int id = rg.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rb_edit:

                        break;
                    case R.id.rb_delete:
                        Log.e(TAG, "onClickdelete: " + mdatalist.get(mpos));
                        mOnDeleteClickListener.OnDeleteClickListener(mdatalist.get(mpos));
                        Toast.makeText(mCtx, "Record for" + mdatalist.get(mpos).getId() + "deleted successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                }


            }
        });
        dialog.show();
    }


    @Override
    public int getItemCount() {
        if (mdatalist != null)
            return mdatalist.size();
        else return 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //view declaration
        TextView txtid, txtfname, txtlname, txtemail;
        CircleImageView circleImageView;
        LinearLayout recordLayout;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            recordLayout = itemView.findViewById(R.id.recordLayout);
            txtid = itemView.findViewById(R.id.txt_id);
            txtfname = itemView.findViewById(R.id.txt_fname);
            txtlname = itemView.findViewById(R.id.txt_lname);
            txtemail = itemView.findViewById(R.id.txt_email);
            circleImageView = itemView.findViewById(R.id.profile_image);

        }

        public void setData(Data d, int position, int adapter_pos) {

            mposition = adapter_pos;
            txtid.setText(String.valueOf(d.getId()));
            txtfname.setText(d.getFirstName());
            txtlname.setText(d.getLastName());
            txtemail.setText("Email :" + d.getEmail());
            recordLayout.setOnClickListener(this);
            if (d.getAvatar() != null) {
                Glide.with(mCtx).load(d.getAvatar()).into(circleImageView);
            } else {
                Glide.with(mCtx).load(R.drawable.ic_person_black_24dp).into(circleImageView);
            }

        }

        @Override
        public void onClick(View v) {

        }


    }


}
