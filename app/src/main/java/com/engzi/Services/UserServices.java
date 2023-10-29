package com.engzi.Services;

import android.util.Log;

import com.engzi.Model.User;
import com.engzi.Utils.FireBaseUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserServices {
    final CollectionReference mFCollection;

    public UserServices() {
        mFCollection = FireBaseUtil.mFStore.collection("users");
    }

    public void createUser(String UID, String profileName, String createdDate) {
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("profileName", profileName);
        userProfile.put("createdDate", createdDate);

        mFCollection.document(UID).set(userProfile)
                .addOnSuccessListener(aVoid -> Log.d("users", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("users", "Error writing document", e));
    }

    public void getUserById(String UID) {
        mFCollection.document(UID).get()
                .addOnSuccessListener(documentSnapshot -> Log.d("users", documentSnapshot.toObject(User.class).getProfileName()))
                .addOnFailureListener(e -> Log.w("users", "Error get user by ID", e));
    }
}
