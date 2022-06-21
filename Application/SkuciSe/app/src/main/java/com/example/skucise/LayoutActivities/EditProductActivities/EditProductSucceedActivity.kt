package com.example.skucise.LayoutActivities.EditProductActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.R

class EditProductSucceedActivity : AppCompatActivity() {

    private var advertId: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product_succeed)
        advertId = intent.getSerializableExtra("advertId") as Long

    }
    public fun redirectHome(view: View){

        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    fun gotoNewAdvert(view: View) {
        val intent = Intent(this, ProductPageActivity::class.java)
        intent.putExtra("advertId", advertId)
        startActivity(intent)
    }
}