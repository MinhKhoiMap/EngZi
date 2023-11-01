package com.engzi.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.FlashCard;
import com.engzi.Utils.FireBaseUtil;
import com.google.firebase.firestore.CollectionReference;

public class FlashCardServices {
    final CollectionReference mFCollection;

    public FlashCardServices() {
        mFCollection = FireBaseUtil.mFStore.collection("flash_cards");
    }

    public void getCardByID(String cardID, IServiceCallBack callBack) {
        mFCollection.document(cardID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    FlashCard card = documentSnapshot.toObject(FlashCard.class);
                    card.setCardID(cardID);
                    Log.d("111", "getCardByID: " + card.getVowel());
                    callBack.retrieveData(card);
                    callBack.onComplete();
                })
                .addOnFailureListener(callBack::onFailed);
    }
}
