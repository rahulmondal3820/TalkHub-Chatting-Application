package com.example.tolkhub;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tolkhub.Adapter.contactList_Recycler_Adapter;
import com.example.tolkhub.Model.ContactListModel;
import com.example.tolkhub.Model.messageContactModel;

import java.util.ArrayList;
import java.util.List;

public class contact_list_fagment extends Fragment implements contactList_Recycler_Adapter.contactListClick {

    private static final String TAG = "ContactListFragment";
    RecyclerView contactRecyclerView;
    List<ContactListModel> contactListModels;
    contactList_Recycler_Adapter myAdapter;
    SearchView contactSearchView;

    public contact_list_fagment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Fragment created");

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.d(TAG, "handleOnBackPressed: Back button pressed");
                // Handle the back button event
                Intent intent = new Intent(requireActivity(), ChatList_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish(); // Close the current activity
                Toast.makeText(getContext(), "Back to Chat List", Toast.LENGTH_SHORT).show();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        Log.d(TAG, "onCreate: OnBackPressedCallback added");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list_fagment, container, false);
        Log.d(TAG, "onCreateView: View created");

        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String userId = pref.getString("phoneNumber", null);

        contactRecyclerView = view.findViewById(R.id.ContactListRecycler);
        contactSearchView = view.findViewById(R.id.SearchView);
        contactSearchView.clearFocus();

        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contactListModels = new ArrayList<>();
        myAdapter = new contactList_Recycler_Adapter(getContext(), contactListModels, this, userId);
        contactRecyclerView.setAdapter(myAdapter);

        contactSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterArrList(newText);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: View created and data initialized");
        dataInisilize();
    }

    public void dataInisilize() {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI}, // Specify the columns you want to retrieve
                null,
                null,
                null
        );

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int imageIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);

            if (nameIndex >= 0 && numberIndex >= 0) { // Check if necessary columns exist
                while (cursor.moveToNext()) {
                    String contactName = cursor.getString(nameIndex);
                    String contactNumberWithSpaces = cursor.getString(numberIndex);
                    String contactNumber = contactNumberWithSpaces.replaceAll("\\s", "");
                    String contactImageUri = cursor.getString(imageIndex);

                    contactListModels.add(new ContactListModel(contactName, contactImageUri, contactNumber));
                }
            } else {
                // Handle case when necessary columns don't exist
                if (nameIndex < 0) {
                    Log.e(TAG, "DISPLAY_NAME column not found");
                }
                if (numberIndex < 0) {
                    Log.e(TAG, "NUMBER column not found");
                }
            }
            cursor.close();
        }
    }

    private void filterArrList(String text) {
        List<ContactListModel> filterList = new ArrayList<>();
        for (ContactListModel item : contactListModels) {
            if (item.getContactName().toLowerCase().contains(text.toLowerCase()) || item.getContactNumber().contains(text)) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(getContext(), "Not found number", Toast.LENGTH_SHORT).show();
            contactRecyclerView.setVisibility(View.GONE);
        } else {
            myAdapter.setFilter(filterList);
            contactRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(String name, String senderNumber) {
        // Handle item click
    }

    @Override
    public messageContactModel findByphoneNumber(String senderPhoneNumber) {
        return null;
    }
}
