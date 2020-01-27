
package com.example.stapp.models;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "Data")
public class Data  {

    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("pId")
    private int pid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @PrimaryKey(autoGenerate =true)
    @SerializedName("id")
    private int mId;
    @SerializedName("last_name")
    private String mLastName;

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }




    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "mAvatar='" + mAvatar + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mId=" + mId +
                ", mLastName='" + mLastName + '\'' +
                '}';
    }



}
