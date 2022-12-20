package ir.rev.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch(Dispatchers.IO) {
            FoodPlugin.initFoodDataBase(context= applicationContext)
            FoodPlugin.getFoodListRepository().getFoodListObservable().subscribe {
                Log.d("checkResult", "onCreate: ${it.first}")
            }
        }
    }
}