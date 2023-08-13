package edu.bu.homies.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.lifecycle.ViewModelProvider
import edu.bu.homies.R
import edu.bu.homies.data.Home
import edu.bu.homies.viewmodel.CurHomeViewModel
import edu.bu.homies.viewmodel.HomeListViewModel

class AddHome : AppCompatActivity() {

    private lateinit var listViewModel: HomeListViewModel
    private lateinit var viewModel: CurHomeViewModel

    private lateinit var projTitle: EditText
    private lateinit var projDesc: EditText
    private lateinit var projLinks: EditText
    private lateinit var projKeywords: EditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var projFavorite: Switch
    private lateinit var submit: Button
    private lateinit var cancel: Button

    companion object {
        private const val TAG = "PP:AddHome"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        Log.v(TAG,"onCreate")

        listViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)
        viewModel = ViewModelProvider(this).get(CurHomeViewModel::class.java)

        prepareTextViews()
        prepareButtons()
    }

    /**
     * Prepares the buttons
     */
    private fun prepareButtons() {
        Log.v(TAG,"prepareButtons")

        submit = findViewById(R.id.submit)
        cancel = findViewById(R.id.cancel)

        submit.setOnClickListener {

            val newHome = Home(
                0,
                projTitle.text.toString(),
                projDesc.text.toString(),
                projLinks.text.split(",").map { it.trim() }.toTypedArray(),
                projKeywords.text.split(",").map { it.trim() }.toTypedArray(),
                projFavorite.isChecked,
            )

            listViewModel.addProject(newHome)
            viewModel.setCurProject(newHome)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun prepareTextViews(){
        Log.v(TAG,"prepareTextViews")

        projTitle = findViewById(R.id.titleAdd)
        projDesc =  findViewById(R.id.descriptionAdd)
        projLinks = findViewById(R.id.linksAdd)
        projKeywords = findViewById(R.id.keywordsAdd)
        projFavorite = findViewById(R.id.projFavoriteSwitch)
    }
}