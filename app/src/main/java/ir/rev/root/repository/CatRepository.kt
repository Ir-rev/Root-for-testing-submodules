package ir.rev.root.repository

import io.reactivex.rxjava3.subjects.BehaviorSubject
import ir.rev.root.models.Cat

/**
 * Пример для пагинации. сюда не смотреть, у вас есть репозиторий для данных
 */
class CatRepository {

    /**
     * подписка на обновление списка
     */
    val catListSubscribe = BehaviorSubject.create<List<Cat>>()

    fun subscribeCatsList(position: Int, count: Int) {
        catListSubscribe.onNext(
            generateCatsList(position, count)
        )
    }

    private fun generateCatsList(position: Int, count: Int): List<Cat> {
        val resultList = mutableListOf<Cat>()
        for (i in 0 until count) {
            resultList.add(generateCat(position + i))
        }
        return resultList
    }

    private fun generateCat(i: Int): Cat {
        return Cat(
            name = "имя кота $i",
            group = when{
                (i in 0..10) -> "black"
                (i in 11..20) -> "orange"
                (i in 21..30) -> "white"
                else -> "other"
            }
        )
    }

}