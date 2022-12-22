package ir.rev.root.repository

import io.reactivex.rxjava3.subjects.BehaviorSubject
import ir.rev.root.models.Cat
import kotlin.random.Random

/**
 * Пример для пагинации. сюда не смотреть, у вас есть репозиторий для данных
 */
class CatRepository {

    /**
     * подписка на обновление списка
     */
    val catListSubscribe = BehaviorSubject.create<List<Cat>>()
    private val deletedCat = mutableSetOf<String>()
    private var lastPositionsAndCount: Pair<Int, Int>? = null

    fun subscribeCatsList(position: Int, count: Int) {
        lastPositionsAndCount = Pair(position, count)
        catListSubscribe.onNext(
            generateCatsList(position, count)
        )
    }

    /**
     * Есть вероятность что котенок не захочет уходить
     */
    fun deleteCat(catName: String): Boolean {
        return if (Random.nextBoolean()) {
            deletedCat.add(catName)
            lastPositionsAndCount?.let {
                subscribeCatsList(it.first, it.second)
            }
            true
        } else {
            false
        }
    }

    private fun generateCatsList(position: Int, count: Int): List<Cat> {
        val resultList = mutableListOf<Cat>()
        for (i in 0 until position + count) {
            if (deletedCat.contains(i.toString())) continue
            resultList.add(generateCat(i))
        }
        return resultList
    }

    private fun generateCat(i: Int): Cat {
        return Cat(
            name = "$i",
            group = when {
                (i in 0..10) -> "black"
                (i in 11..20) -> "orange"
                (i in 21..30) -> "white"
                else -> "other"
            }
        )
    }

}