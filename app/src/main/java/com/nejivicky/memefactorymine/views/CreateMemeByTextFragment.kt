package com.nejivicky.memefactorymine.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.databinding.FragmentCreateMemeByTextBinding
import com.nejivicky.memefactorymine.utils.showToast
import com.nejivicky.memefactorymine.views.dialogs.InsertTextDialog
import java.io.IOException
import java.util.*
import kotlin.math.atan2

class CreateMemeByTextFragment:Fragment(R.layout.fragment_create_meme_by_text_),InsertTextDialog.OnTextSubmit {

    lateinit var fragmentCreateMemeByTextBinding: FragmentCreateMemeByTextBinding
    private val args:CreateMemeByDrawingArgs by navArgs()
    private val textViewAdded= mutableListOf<View>()

    //touch listener
    private var xDelta = 0
    private var yDelta = 0

    //rotate listener
    var viewRotation = 0
    var fingerRotation = 0
    var newFingerRotation = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCreateMemeByTextBinding= FragmentCreateMemeByTextBinding.inflate(inflater)
        return fragmentCreateMemeByTextBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCreateMemeByTextBinding.ivMemeCreateText.setImageResource(args.template)

        fragmentCreateMemeByTextBinding.btAddTextView.setOnClickListener {
            InsertTextDialog(this).show(childFragmentManager,"insertTextDialog")
        }

        fragmentCreateMemeByTextBinding.btnSaveView.setOnClickListener {
            textViewAdded.forEach {view->
                val delete = view.findViewById<ImageView>(R.id.ivDeleteTvCreated)
                val rotate = view.findViewById<ImageView>(R.id.ivRotateTvCreated)
                val remove = view.findViewById<ImageView>(R.id.ivRemoveSettings)
                delete.visibility = View.INVISIBLE
                rotate.visibility = View.INVISIBLE
                remove.visibility = View.INVISIBLE
            }

            val signature= screenShot(fragmentCreateMemeByTextBinding.rlIvHolder)
            savePhotoToInternalStorage(UUID.randomUUID().toString(),signature!!)
            requireContext().showToast("Saved")
            findNavController().popBackStack()

        }


    }

    private val onTouchListener = View.OnTouchListener { view, event ->
        view.performClick()
        val x = event.rawX
        val y = event.rawY
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val lParams = view.layoutParams as RelativeLayout.LayoutParams
                xDelta = (x - lParams.leftMargin).toInt()
                yDelta = (y - lParams.topMargin).toInt()
            }
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {
                val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
                layoutParams.leftMargin = (x - xDelta).toInt()
                layoutParams.topMargin = (y - yDelta).toInt()
                layoutParams.rightMargin = 0
                layoutParams.bottomMargin = 0
                view.layoutParams = layoutParams
            }
        }
        fragmentCreateMemeByTextBinding.rlMemeByText.invalidate()
        true
    }

    private val onTouchRotateListener = View.OnTouchListener { view, event ->
        view.performClick()
        val x = event.rawX
        val y = event.rawY
        val owner = view.parent as ViewGroup
        val xc = fragmentCreateMemeByTextBinding.rlMemeByText.width / 2
        val yc = fragmentCreateMemeByTextBinding.rlMemeByText.height / 2


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                viewRotation = owner.rotation.toInt()
                fingerRotation =
                    Math.toDegrees(atan2((x - xc).toDouble(), (yc - y).toDouble())).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                newFingerRotation = Math.toDegrees(
                    atan2(
                        (x - xc).toDouble(),
                        (yc - y).toDouble()
                    )
                ).toInt()
                owner.rotation = ((viewRotation + newFingerRotation - fingerRotation).toFloat())
            }
            MotionEvent.ACTION_UP -> {
                fingerRotation = 0
                newFingerRotation = 0
            }

        }
        true

    }

    override fun onTextSubmitted(text:String,textSize:Float,textColor:Int) {
        requireContext().showToast(text)
        val viewToInflate= requireActivity().layoutInflater.inflate(R.layout.custom_text_view
        ,fragmentCreateMemeByTextBinding.rlMemeByText,false)
        textViewAdded.add(viewToInflate)

        val tv= viewToInflate.findViewById<TextView>(R.id.tvCreated)
        val delete= viewToInflate.findViewById<ImageView>(R.id.ivDeleteTvCreated)
        val rotate= viewToInflate.findViewById<ImageView>(R.id.ivRotateTvCreated)
        val remove= viewToInflate.findViewById<ImageView>(R.id.ivRemoveSettings)

        tv.text=text
        tv.textSize=textSize
        tv.setTextColor(textColor)

        //add rotate listener
        rotate.setOnTouchListener(onTouchRotateListener)

        //Add View

        fragmentCreateMemeByTextBinding.rlIvHolder.addView(viewToInflate)
        viewToInflate.setOnTouchListener(onTouchListener)


        delete.setOnClickListener {
            fragmentCreateMemeByTextBinding.rlIvHolder.removeView(viewToInflate)
        }

        remove.setOnClickListener {
            delete.visibility=View.INVISIBLE
            rotate.visibility=View.INVISIBLE
            remove.visibility=View.INVISIBLE
        }

        viewToInflate.setOnClickListener {
            delete.visibility=View.VISIBLE
            rotate.visibility=View.VISIBLE
            remove.visibility=View.VISIBLE
        }


    }

    private fun screenShot(view: View): Bitmap?{
        val bitmap=Bitmap.createBitmap(
            view.width,
            view.height,Bitmap.Config.ARGB_8888
        )
        val canvas=Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

}