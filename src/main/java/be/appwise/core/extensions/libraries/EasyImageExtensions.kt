package be.appwise.core.extensions.libraries

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

fun Activity.openEasyImageChooser(title: String) {
    EasyImage.openChooserWithGallery(this, title, 0)
}


fun Fragment.openEasyImageChooser(title: String) {
    EasyImage.openChooserWithGallery(this, title, 0)
}

fun Fragment.handlePictureResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?,
    processImages: (imageFiles: MutableList<File>) -> Unit
) {
    EasyImage.handleActivityResult(requestCode, resultCode, data, activity, object : DefaultCallback() {
        override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
            Logger.d(e?.message)
        }

        override fun onImagesPicked(imageFiles: MutableList<File>, source: EasyImage.ImageSource?, type: Int) {
            processImages(imageFiles)
        }

        override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
            super.onCanceled(source, type)
            if (source == EasyImage.ImageSource.CAMERA) {
                val photoFile = EasyImage.lastlyTakenButCanceledPhoto(activity!!)
                photoFile?.delete()
            }
        }
    })
}