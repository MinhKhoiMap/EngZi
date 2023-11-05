package com.engzi.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.engzi.Fragment.AvatarFragment;
import com.engzi.R;
import com.engzi.Services.UserServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChooseAvatarAdapter extends RecyclerView.Adapter<ChooseAvatarAdapter.MyViewHolder> {
    private final List<String> avatar_urls;
    String currentAvatarUrl;
    AvatarFragment rootFragment;

    public ChooseAvatarAdapter(List<String> avatar_urls, String currentAvatarUrl, AvatarFragment rootFragment) {
        this.avatar_urls = avatar_urls;
        this.currentAvatarUrl = currentAvatarUrl;
        this.rootFragment = rootFragment;
    }

    @NonNull
    @Override
    public ChooseAvatarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avatar_item, parent, false);
        return new ChooseAvatarAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ChooseAvatarAdapter.MyViewHolder holder, int position) {
        final String avatar_url = avatar_urls.get(position);

        Glide.with(holder.card_avatar.getContext()).load(avatar_url).transition(withCrossFade()).into(holder.card_avatar);

        if (avatar_url.equals(currentAvatarUrl)) {
            rootFragment.setCurrentChooseItem(holder.checked_avatar);
            rootFragment.setPositionCurrentChecked(position);
            holder.checked_avatar.setVisibility(View.VISIBLE);
        }

        holder.avatar_item.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.findViewById(R.id.card_avatar).setAlpha(0.5f);
            } else {
                if (rootFragment.getCurrentChooseItem() != null)
                    rootFragment.getCurrentChooseItem().setVisibility(View.INVISIBLE);
                rootFragment.setCurrentChooseItem(view.findViewById(R.id.checked_avatar));
                rootFragment.setPositionCurrentChecked(position);
                view.findViewById(R.id.checked_avatar).setVisibility(View.VISIBLE);
                view.findViewById(R.id.card_avatar).setAlpha(1);
                Glide.with(rootFragment.getContext())
                        .load(avatar_urls.get(position))
                        .transition(withCrossFade())
                        .into(rootFragment.getPreview_avatar());
            }
            Log.d("click choose", view.toString());
            return true;
        });


    }


    @Override
    public int getItemCount() {
        return avatar_urls.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CoordinatorLayout avatar_item;
        ImageView card_avatar, checked_avatar;

        MyViewHolder(View itemView) {
            super(itemView);

            avatar_item = itemView.findViewById(R.id.avatar_item);
            card_avatar = itemView.findViewById(R.id.card_avatar);
            checked_avatar = itemView.findViewById(R.id.checked_avatar);

            this.setIsRecyclable(false);
        }
    }
}
