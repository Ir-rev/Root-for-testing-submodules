package ir.rev.root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ir.rev.foodMaker.FoodPlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            FoodPlugin.initFoodDataBase(applicationContext)
            Log.d("checkResult", "onCreate: ${FoodPlugin.getFoodListRepository().checkDataBaseInit()}")
            withContext(Dispatchers.Main){
                setContentView(R.layout.activity_main)
            }
        }
    }
}