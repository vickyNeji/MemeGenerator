package com.nejivicky.memefactorymine.views
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.adapters.InternalStoragePhotoAdapter
import com.nejivicky.memefactorymine.databinding.FragmentSavedBinding
import com.nejivicky.memefactorymine.models.InternalStoragePhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

class SavedFragment: Fragment(R.layout.fragment_saved),InternalStoragePhotoAdapter.OnStoragePhotoClicked {

    lateinit var binding:FragmentSavedBinding
    lateinit var internalStoragePhotoAdapter: InternalStoragePhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSavedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        internalStoragePhotoAdapter=InternalStoragePhotoAdapter(this)
        binding.rvSavedPhotos.adapter= internalStoragePhotoAdapter
        loadPhotosFromInternalStorageIntoRecyclerView()



    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            val photos = loadPhotosFromInternalStorage()
            internalStoragePhotoAdapter.differ.submitList(photos)
        }
    }

    private suspend fun loadPhotosFromInternalStorage():List<InternalStoragePhoto>{
        return withContext(Dispatchers.IO) {
            val files = requireActivity().filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }

    override fun onShareClick(fileName: String, bitmap: Bitmap) {
        val path: String = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            bitmap,
            "Image I want to share",
            null
        )
        val uri = Uri.parse(path)
        try {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.type = "image/jpeg"
            requireContext().startActivity(Intent.createChooser(shareIntent, "Share"))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDeleteClick(fileName: String) {
        val isDeleted = deletePhotoFromInternalStorage(fileName)
        loadPhotosFromInternalStorageIntoRecyclerView()
        if (isDeleted) {
            Toast.makeText(requireContext(), "Photo deleted", Toast.LENGTH_SHORT).show()
        }
    }


    private fun deletePhotoFromInternalStorage(filename: String): Boolean {
        return try {
            requireActivity().deleteFile(filename)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}