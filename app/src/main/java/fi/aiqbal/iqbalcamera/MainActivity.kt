package fi.aiqbal.iqbalcamera

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 99
    private val FILE_NAME = "phot0.jpg"
    private lateinit var photoFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCapture.setOnClickListener {
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider =
                FileProvider.getUriForFile(this, "fi.iqbal.iqbalcamera.fileprovider", photoFile)
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (captureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(this, "No camera found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
           val imageTaken = BitmapFactory.decodeFile(photoFile.absolutePath)
            imageView.setImageBitmap(imageTaken)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}