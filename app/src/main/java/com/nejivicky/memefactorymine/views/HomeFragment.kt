package com.nejivicky.memefactorymine.views
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.adapters.RvTemplateAdapter
import com.nejivicky.memefactorymine.databinding.FragmentHomeBinding
import com.nejivicky.memefactorymine.utils.Constants.TEMPLATE_LIST
import com.nejivicky.memefactorymine.utils.showToast

class HomeFragment: Fragment(R.layout.fragment_home),RvTemplateAdapter.OnTemplateClicked{

    lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding= FragmentHomeBinding.inflate(inflater)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding.rvTemplates.adapter= RvTemplateAdapter(TEMPLATE_LIST,this)
    }

    override fun onTemplateCLick(template: Int) {
        //requireContext().showToast("$template")
//        DialogCreateMemeOptions().getInstance(template).show(
//            childFragmentManager,"memeCreateOptions"
//        )
//        findNavController().navigate(
//            HomeFragmentDirections.actionHomeFragmentToDialogCreateMemeOptions(template)
//        )
    }


}