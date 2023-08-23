package com.tucto.ec_final_lisbeth.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.tucto.ec_final_lisbeth.R
import com.tucto.ec_final_lisbeth.data.adaptador.DatosAdaptador
import com.tucto.ec_final_lisbeth.data.adaptador.DatosAtributos
import com.tucto.ec_final_lisbeth.databinding.FragmentApiFirebaseBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ApiFirebaseFragment : Fragment() {
    private lateinit var binding: FragmentApiFirebaseBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val IMAGE_CAPTURE_REQUEST_CODE = 1
    private lateinit var currentPhotoPath: String
    private lateinit var imageRef: StorageReference
    private lateinit var dataAdapter: DatosAdaptador
    private val dataList: MutableList<DatosAtributos> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApiFirebaseBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        imageRef = Firebase.storage.reference.child("imagenes/${UUID.randomUUID()}.jpg")

        binding.btnFoto.setOnClickListener {
            if (hasCameraPermission()) {
                dispatchTakePictureIntent()
            } else {
                requestCameraPermission()
            }
        }

        binding.btnAgregar.setOnClickListener {
            uploadDataToFirestore()
        }
        dataAdapter = DatosAdaptador(dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = dataAdapter

        firestore.collection("starwars")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val name = document.getString("name") ?: ""
                    val gender = document.getString("gender") ?: ""
                    val heigth = document.getString("heigth") ?: ""
                    val mass = document.getString("mass") ?: ""
                    val imagen = document.getString("imagen") ?: ""
                    val dataItem = DatosAtributos(name, gender, heigth, mass, imagen)
                    dataList.add(dataItem)
                }
                dataAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }



        return binding.root
    }
    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), IMAGE_CAPTURE_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permiso de cámara denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.tucto.ec_final_lisbeth.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_CODE)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


    private fun uploadDataToFirestore() {
        val name = binding.txtName.editText?.text.toString()
        val gender = binding.txtGender.editText?.text.toString()
        val heigth = binding.txtHeigth.editText?.text.toString()
        val mass = binding.txtMass.editText?.text.toString()

        val newData = hashMapOf(
            "name" to name,
            "gender" to gender,
            "heigth" to heigth,
            "mass" to mass
        )

        firestore.collection("starwars")
            .add(newData)
            .addOnSuccessListener { documentReference ->
                imageRef.putFile(Uri.fromFile(File(currentPhotoPath)))
                    .addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            val imageUri = uri.toString()
                            documentReference.update("imagen", imageUri)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Datos agregados", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(), "Error de datos", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error de imagen", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error de datos", Toast.LENGTH_SHORT).show()
            }
    }

}