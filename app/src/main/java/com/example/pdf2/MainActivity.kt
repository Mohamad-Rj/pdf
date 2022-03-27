package com.example.pdf2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val STORAGE_CODE: Int = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                } else {
                    savePdf()
                }
            } else {
                savePdf()
            }
        }
    }
    private fun savePdf() {
        val editText = findViewById<EditText>(R.id.edittext)

        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis())
       // val mFilepath = Environment.getDataDirectory().toString()+ "/" +mFileName + "pdf"
        val path = this.getExternalFilesDir(null)
        val letdir = File(path,"phonebackup")
        letdir.mkdirs()
        val file = File(letdir,"demo.pdf")

        try {
            PdfWriter.getInstance(mDoc,FileOutputStream(file))
            mDoc.open()
            val mText = editText.text.toString()
            mDoc.addAuthor("aaaa")
            mDoc.add(Paragraph(mText))
            mDoc.close()
            Toast.makeText(this,"mf" ,Toast.LENGTH_LONG).show()
        }
        catch (e : Exception){
            Toast.makeText(this, e.message  ,Toast.LENGTH_LONG).show()

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePdf()

                } else {
                    Toast.makeText(this, "permisiion denied", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}
