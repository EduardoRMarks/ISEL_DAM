package dam_a45977.pokedex.ui.viewBinding

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView

object ViewBinding {

    @JvmStatic
    @BindingAdapter("app:cardBackgroundColor")
    fun setCardViewBackgroundColor(cardView: MaterialCardView, colorId: Int) {
        if (colorId != 0) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, colorId))
        }
    }



    @JvmStatic
    @BindingAdapter("android:src", "isToSetBackground")
    fun setRegionImage(imageView: AppCompatImageView, regionName: String, isToSetBackground: Boolean) {
        if (isToSetBackground) {
            val regionImageUri = "@drawable/bg_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        } else {
            val regionImageUri = "@drawable/pk_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        }
    }

    @JvmStatic
    @BindingAdapter("typeName")
    fun setTypeImage(imageView: AppCompatImageView, typeName: String) {
        val typeImageUri = "@drawable/${typeName.lowercase()}"
        val typeDrawableId = imageView.context.resources.getIdentifier(typeImageUri,null, imageView.context.packageName)
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, typeDrawableId))
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteView")
    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String?, paletteView: View) {
        val context = view.context
        Glide.with(context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap>
            {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    p2: Target<Bitmap>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")
                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()

                        val rgb = p?.lightMutedSwatch?.rgb
                        if (rgb != null) {

                            val card = paletteView as MaterialCardView
                            card.setCardBackgroundColor(rgb)
                        }
                    }
                    return false
                }
            })
            .into(view)
        /*Glide.with(view.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap>
            {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    p2: Target<Bitmap>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")

                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()

                        val rgb = p?.lightMutedSwatch?.rgb

                        val light = p?.lightVibrantSwatch?.rgb
                        val domain = p?.dominantSwatch?.rgb

                        if (domain != null) {
                            if (light != null) {

                                val gfgGradient = GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM,
                                    intArrayOf(
                                        domain,
                                        light
                                    ))

                                val card = paletteView as MaterialCardView
                                card.background = gfgGradient

                            } else {
                                if (rgb != null) {
                                    val card = paletteView as MaterialCardView
                                    card.setCardBackgroundColor(rgb)
                                    //paletteView.setBackgroundColor(rgb)

                                }
                            }

                        }
                    }
                    return false
                }
            })
            .into(view)*/
    }
}