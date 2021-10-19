package com.nejivicky.memefactorymine.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.Toast

fun Context.showToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun toMutableBitmap(bitmap: Bitmap): Bitmap?{
    return resizeImage( bitmap.copy(Bitmap.Config.ARGB_8888, true),100,100)
}


fun resizeImage(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
    var resizedImage: Bitmap? = null
    try {
        var imageHeight = image.height
        if (imageHeight > maxHeight) imageHeight = maxHeight
        var imageWidth = (imageHeight * image.width
                / image.height)
        if (imageWidth > maxWidth) {
            imageWidth = maxWidth
            imageHeight = (imageWidth * image.height
                    / image.width)
        }
        if (imageHeight > maxHeight) imageHeight = maxHeight
        if (imageWidth > maxWidth) imageWidth = maxWidth
        resizedImage = Bitmap.createScaledBitmap(
            image, imageWidth,
            imageHeight, true
        )
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
    } catch (e: NullPointerException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return resizedImage
}

 fun screenShot(view: View): Bitmap? {
    val bitmap = Bitmap.createBitmap(
        view.width,
        view.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}