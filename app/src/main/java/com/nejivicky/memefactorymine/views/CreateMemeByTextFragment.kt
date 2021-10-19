package com.nejivicky.memefactorymine.views
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.databinding.FragmentCreateMemeByTextBinding

class CreateMemeByTextFragment:Fragment(R.layout.fragment_create_meme_by_text_) {

    lateinit var fragmentCreateMemeByTextBinding: FragmentCreateMemeByTextBinding
    private val args:CreateMemeByDrawingArgs by navArgs()

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
            
        }


    }



}