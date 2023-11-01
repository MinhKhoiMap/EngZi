package com.engzi.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.engzi.Interface.IServiceCallBack;
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

import java.util.ArrayList;
import java.util.List;

public class LessonServices {
    final CollectionReference mFCollection;

    public LessonServices() {
        mFCollection = FireBaseUtil.mFStore.collection("lessons");
    }

    //    Read Services
    public void getAllLessonList(IServiceCallBack callBack) {
        mFCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
//                        Log.d("lessons", document.toObject(LessonPractice.class).getTopic_name());
                    LessonPractice temp = document.toObject(LessonPractice.class);
                    temp.setLessonID(document.getId());
                    callBack.retrieveData(temp);
                }
                callBack.onComplete();
            } else {
                callBack.onFailed(task.getException());
            }
        });
    }

    public void getLessonByName(String topicName, IServiceCallBack callBack) {
        mFCollection.whereEqualTo("topic_name", topicName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
//                        Log.d("lessons object", document.getId() + " " + document.toObject(LessonPractice.class).getList_cards().get(0));
                            callBack.retrieveData(document.toObject(LessonPractice.class));
                        }
                        callBack.onComplete();
                    } else {
//                    Log.w("lessons", "Error getting documents", task.getException());
                        callBack.onFailed(task.getException());
                    }
                });
    }

    public void getLessonByID(String lessonID, IServiceCallBack callBack) {
        mFCollection.document(lessonID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    callBack.retrieveData(documentSnapshot.toObject(LessonPractice.class));
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }
}
