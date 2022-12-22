package ir.rev.root.catFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.rev.root.models.Cat
import ir.rev.root.repository.CatRepository
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper

class CatViewModel : ViewModel() {

    private val catRepository = CatRepository()

    /*
    * инкапсуляция лайвдаты, во вне торчит НЕмутабильная лайвдата
    */
    private val _catListLiveData = MutableLiveData<List<BaseCat>>()
    val catListLiveData: LiveData<List<BaseCat>> = _catListLiveData

    /**
     * Позволяет унифицировать механизм взаимодействия между вью-моделью и фрагментом.
     */
    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)

    /**
     * Прокси обёртка для фрагмента.
     */
    val action
        get() = actionWrapper.action

    init {
        catRepository.catListSubscribe.subscribe { catList ->
            _catListLiveData.postValue(catList.createViewModels())
        }
        subscribeToCatList(0)
    }

    /**
     * механизм взаимодействия между вью-моделью и фрагментом
     * @param receivedAction действие, см. [InOutDocumentsListViewModelAction].
     */
    private fun handleAction(receivedAction: CatListViewModelAction) {
        when (receivedAction) {
            is DeleteCat -> deleteCat(receivedAction.catNumber)
            else -> {
                /* тут должно быть пусто*/
            }
        }
    }

    /**
     *  пытаемся удалить котенка
     */
    private fun deleteCat(catName: String) {
        if (catRepository.deleteCat(catName)) {
            action.post(ShowToast("Котенок был удален"))
        } else {
            action.post(ShowToast("Котенок не захотел уходить, но растроился =("))
        }
    }

    /**
     * экстеншен функция для List<Cat>
     * создает вьюмодели
     */
    private fun List<Cat>.createViewModels(): List<BaseCat> {
        return map {
            when (it.group) {
                "black" -> BlackCat(name = it.name, group = it.group)
                "orange" -> OrangeCat(name = it.name, group = it.group)
                "white" -> WhiteCat(name = it.name, group = it.group)
                else -> OtherCat(name = it.name, group = it.group)
            }
        }
    }

    /**
     * Следует вызывать в момент достижения конца при прокручивании
     */
    fun refreshByScroll() {
        subscribeToCatList(catListLiveData.value?.size ?: 0)
    }

    /**
     * метод для подписки, ни чего не возвращает, НО под капотом обновляет подписку catRepository.catListSubscribe,
     * на которую мы подписались в init{}
     */
    private fun subscribeToCatList(position: Int) {
        catRepository.subscribeCatsList(position, COUNT_CAT)
    }

    private companion object {
        const val COUNT_CAT = 10
    }
}