package com.example.tolkhub.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tolkhub.ChatList_activity;
import com.example.tolkhub.Model.AuthenticationModel;
import com.example.tolkhub.Model.ContactListModel;

import com.example.tolkhub.Model.messageContactModel;
import com.example.tolkhub.Model.userModel;
import com.example.tolkhub.R;
import com.example.tolkhub.Util.FirebaseUtil;
import com.example.tolkhub.chatFregment;
import com.example.tolkhub.contact_list_fagment;
import com.example.tolkhub.retrofit.authApi;
import com.example.tolkhub.retrofit.retrofitService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class contactList_Recycler_Adapter extends RecyclerView.Adapter<contactList_Recycler_Adapter.ContactViewHolder> {

   private Context context;

   private List<ContactListModel> contactModel;
   private retrofitService retrofitService;
    AuthenticationModel authModel;
    contactListClick listener;
    Application application;
    String receiverPhoneNumber;

    public contactList_Recycler_Adapter(Context context,List<ContactListModel> contactModel,contactListClick listener,String receiverPhoneNumber) {
        this.context = context;
        this.contactModel=contactModel;
        this.listener=listener;
        this.receiverPhoneNumber=receiverPhoneNumber;
    }

    public void setFilter(List<ContactListModel> filterList) {
        this.contactModel=filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_recycler_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactListModel contactList = contactModel.get(position);
//        holder.contactImageView.setImageResource(contactList.getContactImg());
        holder.numberTextView.setText(contactList.getContactNumber());
        holder.nameTextView.setText(contactList.getContactName());


        holder.itemView.setOnClickListener(v->{
String contactNumber =contactList.getContactNumber();
String contactName = contactList.getContactName();
            Toast.makeText(context,contactNumber, Toast.LENGTH_SHORT).show();

            FirebaseUtil.fireBaseQuarry(contactNumber).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<userModel> userModels = queryDocumentSnapshots.toObjects(userModel.class);
                    AppCompatActivity  appCompatActivity= (AppCompatActivity)v.getContext();

                    if(!userModels.isEmpty()) {
                            userModel user = userModels.get(0); // Assuming you want the first match
                            Toast.makeText(context, user.getUsername(), Toast.LENGTH_SHORT).show();
                            callFragment(user.getUsername(),user.getPhone(),appCompatActivity,user.getUserId());
                        }
                        else {
                            Toast.makeText(context, "Not foun data in the fireStore Database", Toast.LENGTH_SHORT).show();
                        }

                    }

            });




        });

    }

    @Override
    public int getItemCount() {
        return contactModel.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView,numberTextView;
        ImageView contactImageView;


            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView=itemView.findViewById(R.id.ContactNameTView);
                numberTextView=itemView.findViewById(R.id.ContactNumberTView);
                contactImageView = itemView.findViewById(R.id.profile_image);

            }


        }



    public interface contactListClick {
void onItemClick(String name,String senderNumber);
messageContactModel findByphoneNumber(String senderPhoneNumber);

    }

    public void callFragment(String name, String phoneN, AppCompatActivity  appCompatActivity, String userId) {

        chatFregment myFragment = new chatFregment();
        FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, myFragment);
        fragmentTransaction.commit();
        Bundle bundle =new Bundle();
        bundle.putString("name",name);
        bundle.putString("phone",phoneN);
        bundle.putString("otherUserId",userId);
        myFragment.setArguments(bundle);
    }




}
