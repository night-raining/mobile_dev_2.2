//package com.example.mobileapps22.lab4
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import com.example.mobileapps22.R
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.SignInButton
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task
//import okhttp3.MultipartBody
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody
//import org.json.JSONException
//import org.json.JSONObject
//
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [Lab4Fragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class Lab4Fragment : Fragment() {
//
//    private val RC_SIGN_IN: Int = 101
//    private val TAG = "FRAGMENT_OAUTH"
//
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_lab4, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val gso =
//                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestEmail()
//                        .requestProfile()
//                        .requestIdToken(R.string.server_client_id.toString())
//                        .requestServerAuthCode(R.string.server_client_id.toString())
//                        .build()
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        var mGoogleSignInClient = GoogleSignIn.getClient(this.context!!, gso) // todo: check activity parent result
//        view.findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
//            signIn(mGoogleSignInClient)
//        }
//        view.findViewById<Button>(R.id.btn_deauth).setOnClickListener {
//            mGoogleSignInClient.signOut()
//            view.findViewById<RelativeLayout>(R.id.rl_gAccContainer).visibility = View.INVISIBLE
//            view.findViewById<SignInButton>(R.id.sign_in_button).visibility = View.VISIBLE
//            view.findViewById<Button>(R.id.btn_deauth).visibility = View.GONE
//        }
//    }
//
//    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
//        val signInIntent: Intent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            val signInAccount: Task<GoogleSignInAccount> =
//                    GoogleSignIn.getSignedInAccountFromIntent(data)
////            val authCode = signInAccount.result?.serverAuthCode
////            Log.d(TAG, "Server auth code - $authCode")
////            val accessToken = getAccessToken(authCode)
//            handleSignInResult(signInAccount)
//        }
//    }
//
////    fun getAccessToken(authCode: String?) {
////        val client = OkHttpClient()
//////        val requestBody: RequestBody = RequestBody.create()
//////                .add("grant_type", "authorization_code")
//////                .add("client_id", getString(R.string.server_client_id))
//////                .add("client_secret", getString(R.string.client_secret))
//////                .add("code", authCode)
//////                .build()
//////        val requestBody =
////        val jsonObject = JSONObject()
////        jsonObject.put("grant_type", "authorization_code")
////        jsonObject.put("client_id", R.string.server_client_id)
////        jsonObject.put("client_secret", R.string.client_secret)
////        val request: Request = Request.Builder()
////                .url("https://www.googleapis.com/oauth2/v4/token")
////                .header("Content-Type", "application/x-www-form-urlencoded")
////                .post(requestBody)
////                .build()
////        client.newCall(request).enqueue(object : Callback() {
////            fun onFailure(request: Request?, e: IOException?) {}
////
////            @Throws(IOException::class)
////            fun onResponse(response: Response) {
////                try {
////                    val jsonObject = JSONObject(response.body().string())
////                    mAccessToken = jsonObject.get("access_token").toString()
////                    mTokenType = jsonObject.get("token_type").toString()
////                    mRefreshToken = jsonObject.get("refresh_token").toString()
////                } catch (e: JSONException) {
////                    e.printStackTrace()
////                }
////            }
////        })
////    }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account =
//                    completedTask.getResult(ApiException::class.java)
//            // Signed in successfully, show authenticated UI.
//            updateUI(account)
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
//        }
//    }
//
//    private fun updateUI(account: GoogleSignInAccount?) {
//        Log.d(TAG, "update UI returned. GoogleSignInAccount - ${account.toString()}")
//        view?.findViewById<RelativeLayout>(R.id.rl_gAccContainer)?.visibility = View.VISIBLE
//        view?.findViewById<SignInButton>(R.id.sign_in_button)?.visibility = View.GONE
//        view?.findViewById<Button>(R.id.btn_deauth)?.visibility = View.VISIBLE
//        view?.findViewById<TextView>(R.id.tv_gName)?.text = account?.displayName
//        val gAvatarUri = account?.photoUrl
//        view?.findViewById<ImageView>(R.id.iv_gAvatar)?.setImageURI(gAvatarUri)
//        view?.findViewById<TextView>(R.id.tv_gMail)?.text = account?.email
//        view?.findViewById<TextView>(R.id.tv_gId)?.text = account?.id
//        view?.findViewById<TextView>(R.id.tv_gIdToken)?.text = account?.idToken
//        view?.findViewById<TextView>(R.id.tv_gIdToken)?.text = account?.grantedScopes.toString()
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment Lab4Fragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                Lab4Fragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
//}
package com.example.mobileapps22.lab4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mobileapps22.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Lab4Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lab4Fragment : Fragment() {

    private val RC_SIGN_IN: Int = 101
    private val TAG = "FRAGMENT_OAUTH"

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
                        .build()

        // Build a GoogleSignInClient with the options specified by gso.
        var mGoogleSignInClient = GoogleSignIn.getClient(this.context!!, gso) // todo: check activity parent result
        view.findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signIn(mGoogleSignInClient)
        }
        view.findViewById<Button>(R.id.btn_deauth).setOnClickListener {
            mGoogleSignInClient.signOut()
            view.findViewById<RelativeLayout>(R.id.rl_gAccContainer).visibility = View.INVISIBLE
            view.findViewById<SignInButton>(R.id.sign_in_button).visibility = View.VISIBLE
            view.findViewById<Button>(R.id.btn_deauth).visibility = View.GONE
        }
    }

    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                    completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            Log.d(TAG, "Sign in account - $account")
            account?.serverAuthCode
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        Log.d(TAG, "update UI returned. GoogleSignInAccount - ${account.toString()}")
        view?.findViewById<RelativeLayout>(R.id.rl_gAccContainer)?.visibility = View.VISIBLE
        view?.findViewById<SignInButton>(R.id.sign_in_button)?.visibility = View.GONE
        view?.findViewById<Button>(R.id.btn_deauth)?.visibility = View.VISIBLE
        view?.findViewById<TextView>(R.id.tv_gName)?.text = account?.displayName
        val gAvatarUri = account?.photoUrl
        view?.findViewById<ImageView>(R.id.iv_gAvatar)?.setImageURI(gAvatarUri)
        view?.findViewById<TextView>(R.id.tv_gMail)?.text = account?.email
        view?.findViewById<TextView>(R.id.tv_gId)?.text = account?.id
        view?.findViewById<TextView>(R.id.tv_gIdToken)?.text = account?.idToken
        view?.findViewById<TextView>(R.id.tv_gIdToken)?.text = account?.grantedScopes.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lab4Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Lab4Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}