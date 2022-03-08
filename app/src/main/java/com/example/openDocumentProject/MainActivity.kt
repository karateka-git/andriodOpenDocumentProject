package com.example.openDocumentProject

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.openDocumentProject.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        private val GET_FILE_TYPE = arrayOf("*/*")
        private const val IMAGE_FILE_TYPE = "image/*"
        private val TEMP_PHOTO_FILE_NAME = File("tmp_image_file.jpg")

        enum class MethodForAddFile {
            SELECT_FROM_GALLERY,
            MAKE_A_PHOTO,
            ATTACH_FILE;
        }
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var selectFromGallery: ActivityResultLauncher<String>
    private lateinit var makePhoto: ActivityResultLauncher<Uri>
    private lateinit var attachFile: ActivityResultLauncher<Array<String>>
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForMultipleActivityResult()
        initListeners()
    }

    private fun registerForMultipleActivityResult() {
        selectFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            prepareUrl(uri)
        }
        makePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                prepareUrl(photoUri)
            }
        }
        attachFile = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            prepareUrl(uri)
        }
    }

    private fun initListeners() {
        binding.apply {
            methodSelectFromGallery.setOnClickListener {
                onMethodForAddFileClick(MethodForAddFile.SELECT_FROM_GALLERY)
            }
            methodMakeAPhoto.setOnClickListener {
                onMethodForAddFileClick(MethodForAddFile.MAKE_A_PHOTO)
            }
            methodAttachFile.setOnClickListener {
                onMethodForAddFileClick(MethodForAddFile.ATTACH_FILE)
            }
        }
    }

    private fun onMethodForAddFileClick(selectedMethod: MethodForAddFile) {
        when (selectedMethod) {
            MethodForAddFile.SELECT_FROM_GALLERY -> {
                selectFromGallery.launch(IMAGE_FILE_TYPE)
            }
            MethodForAddFile.MAKE_A_PHOTO -> {
                val tmpFile = getTempFileName()

                photoUri = FileProvider.getUriForFile(
                    applicationContext,
                    "${applicationContext.packageName}.fileProvider",
                    tmpFile
                )
                makePhoto.launch(photoUri)
            }
            MethodForAddFile.ATTACH_FILE -> {
                attachFile.launch(GET_FILE_TYPE)
            }
        }
    }

    private fun getTempFileName() =
        File.createTempFile(
            TEMP_PHOTO_FILE_NAME.nameWithoutExtension,
            ".${TEMP_PHOTO_FILE_NAME.extension}",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        ).apply {
            createNewFile()
            deleteOnExit()
        }

    private fun prepareUrl(url: Uri?) {
        Log.d("VLADISLAV", url?.path ?: "")
    }
}
