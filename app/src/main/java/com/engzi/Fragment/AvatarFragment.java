package com.engzi.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.engzi.Activities.MainActivity;
import com.engzi.Activities.SplashActivity;
import com.engzi.Activities.WelcomeActivity;
import com.engzi.Adapter.ChooseAvatarAdapter;
import com.engzi.Interface.IServiceCallBack;
import com.engzi.Model.User;
import com.engzi.R;
import com.engzi.Services.UserServices;
import com.engzi.Utils.FireBaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AvatarFragment extends Fragment {
    MainActivity mMainActivity;
    String avatar_url;
    List<String> avatar_urls = new ArrayList<>();
    int positionCurrentChecked;

    //    View
    View groupView, currentChooseItem;
    ImageView preview_avatar;
    RecyclerView choose_avatar;
    AppCompatButton confirm_change_button;
    Toolbar avatar_toolbar;

    //    Layout
    ConstraintLayout success_popup;

    //    Services
    UserServices userServices = new UserServices();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.fragment_avatar, container, false);
        mMainActivity = (MainActivity) getActivity();

        assert getArguments() != null;
        avatar_url = (String) getArguments().get("avatar_url");
        getAvatarList();

        initUI();

        Picasso.get().load(avatar_url).into(preview_avatar);

        confirm_change_button.setOnClickListener(view -> {
        });

        avatar_toolbar.setNavigationOnClickListener(view -> {
            mMainActivity.getSupportFragmentManager().popBackStack();
        });

        confirm_change_button.setOnClickListener(view -> {
            if (positionCurrentChecked >= 0)
                userServices.updateUserProfile("avatarImg", avatar_urls.get(positionCurrentChecked), new IServiceCallBack() {
                    @Override
                    public void retrieveData(Object response) {

                    }

                    @Override
                    public void onFailed(Exception e) {

                    }

                    @Override
                    public void onComplete() {
                        Picasso.get().load(avatar_urls.get(positionCurrentChecked)).into(preview_avatar);

                        success_popup.setVisibility(View.VISIBLE);

                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            success_popup.setVisibility(View.INVISIBLE);
                        }, 2000);
                    }
                });
        });

        return groupView;
    }

    private void initUI() {
        preview_avatar = groupView.findViewById(R.id.preview_avatar);
        choose_avatar = groupView.findViewById(R.id.choose_avatar);
        confirm_change_button = groupView.findViewById(R.id.confirm_change_button);
        avatar_toolbar = groupView.findViewById(R.id.avatar_toolbar);
        success_popup = groupView.findViewById(R.id.success_popup);
    }

    private void getAvatarList() {
        FireBaseUtils.avatarsRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            avatar_urls.add(uri.toString());

                            LinearLayoutManager mAvatarChooseLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            ChooseAvatarAdapter chooseAvatarAdapter = new ChooseAvatarAdapter(avatar_urls, avatar_url, this);
                            choose_avatar.setLayoutManager(mAvatarChooseLayout);
                            choose_avatar.setAdapter(chooseAvatarAdapter);
                        });
                    }
                });
    }

    public View getCurrentChooseItem() {
        return currentChooseItem;
    }

    public ImageView getPreview_avatar() {
        return preview_avatar;
    }

    public void setCurrentChooseItem(View currentChooseItem) {
        this.currentChooseItem = currentChooseItem;
    }

    public void setPositionCurrentChecked(int positionCurrentChecked) {
        this.positionCurrentChecked = positionCurrentChecked;
    }
}