package com.example.travelhelper.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelhelper.model.Architecture
import com.example.travelhelper.model.Sight
import com.example.travelhelper.util.NODE_SIGHTS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

class SightsViewModel : ViewModel() {

    private val dbSights = FirebaseDatabase.getInstance().getReference(NODE_SIGHTS)
    private val storageSights = FirebaseStorage.getInstance().getReference(NODE_SIGHTS)

    private val _addSightSuccess = MutableLiveData<Unit>()
    val addSightSuccess: LiveData<Unit>
        get() = _addSightSuccess

    private val _addSightFail = MutableLiveData<String>()
    val addSightFail: LiveData<String>
        get() = _addSightFail

    private val _getSightsSuccess = MutableLiveData<List<Sight>>()
    val getSightsSuccess: LiveData<List<Sight>>
        get() = _getSightsSuccess

    private val _getSightsFail = MutableLiveData<String>()
    val getSightsFail: LiveData<String>
        get() = _getSightsFail

    private val _getSightsArchitecture = MutableLiveData<String>()
    val getSightsArchitecture: LiveData<String>
        get() = _getSightsArchitecture

    fun addSight(imageUri: Uri, sight: Sight) {
        sight.id = dbSights.push().key

        storageSights.child(sight.id!!).putFile(imageUri).addOnSuccessListener {
            dbSights.child(sight.id!!).setValue(sight).addOnCompleteListener { task ->
                if (task.isSuccessful) _addSightSuccess.value = Unit else _addSightFail.value =
                    task.exception?.message
            }
        }
    }

    fun getSights(architecture: Architecture, currentLocation: Location) {
        Log.d("SightsVM", "Architecture: $architecture")
        dbSights.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sights: List<Sight> =
                    snapshot.children.map { it.getValue<Sight>() }.filterIsInstance<Sight>()

                when (architecture) {
                    Architecture.MODERN,
                    Architecture.SECESSION,
                    Architecture.BAROQUE,
                    Architecture.GOTHIC -> sights = sights.filter {
                        val architecture = architecture.name.lowercase(
                            Locale.getDefault()
                        )
                        _getSightsArchitecture.value = architecture
                        it.architecture == architecture
                    }
                    else -> { //nothing
                    }
                }

                Log.d("SightsVM", "Sights: $sights")
                _getSightsSuccess.value = sortSightsByNearestLocation(sights, currentLocation)
            }

            override fun onCancelled(error: DatabaseError) {
                _getSightsFail.value = error.message
            }
        })
    }

    private fun sortSightsByNearestLocation(sights: List<Sight>, currentLocation: Location): List<Sight> {
        val map = mutableMapOf<Location, Sight>(). also { map ->
            sights.forEach { sight ->
                map[Location(""). also {
                    it.longitude = sight.location!!.longitude
                    it.latitude = sight.location.latitude
                }] = sight
            }
        }
        val sorted = map.toSortedMap(compareBy { it.distanceTo(currentLocation) })
        return sorted.values.toList()
    }

    fun getImageBitmapFromStorage(imageId: String, onSuccess: (bitmap: Bitmap) -> Unit) {
        val file = File.createTempFile("temp", ".jpg")
        storageSights.child(imageId).getFile(file).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            onSuccess(bitmap)
        }
    }
}