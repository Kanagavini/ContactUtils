package com.ar.contactUtils.binding;

import android.widget.ImageView;
import com.ar.contactUtils.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class BindingAdapter {

   @androidx.databinding.BindingAdapter("contactUri")
    public static void loadImage(ImageView view,String imageUri){
       if (imageUri != null) {
           if (!imageUri.isEmpty()) {
               RequestOptions myOptions = new RequestOptions()
                       .fitCenter() // or centerCrop
                       .circleCropTransform()
                       .diskCacheStrategy(DiskCacheStrategy.NONE);
               Glide.with(view.getContext().getApplicationContext())
                       .load(imageUri).thumbnail(0.25f)
                       .apply(myOptions)
                       .transition(DrawableTransitionOptions.withCrossFade())
                       .into(view);
           }
       }

   }


}
