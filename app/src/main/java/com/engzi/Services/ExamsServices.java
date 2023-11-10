package com.engzi.Services;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Utils.FireBaseUtils;
import com.google.firebase.firestore.CollectionReference;

public class ExamsServices {
    final CollectionReference mFCollection;

    public ExamsServices() {
        this.mFCollection = FireBaseUtils.mFStore.collection("exams");
    }

    public void createExamRecords(String lessonID, IServiceCallBack callBack) {
        String UID = FireBaseUtils.mAuth.getCurrentUser().getUid();
    }
}
