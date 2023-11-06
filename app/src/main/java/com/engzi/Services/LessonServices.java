package com.engzi.Services;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.LessonPractice;
import com.engzi.Utils.FireBaseUtils;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class LessonServices {
    final CollectionReference mFCollection;

    public LessonServices() {
        mFCollection = FireBaseUtils.mFStore.collection("lessons");
    }

    //    Read Services
    public void getAllLessonList(IServiceCallBack callBack) {
        mFCollection.orderBy("lesson_name").get()
                .addOnCompleteListener(task -> {
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
                    LessonPractice lessonPractice = documentSnapshot.toObject(LessonPractice.class);
                    assert lessonPractice != null;
                    lessonPractice.setLessonID(lessonID);
                    callBack.retrieveData(lessonPractice);
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }
}
