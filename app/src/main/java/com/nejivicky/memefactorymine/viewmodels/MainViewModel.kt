package com.nejivicky.memefactorymine.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.nejivicky.memefactorymine.adapters.RvTemplateAdapterTest
import com.nejivicky.memefactorymine.models.Template
import java.lang.Exception

class MainViewModel:ViewModel() {
    private val _templateList=MutableLiveData<List<Template>>()
    val templateList: LiveData<List<Template>> = _templateList
    private val template= FirebaseFirestore.getInstance().collection("templates")
    init {
        getTemplates()
    }

    private fun getTemplates(){
        try {
            template.get().addOnSuccessListener {documentSnapshot->
                if(documentSnapshot.isEmpty){
                    Log.d("TAG", "getTemplates: No data available ")
                }else{
                    val listTemplates=documentSnapshot.toObjects(Template::class.java)
                    _templateList.postValue(listTemplates)
                }
            }.addOnFailureListener {

            }
        }catch (exception: Exception){

        }
    }

}