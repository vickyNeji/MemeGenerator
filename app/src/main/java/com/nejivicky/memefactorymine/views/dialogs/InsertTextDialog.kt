package com.nejivicky.memefactorymine.views.dialogs

import android.graphics.Color
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.view.View
import androidx.fragment.app.DialogFragment
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.adapters.AdapterColorPalette
import com.nejivicky.memefactorymine.databinding.DialogInsertBinding

class InsertTextDialog(private val onTextSubmit: OnTextSubmit):DialogFragment(R.layout.dialog_insert),AdapterColorPalette.OnPaletteClick {

    lateinit var dialogInsertBinding: DialogInsertBinding

    var textColor=Color.BLACK
    var textSize=18F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogInsertBinding= DialogInsertBinding.bind(view)
        initColorPalette()

        dialogInsertBinding.btInsertText.setOnClickListener {
            textSize=dialogInsertBinding.sliderTextSize.value
            onTextSubmit.onTextSubmitted(dialogInsertBinding.etInsertText.text.toString(),
            textSize, textColor)
            dismiss()
        }

    }

    private fun initColorPalette() {
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
        dialogInsertBinding.rvDialogInsertTextColorPicker.adapter= AdapterColorPalette(colorList,this)
    }

    override fun onPaletteClicked(selectedColor: Int) {
        textColor=selectedColor
        dialogInsertBinding.tvTextColor.setTextColor(selectedColor)

    }

    interface OnTextSubmit{
        fun onTextSubmitted(text:String,textSize:Float,textColor:Int)
    }

}