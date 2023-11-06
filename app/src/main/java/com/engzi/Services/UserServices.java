package com.engzi.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.LessonPractice;
import com.engzi.Model.User;
import com.engzi.Utils.DateToTimestamp;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserServices {
    final CollectionReference mFCollection;
    final String avatarDefault = "https://firebasestorage.googleapis.com/v0/b/test-865b1.appspot.com/o/images%2Favatar%2Ficon.png?alt=media&token=e17b12fe-b7eb-4f35-b846-8b3d2f13da23";

    public UserServices() {
        mFCollection = FireBaseUtils.mFStore.collection("users");
    }

    //    Create Services
    public void createUser(String profileName, String createdDate, IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("profileName", profileName);
        userProfile.put("createdDate", createdDate);
        userProfile.put("avatarImg", avatarDefault);

        Map<String, List<String>> userNoteBook = new HashMap<>();
        userNoteBook.put("level_1", new ArrayList<>());
        userNoteBook.put("level_2", new ArrayList<>());
        userNoteBook.put("level_3", new ArrayList<>());

        mFCollection.document(UID).set(userProfile)
                .addOnSuccessListener(unused -> {
                    NoteBookServices noteBookServices = new NoteBookServices();
                    noteBookServices.mFCollection.document(UID).set(userNoteBook)
                            .addOnSuccessListener(unused1 -> {
                                Log.d("TAG", "createUser: ");
                                callBack.onComplete();
                            });
                })
                .addOnFailureListener(e -> Log.w("users", "Error writing document", e));
    }

    //    Read Services
    public void getUserById(IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        mFCollection.document(UID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    user.setEmail(Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getEmail());
                    callBack.retrieveData(user);
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }

    public void getLastPositionCardLesson(String lessonID, IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        mFCollection.document(UID)
                .collection("recently")
                .document(lessonID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot != null)
                        callBack.retrieveData(documentSnapshot.get("last_position_card"));
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }

    public void getRecentlyLessonList(IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        LessonServices lessonServices = new LessonServices();
        mFCollection
                .document(UID)
                .collection("recently")
                .orderBy("last_access", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.size() > 0) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.d("TAGHEHEHEE", documentSnapshot.getData().toString() + documentSnapshot.getId());
                            lessonServices.getLessonByID(documentSnapshot.getId(), new IServiceCallBack() {
                                @Override
                                public void retrieveData(Object response) {
                                    LessonPractice lessonPractice = (LessonPractice) response;
                                    lessonPractice.setLessonID(documentSnapshot.getId());
                                    long last_position_card = (long) documentSnapshot.get("last_position_card");
                                    lessonPractice.setLast_position_card((int) last_position_card);

                                    callBack.retrieveData(lessonPractice);
                                }

                                @Override
                                public void onFailed(Exception e) {
                                    callBack.onFailed(e);
                                }

                                @Override
                                public void onComplete() {
                                    callBack.onComplete();
                                }
                            });
                        }
                    } else {
                        callBack.onComplete();
                    }
                })
                .addOnFailureListener(callBack::onFailed);
    }

    //    Update Services
    public void updateRecentlyLesson(@NonNull String lessonID, int percentComplete) {
        Log.d("Recently", "updateRecentlyLesson:" + lessonID);
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        Map<String, Object> recentlyLesson = new HashMap<>();
        recentlyLesson.put("last_position_card", percentComplete);
        recentlyLesson.put("last_access", DateToTimestamp.dateToTimestamp(new Date()));

        mFCollection
                .document(UID)
                .collection("recently")
                .document(lessonID)
                .set(recentlyLesson)
                .addOnSuccessListener(unused -> {
                    Log.d("update recently lesson", "updateRecentlyLesson: complete");
                })
                .addOnFailureListener(e -> {
                    Log.w("update recently lesson", "updateRecentlyLesson: ", e);
                });
    }

    public void updateUserProfile(String field, String value, IServiceCallBack callBack) {
        String UID = FireBaseUtils.mAuth.getCurrentUser().getUid();
        mFCollection.document(UID).update(field, value)
                .addOnSuccessListener(unused -> callBack.onComplete())
                .addOnFailureListener(e -> {
                    Log.w("update pro5", "updateUserProfile " + field, e);
                    callBack.onFailed(e);
                });
    }

    public void updateUserPassword(String newPass, IServiceCallBack callBack) {
        Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).updatePassword(newPass)
                .addOnSuccessListener(unused -> callBack.onComplete())
                .addOnFailureListener(e -> {
                    Log.w("update pro5", "updatePass ", e);
                    callBack.onFailed(e);
                });
    }
}
