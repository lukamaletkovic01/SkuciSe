package com.example.skucise.LayoutActivities.RatePropertyActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.R

class CommentSentActivity : AppCompatActivity() {
    private var advertId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_sent)

        init()
    }

    private fun init(){
        advertId =  intent.getSerializableExtra("advertId") as Long
    }

    fun redirectToAdvert(view: View){
        if(advertId != null){
            val intent = Intent(this, ProductPageActivity::class.java)
            intent.putExtra("advertId", advertId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    public fun redirectHome(view: View){

        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}