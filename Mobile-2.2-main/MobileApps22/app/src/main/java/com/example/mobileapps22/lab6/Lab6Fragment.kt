package com.example.mobileapps22.lab6

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileapps22.R
import java.io.File
import javax.crypto.SecretKey


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val TAG = "FRAGMENT_CRYPTO"

//private lateinit var sKey: SecretKey
private lateinit var clearFile: File

private lateinit var fragmentContext: Context

private lateinit var encryptedFile: File
//private lateinit var encryptedFileSeparated: ArrayList<File>

private lateinit var externalFilesDir: String

private val FILENAME_CLEAR = "clear.txt"
private val FILENAME_ENCRYPTED = "encrypted.txt"
private val FILENAME_DECRYPTED = "decrypted.txt"


private val customContent =
    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA, часто используемый в печати и вэб-дизайне. Lorem Ipsum является стандартной \"рыбой\" для текстов на латинице с начала XVI века. В то время некий безымянный печатник создал большую коллекцию размеров и форм шрифтов, используя Lorem Ipsum для распечатки образцов. Lorem Ipsum не только успешно пережил без заметных изменений пять веков, но и перешагнул в электронный дизайн. Его популяризации в новое время послужили публикация листов Letraset с образцами Lorem Ipsum в 60-х годах и, в более недавнее время, программы электронной вёрстки типа Aldus PageMaker, в шаблонах которых используется Lorem Ipsum. Lorem Ipsum - это текст-\"рыба\", часто используемый в печати и вэб-дизайне. Lorem Ipsum является стандартной \"рыбой\" для текстов на латинице с начала XVI века. В то время некий безымянный печатник создал большую коллекцию размеров и форм шрифтов, используя Lorem Ipsum для распечатки образцов. Lorem Ipsum не только успешно пережил без заметных изменений пять веков, но и перешагнул в электронный дизайн. Его популяризации в новое время послужили публикация листов Letraset с образцами Lorem Ipsum в 60-х годах и, в более недавнее время, программы электронной вёрстки типа Aldus PageMaker, в шаблонах которых используется Lorem Ipsum."
//"private content"
/**
 * A simple [Fragment] subclass.
 * Use the [Lab6Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab6Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab6, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentContext = context!!
//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            Log.d(TAG, "IO - ok")
//        } else {
//            Log.d(TAG, "not ok")
//        }
        val cryptoHelper = CryptoHelper(context = fragmentContext)
        externalFilesDir = "${context?.getExternalFilesDir(null)}/lab6/"
        val tvSecretKey = view.findViewById<TextView>(R.id.tv_secretKey)
        val tvLastLog = view.findViewById<TextView>(R.id.tv_log)
        val tvFileContent = view.findViewById<TextView>(R.id.tv_fileContent)
        view.findViewById<Button>(R.id.btn_encrypt).isEnabled = false
        view.findViewById<Button>(R.id.btn_decrypt).isEnabled = false
        view.findViewById<Button>(R.id.btn_generate).setOnClickListener {
            clearFile = cryptoHelper.createFileWithContent(customContent.toByteArray(), FILENAME_CLEAR)
            Log.d(TAG, lsDir())
            view.findViewById<Button>(R.id.btn_encrypt).isEnabled = true
            view.findViewById<Button>(R.id.btn_decrypt).isEnabled = false
            view.findViewById<Button>(R.id.btn_generate).isEnabled = false
            tvLastLog.text = "Generated new file!"
            tvFileContent.text = clearFile.readText()
            tvSecretKey.text = "No key generated yet"
        }
        view.findViewById<Button>(R.id.btn_encrypt).setOnClickListener {
            //        Creating file with encrypted data
//            encryptedFileSeparated = cryptoHelper.encrypt(clearFile.readBytes())
//            encryptedFile = cryptoHelper.createFileWithContent(cryptoHelper.encryptNew(clearFile.readBytes(), FILENAME_ENCRYPTED))
            encryptedFile = cryptoHelper.encryptNew(clearFile.readBytes(), FILENAME_ENCRYPTED)
            Log.d(TAG, lsDir())
//            encryptedFile = createFileWithContent(encryptedBytes, "encrypted.txt")
//            val testDecryptedFile = cryptoHelper.createFileWithContent(cryptoHelper.decrypt(encryptedParts), "partsDecrypted.txt")
            view.findViewById<Button>(R.id.btn_decrypt).isEnabled = true
            view.findViewById<Button>(R.id.btn_encrypt).isEnabled = false
            tvLastLog.text = "Encrypted clear file content!"
            tvSecretKey.text = "Secret key - ${cryptoHelper.key?.encoded}"
//            tvFileContent.text = encryptedFile.readText()
        }
        view.findViewById<Button>(R.id.btn_decrypt).setOnClickListener {
            //        Decrypting file data
//            val decryptCipher = Cipher.getInstance("AES")
//            decryptCipher.init(Cipher.DECRYPT_MODE, sKey)
//            val decryptedByteArray = decryptCipher.doFinal(encryptedFile.readBytes())
//            for part files
//            val decryptedBytes = cryptoHelper.decrypt(encryptedFileSeparated)
//            val decryptedBytes = cryptoHelper.decrypt(encryptedFile.readBytes())
//            val decryptedBytes = cryptoHelper.decryptNew(encryptedFile.readBytes())
            val decryptedFile = cryptoHelper.decryptNew(encryptedFile.readBytes(), FILENAME_DECRYPTED)
            Log.d(TAG, lsDir())
            view.findViewById<Button>(R.id.btn_decrypt).isEnabled = false
//            view.findViewById<Button>(R.id.btn_generate).isEnabled = true
            tvLastLog.text = "File decrypted!"
            tvFileContent.text = decryptedFile.readText()
            Log.d(
                TAG,
                context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath!!
            )
        }
    }

    fun lsDir(): String {
        val rootDir = File(externalFilesDir).listFiles()
        var res = ""
        for (file in rootDir) {
            res += "${file.name}, "
        }
        return res
    }

//    private fun isExternalStorageReadOnly(): Boolean {
//        val extStorageState = Environment.getExternalStorageState()
//        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
//    }
//
//    private fun isExternalStorageAvailable(): Boolean {
//        val extStorageState = Environment.getExternalStorageState()
//        return Environment.MEDIA_MOUNTED == extStorageState
//    }

    //
//    private fun createFileWithContent(content: String, fileName: String): File {
//        val file = File("/storage/emulated/0/${Environment.DIRECTORY_DOCUMENTS}/$fileName")
//        val fileWriter = FileWriter(file)
//        fileWriter.write(content)
//        fileWriter.close()
//        return file
//    }
//
//    private fun createFileWithContent(content: ByteArray, fileName: String): File {
//        val file = File("/storage/emulated/0/${Environment.DIRECTORY_DOCUMENTS}/$fileName")
//        Log.d(TAG,"File exists - ${file.exists()}")
//        file.writeBytes(content)
////        val fos = FileOutputStream(file)
////        fos.write(content)
////        fos.close()
//        return file
//    }
//    fun createFileWithContent(content: ByteArray, fileName: String): File {
//        val file =
//            File("${context?.getExternalFilesDir(null)}/lab6/$fileName")
//        Log.d(TAG, "File ${file.absolutePath} is exists - ${file.exists()}")
//        if (file.exists()) {
//            file.delete()
//            Log.d(TAG, "Deleted ${file.name} - ${file.delete()}")
//        } else {
//            file.createNewFile()
//            Log.d(TAG, "Created ${file.name} - ${file.createNewFile()}")
//        }
//        Log.d(TAG, "File ${file.absolutePath} is exists - ${file.exists()}")
//        file.writeBytes(content)
////        val fos = FileOutputStream(file, false)
////        fos.write(content)
////        fos.close()
////        Log.d(TAG, "File ${file.absolutePath} is exists - ${file.exists()}")
//        return file
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab6Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Lab6Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}