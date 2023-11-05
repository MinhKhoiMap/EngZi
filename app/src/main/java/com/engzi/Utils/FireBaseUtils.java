package com.engzi.Utils;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireBaseUtils {
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore mFStore = FirebaseFirestore.getInstance();
    public static FirebaseStorage mStorage = FirebaseStorage.getInstance();

    public static StorageReference storageRef = mStorage.getReference();
    public static StorageReference avatarsRef = storageRef.child("images/avatar");

}
