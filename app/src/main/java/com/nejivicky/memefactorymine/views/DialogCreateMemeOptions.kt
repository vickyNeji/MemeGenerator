package com.nejivicky.memefactorymine.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.databinding.DialogMemeCreateOptionBinding

class DialogCreateMemeOptions:DialogFragment() {

    lateinit var dialogBinding: DialogMemeCreateOptionBinding
    private val args:DialogCreateMemeOptionsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogBinding=DialogMemeCreateOptionBinding.inflate(inflater)
        return dialogBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogBinding.clText.setOnClickListener {

            val action = DialogCreateMemeOptionsDirections.actionDialogCreateMemeOptionsToCreateMemeByTextFragment(args.template)
            findNavController().navigate(action)

        }

        dialogBinding.clDraw.setOnClickListener {

            findNavController().navigate(
                DialogCreateMemeOptionsDirections.actionDialogCreateMemeOptionsToCreateMemeByDrawing(args.template)
            )
        }



    }

}