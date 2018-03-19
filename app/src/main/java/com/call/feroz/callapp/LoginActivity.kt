package com.call.feroz.callapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.call.feroz.callapp.pojo.Contact
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*












class LoginActivity : AppCompatActivity(),View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private val REQUEST_READ_CONTACTS = 444
    var contactList: ArrayList<String>? = null
    val contact_list: ArrayList<Contact>? = ArrayList<Contact>()

    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mDatabase: FirebaseDatabase? = null
    private var max_user_id: Int? = -1
    private val TAG = "PermissionDemo"
    private var counter: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        google_login.setOnClickListener(this)
        btnLogin.setOnClickListener(this)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        mDatabase = FirebaseDatabase.getInstance();
        var abc:DatabaseReference = mDatabase!!.getReference("max_user_id");


        abc.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.e("Firebase:", "error!")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Firebase:", "current_week:" + snapshot.value)
                max_user_id = (snapshot.value as Long).toInt() as Int?

                max_user_id = max_user_id?.plus(1);
            }

        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.google_login -> {
                signIn(this.mGoogleApiClient!!, RC_SIGN_IN, this)
            }
            R.id.btnLogin -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("contactList",contactList);

                startActivity(intent)

            }
            else -> {
                // else condition
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent) {
        Log.v("requestCode", "requestCode-------------------" + requestCode)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                handleSignInResult(result, this)
            } else {
            }
        }

    }

    fun signIn(mGoogleApiClient: GoogleApiClient , RC_SIGN_IN: Int,context: LoginActivity){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        context.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleSignInResult( result: GoogleSignInResult,context:Context){
        if (result.isSuccess()) {

            val acct = result.signInAccount
            System.out.println("getDisplayName "+acct?.displayName);
            System.out.println("getEmail "+acct?.email);
            System.out.println("getPhotoUrl "+acct?.photoUrl);
            val context = HashMap<String, String>()

            context.put("name", acct?.displayName!!);
            context.put("email", acct?.email!!);
            context.put("photoUrl", acct?.photoUrl.toString());
            var users:DatabaseReference = mDatabase!!.getReference("users");
            val child = users.child(max_user_id.toString()).setValue(context);

        }

    }








}




