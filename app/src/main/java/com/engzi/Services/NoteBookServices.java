package com.engzi.Services;

import android.util.Log;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Utils.FireBaseUtils;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;

import java.util.List;
import java.util.Objects;

public class NoteBookServices {
    final CollectionReference mFCollection;

    public NoteBookServices() {
        mFCollection = FireBaseUtils.mFStore.collection("note_books");
    }

    //    Read Services
    public void getNotebookCardOrderByLevel(int level, IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        mFCollection.document(UID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    List<String> notebookCardID = (List<String>) documentSnapshot.get("level_" + level);
                    FlashCardServices flashCardServices = new FlashCardServices();
                    assert notebookCardID != null;
                    for (String cardID : notebookCardID) {
                        flashCardServices.getCardByID(cardID, new IServiceCallBack() {
                            @Override
                            public void retrieveData(Object response) {
//                                Log.d("notebook", ((FlashCard) response).getEnglish_word());
                                callBack.retrieveData(response);
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
                })
                .addOnFailureListener(callBack::onFailed);
    }


    //    Create + Update Services
    public void updateNoteBookOrderByLevel(int level, String cardID, IServiceCallBack callBack) {
        String UID = Objects.requireNonNull(FireBaseUtils.mAuth.getCurrentUser()).getUid();
        mFCollection
                .document(UID)
                .update("level_" + level, FieldValue.arrayUnion(cardID))
                .addOnFailureListener(callBack::onFailed);
    }
}
