package entrego.com.android.web.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.module.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import entrego.com.android.util.Logger
import java.io.InputStream


class CustomGlideModule : GlideModule {
    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        Logger.logd("Asdasd")
    }

    override fun registerComponents(context: Context?, glide: Glide?) {
        glide?.register(GlideUrl::class.java, InputStream::class.java, GlideOkHttpUrlLoader.Factory())
    }
}