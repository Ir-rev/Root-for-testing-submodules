package ir.rev.root.firstFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.rev.root.models.Cat
import ir.rev.root.repository.CatRepository

class CatViewModel : ViewModel() {

    private val catRepository = CatRepository()

    /*
    * инкапсуляция лайвдаты, во вне торчит НЕмутабильная лайвдата
    */
    private val _catListLiveData = MutableLiveData<Cat>()
    val catListLiveData: LiveData<Cat> = _catListLiveData

    init {
        catRepository.catListSubscribe.subscribe {

        }
        subscribeToCatList(0)
    }

    private fun subscribeToCatList(position: Int) {
        catRepository.subscribeCatsList(position, COUNT_CAT)
    }

    private companion object {
        const val COUNT_CAT = 10
    }
}