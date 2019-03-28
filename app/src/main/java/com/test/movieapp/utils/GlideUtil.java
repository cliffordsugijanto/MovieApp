package com.test.movieapp.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.movieapp.R;
import com.test.movieapp.model.MovieListItem;

public class GlideUtil {

    public static void loadThumbnail(Activity activity, ImageView imageView, MovieListItem item) {
        if (!activity.isFinishing()) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.film_poster_placeholder)
                    .error(R.drawable.film_poster_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .dontTransform();

            Glide.with(activity)
                    .load(activity.getString(R.string.image_url)+item.getPosterPath())
                    .thumbnail(0.1f)
                    .apply(options)
                    .into(imageView);
        }
    }
}
