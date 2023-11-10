package com.engzi.Services;

import static com.google.firebase.firestore.Filter.and;
import static com.google.firebase.firestore.Filter.equalTo;

import android.util.Log;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ExamsServices {
    final CollectionReference mFCollection;

    public ExamsServices() {
        this.mFCollection = FireBaseUtils.mFStore.collection("exams");
    }

    //    Create
    public void createExamRecord(String lessonID, String result, IServiceCallBack callBack) {
        String UID = FireBaseUtils.mAuth.getCurrentUser().getUid();

        LocalDate now = LocalDate.now();
        Log.d("Date", String.valueOf(LocalDate.parse(now.toString())));

        Map<String, Object> examDoc = new HashMap<>();
        examDoc.put("topic_id", (String) lessonID);
        examDoc.put("uid", (String) UID);
        examDoc.put("result", result);
        examDoc.put("date", now.toString());

        mFCollection.add(examDoc)
                .addOnSuccessListener(documentReference -> callBack.onComplete())
                .addOnFailureListener(callBack::onFailed);
    }

    //    Read
    public void getExamRecord(String lessonID, IServiceCallBack callBack) {
        String UID = FireBaseUtils.mAuth.getCurrentUser().getUid();

        mFCollection.where(and(
                        equalTo("uid", UID),
                        equalTo("topic_id", lessonID)
                )).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                    String result = (String) document.get("result");

                    callBack.retrieveData(result);
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }

    //    Update
    public void updateExamRecords(String lessonID, String result, IServiceCallBack callBack) {
        String UID = FireBaseUtils.mAuth.getCurrentUser().getUid();
        LocalDate now = LocalDate.now();

        Map<String, Object> examDoc = new HashMap<>();
        examDoc.put("result", result);
        examDoc.put("date", now.toString());

        mFCollection.where(and(
                        equalTo("uid", UID),
                        equalTo("topic_id", lessonID)
                )).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                    String examID = document.getId();
                    mFCollection.document(examID)
                            .update(examDoc);
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }
}
