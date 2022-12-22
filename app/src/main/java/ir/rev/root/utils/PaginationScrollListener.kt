package ir.rev.root.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Слушатель скролла до конца списка для исполнения пагинации.
 * @param context для создания экземпляра [GestureDetectorCompat].
 * @param loadNextPage Должен заружать новую страницу для пагинации.
 * @param scrollHelper экземпляр [ScrollHelper], который связан с нижней панелью навигации и скрывает её в процессе
 * прокрутки.
 * @property pageSize размер страницы.
 *
 * @author aa.sviridov
 */
class PaginationScrollListener(
    context: Context,
    loadNextPage: () -> Unit,
    private val pageSize: Int = DEFAULT_PAGE_SIZE
) : RecyclerView.OnScrollListener(),
    View.OnTouchListener,
    GestureDetector.OnGestureListener {

    /**
     * Детектор жестов, т.к. [RecyclerView.OnScrollListener] ничего не знает о жестах.
     */
    private var gestureDetector: GestureDetectorCompat? = GestureDetectorCompat(context, this@PaginationScrollListener)

    /**
     * Предыдущее событие скролла. Больше 0 - скролл вниз, меньше нуля - скролл вверх.
     */
    private var lastVerticalScrollDelta = 0F

    /**
     * true если последний элемент [RecyclerView] показан, иначе false.
     */
    private var lastIsShown = false

    /**
     * Должен заружать новую страницу для пагинации.
     */
    private var loadNextPage: (() -> Unit)? = loadNextPage

    /**
     * Освобождает ресурсы.
     */
    fun release() {
        gestureDetector = null
        loadNextPage = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        gestureDetector?.onTouchEvent(event)
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean = false

    override fun onShowPress(e: MotionEvent?) = Unit

    override fun onSingleTapUp(e: MotionEvent?): Boolean = false

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        lastVerticalScrollDelta = distanceY
        return false
    }

    override fun onLongPress(e: MotionEvent?) = Unit

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val capturedLoadNextPage = loadNextPage ?: return
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        if (dy > 0 && layoutManager.isNeedLoadNextPage()) {
            capturedLoadNextPage()
        }
        lastIsShown = layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val capturedLoadNextPage = loadNextPage ?: return
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_SETTLING
            && lastIsShown
            && lastVerticalScrollDelta > 0F) {
            capturedLoadNextPage()
        }
    }

    /**
     * Определение необходимости загрузки следующих страницы
     *
     * @return true, если следующая страница нужна, иначе false
     */
    private fun LinearLayoutManager.isNeedLoadNextPage(): Boolean =
        findLastVisibleItemPosition() >= itemCount - 1 - pageSize / 2

    companion object {
        /**
         * Размер страницы для пагинации по-умолчанию.
         */
        const val DEFAULT_PAGE_SIZE = 10
    }
}