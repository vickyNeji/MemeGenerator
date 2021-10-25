package com.nejivicky.memefactorymine.views
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.adapters.AdapterColorPalette
import com.nejivicky.memefactorymine.databinding.FragmentCreateDrawBinding
import com.nejivicky.memefactorymine.views.paint.PaintView
import java.io.IOException
import java.util.*

class CreateMemeByDrawing:Fragment(R.layout.fragment_create_draw),AdapterColorPalette.OnPaletteClick {

     lateinit var binding:FragmentCreateDrawBinding
     private val args:CreateMemeByDrawingArgs by navArgs()
     lateinit var paintView: PaintView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCreateDrawBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paintView=requireActivity().findViewById(R.id.paintViewCanvas)
        paintView.init(args.template)
        initPalette()


        binding.ivClearDraw.setOnClickListener {
            paintView.clear()
        }

        binding.ivSaveDraw.setOnClickListener {
            val signature = screenShot(binding.llPaintViewContainer)
            savePhotoToInternalStorage(UUID.randomUUID().toString(), signature!!)
            Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.ivDrawPen.setOnClickListener {
            binding.cvBrushSize.visibility=View.VISIBLE
        }


        binding.brushSize1.setOnClickListener {
            binding.cvBrushSize.visibility=View.GONE
            paintView.setBrushSize(5)
        }
        binding.brushSize2.setOnClickListener {
            binding.cvBrushSize.visibility=View.GONE
            paintView.setBrushSize(10)
        }
        binding.brushSize3.setOnClickListener {
            binding.cvBrushSize.visibility=View.GONE
            paintView.setBrushSize(30)
        }

        binding.brushSize4.setOnClickListener {
            binding.cvBrushSize.visibility=View.GONE
            paintView.setBrushSize(40)
        }



    }

   private fun initPalette(){
       val colorList = listOf(
           Color.GREEN,
           Color.RED,
           Color.rgb(200, 100, 120),
           Color.rgb(100, 200, 0),
           Color.LTGRAY,
           Color.CYAN,
           Color.BLACK,
           Color.rgb(10, 130, 200),
           Color.rgb(200, 200, 20),
           Color.rgb(200, 0, 200)
       )
       binding.rvColor.adapter= AdapterColorPalette(colorList,this)


    }

    override fun onPaletteClicked(selectedColor: Int) {
        val drawable= AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_draw_24)
        drawable?.let {
            val wrappedDrawable = DrawableCompat.wrap(it).apply {
                setTint(selectedColor)
            }
            binding.ivDrawPen.background=wrappedDrawable
        }
        paintView.setColor(selectedColor)
    }

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch(e: IOException) {
            e.printStackTrace()
            false
        }
    }


    private fun screenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


}