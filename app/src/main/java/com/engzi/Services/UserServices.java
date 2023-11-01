package com.engzi.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.User;
import com.engzi.Utils.FireBaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserServices {
    final CollectionReference mFCollection;

    public UserServices() {
        mFCollection = FireBaseUtil.mFStore.collection("users");
    }

    //    Create Services
    public void createUser(String profileName, String createdDate) {
        String UID = Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid();
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("profileName", profileName);
        userProfile.put("createdDate", createdDate);

        mFCollection.document(UID).set(userProfile)
                .addOnSuccessListener(aVoid -> Log.d("users", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("users", "Error writing document", e));
    }

    //    Read Services
    public void getUserById(IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid();
        mFCollection.document(UID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    callBack.retrieveData(documentSnapshot.toObject(User.class));
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }

    public void getRecentlyLesson(IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid();
        mFCollection.document(UID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    LessonServices lessonServices = new LessonServices();
                    List<String> recentlyLessons = (List<String>) documentSnapshot.get("recently_lessons");
                    assert recentlyLessons != null;
                    for (String lessonID : recentlyLessons) {
//                        Log.d("TAGGG", "getRecentlyLesson: " + lessonID);
                        lessonServices.getLessonByID(lessonID, new IServiceCallBack() {
                            @Override
                            public void retrieveData(Object response) {
                                callBack.retrieveData(response);
                            }

                            @Override
                            public void onFailed(Exception e) {
                                Log.w("get recently lessons by id", "onFailed: ", e);
                            }

                            @Override
                            public void onComplete() {
                                callBack.onComplete();
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("get recently lessons", "getRecentlyLesson: ", e);
                });
    }

    //    Update Services
    public void updateRecentlyLesson(@NonNull String lessonID) {
        String UID = Objects.requireNonNull(FireBaseUtil.mAuth.getCurrentUser()).getUid();
        Log.d("recently lesson ID", lessonID);
        mFCollection.document(UID).update("recently_lessons", FieldValue.arrayRemove(lessonID))
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d("update recently lesson", "updateRecentlyLesson: complete");
                    mFCollection.document(UID).update("recently_lessons", FieldValue.arrayUnion(lessonID));
                })
                .addOnFailureListener(e -> {
                    Log.w("update recently lesson", "updateRecentlyLesson: ", e);
                });
    }

    public void updateUserProfile(String field, String value, IServiceCallBack callBack) {
        String UID = FireBaseUtil.mAuth.getCurrentUser().getUid();
        mFCollection.document(UID).update(field, value)
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d("fasdfvv", "updateUserProfile: " + documentSnapshot.toString());
                })
                .addOnFailureListener(e -> {
                    Log.w("update pro5", "updateUserProfile " + field, e);
                });
    }
}
