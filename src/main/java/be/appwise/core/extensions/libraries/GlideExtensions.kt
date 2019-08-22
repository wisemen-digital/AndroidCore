package be.appwise.core.extensions.libraries

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 * loads image in view
 * @param url location of the image
 * @param cropOptions transformation of the image (default RequestOptions.noTransformation())
 * @param placeholder id of placeholder drawable
 */
fun ImageView.load(url: String?, cropOptions: RequestOptions = RequestOptions.noTransformation(), placeholder: Int? = null) {
    Glide.with(context).load(url).apply(cropOptions.apply {
        placeholder?.let { placeholder(it) }
    }).into(this)
}

/**
 * loads image in view from an Uri
 * @param uri location of the image as uri
 * @param cropOptions transformation of the image (default RequestOptions.noTransformation())
 * @param placeholder id of placeholder drawable
 */
fun ImageView.loadUri(uri: Uri, cropOptions: RequestOptions = RequestOptions.noTransformation(), placeholder: Int? = null) {
    Glide.with(context).load(uri).apply(cropOptions.apply {
        placeholder?.let { placeholder(it) }
    }).into(this)
}

/**
 * loads image in view from file
 * @param file image file
 * @param cropOptions transformation of the image (default RequestOptions.noTransformation())
 * @param placeholder id of placeholder drawable
 */
fun ImageView.loadFile(file: File?, cropOptions: RequestOptions = RequestOptions.noTransformation(), placeholder: Int? = null) {
    Glide.with(context).load(file).apply(cropOptions.apply {
        placeholder?.let { placeholder(it) }
    }).into(this)
}

/**
 * loads image in view as circle
 * @param url location of the image
 * @param placeholder id of placeholder drawable
 *
 * @see RequestOptions.circleCrop
 */
fun ImageView.loadCircle(url: String?, placeholder: Int? = null) {
    val cropOptions = RequestOptions().circleCrop().apply {
        placeholder?.let { placeholder(it) }
    }

    load(url, cropOptions)
}

/**
 * loads image with rounded corners
 * @param url location of the image
 * @param radius radius of corners in dp
 * @param placeholder id of placeholder drawable
 *
 * @see RequestOptions.centerCrop
 * @see RoundedCorners
 */
fun ImageView.loadRoundedCorners(url: String?, radius: Int = 10, placeholder: Int? = null) {
    val roundedCorners = RoundedCorners((radius * resources.displayMetrics.density).toInt())
    val cropOptions = RequestOptions().transforms(CenterCrop(), roundedCorners).apply{
        placeholder?.let { placeholder(it) }
    }

    load(url, cropOptions)
}

/**
 * loads image with centerCrop
 * @param url location of the image
 * @param placeholder id of placeholder drawable
 *
 * @see RequestOptions.centerCrop
 */
fun ImageView.loadCenterCrop(url: String?, placeholder: Int? = null) {
    val cropOptions = RequestOptions().centerCrop().apply{
        placeholder?.let { placeholder(it) }
    }
    load(url, cropOptions)
}