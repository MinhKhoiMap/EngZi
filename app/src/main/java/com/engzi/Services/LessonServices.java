package com.engzi.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.engzi.Model.LessonPractice;
import com.engzi.Utils.FireBaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LessonServices {
    final CollectionReference mFCollection;

    public LessonServices() {
        mFCollection = FireBaseUtil.mFStore.collection("lessons");
    }

    public void getAllLessonList() {
        mFCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("lessons", document.getId() + " " + document.getData().get("topic_name"));
                    }
                } else {
                    Log.w("lessons", "Error getting documents", task.getException());
                }
            }
        });
    }

    //    Nghi cach de tra ve LessonPractice object
    public void getLessonByName(String topicName) {
        mFCollection.whereEqualTo("topic_name", topicName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("lessons", document.getId() + " " + document.getData());
                        Log.d("lessons object", document.getId() + " " + document.toObject(LessonPractice.class).getList_cards().get(0));
                    }
                } else {
                    Log.w("lessons", "Error getting documents", task.getException());
                }
            }
        });
    }

    public void updateRecentlyLesson(String lessionID) {
        mFCollection.document(lessionID).get()
                .addOnSuccessListener(documentSnapshot -> {
                })
                .addOnFailureListener(e -> {
                });
    }
}
