package com.nejivicky.memefactorymine.views
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.adapters.RvTemplateAdapter
import com.nejivicky.memefactorymine.adapters.RvTemplateAdapterTest
import com.nejivicky.memefactorymine.databinding.FragmentHomeBinding
import com.nejivicky.memefactorymine.databinding.FragmentHomeTestBinding
import com.nejivicky.memefactorymine.models.Template
import com.nejivicky.memefactorymine.utils.Constants.TEMPLATE_LIST
import com.nejivicky.memefactorymine.utils.showToast
import com.nejivicky.memefactorymine.viewmodels.MainViewModel
import java.lang.Exception

class HomeFragmentTest: Fragment(R.layout.fragment_home),RvTemplateAdapterTest.OnTemplateClicked{

    lateinit var fragmentHomeBinding: FragmentHomeTestBinding

    private val template=FirebaseFirestore.getInstance().collection("templates")
    private lateinit var mainViewModel:MainViewModel
    private lateinit var testAdapter:RvTemplateAdapterTest

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding= FragmentHomeTestBinding.inflate(inflater)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.templateList.observe(viewLifecycleOwner, Observer { listTemplates->
            testAdapter=RvTemplateAdapterTest(listTemplates,this)
            fragmentHomeBinding.rvTemplates.adapter=  testAdapter
        })
        //getTemplates()


        fragmentHomeBinding.etSearchTemplate.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                testAdapter.filter.filter(p0.toString())
            }

        })
    }

    private  fun getTemplates(){
        try {
                template.get().addOnSuccessListener {documentSnapshot->
                        if(documentSnapshot.isEmpty){
                            Log.d("TAG", "getTemplates: No data available ")
                        }else{
                            val listTemplates=documentSnapshot.toObjects(Template::class.java)
                            fragmentHomeBinding.rvTemplates.adapter= RvTemplateAdapterTest(listTemplates,this)
                        }
                }.addOnFailureListener {

                }
        }catch (exception:Exception){

        }
    }

    override fun onTemplateCLick(template: Template) {
        findNavController().navigate(
            HomeFragmentTestDirections.actionHomeFragmentTestToDialogCreateMemeOptions(template.url)
        )
        fragmentHomeBinding.etSearchTemplate.setText("")
    }

}