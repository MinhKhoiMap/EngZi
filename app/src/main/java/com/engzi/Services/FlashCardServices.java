package com.engzi.Services;

import android.util.Log;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Utils.FireBaseUtils;
import com.google.firebase.firestore.CollectionReference;

public class FlashCardServices {
    final CollectionReference mFCollection;

    public FlashCardServices() {
        mFCollection = FireBaseUtils.mFStore.collection("flash_cards");
    }

    public void getCardByID(String cardID, IServiceCallBack callBack) {
        mFCollection.document(cardID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    FlashCard card = documentSnapshot.toObject(FlashCard.class);
                    assert card != null;
                    card.setCardID(cardID);
                    callBack.retrieveData(card);
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }
}
