package  com.android.modern

import android.util.SparseIntArray
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference

/**
 * Created by Albert on 2020/2/26.
 * Description: Bottom navigation item selected to listen
 */
class OnBottomItemSelectedListener internal constructor(
    viewPager: ViewPager2,
    bnve: CustomBottomNavigationView,
    smoothScroll: Boolean,
    listener: BottomNavigationView.OnNavigationItemSelectedListener?,
    isNavigationItemClicking: Boolean
) : BottomNavigationView.OnNavigationItemSelectedListener {
    private var listener: BottomNavigationView.OnNavigationItemSelectedListener?
    private val viewPagerRef: WeakReference<ViewPager2>
    private val smoothScroll: Boolean
    private var isNavigationItemClicking: Boolean
    private val items: SparseIntArray
    private var previousPosition = -1
    fun setOnNavigationItemSelectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
        this.listener = listener
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = items[item.itemId]
        if (previousPosition == position) {
            return true
        }
        if (null != listener) {
            val bool = listener!!.onNavigationItemSelected(item)
            if (!bool) return false
        }
        val viewPager = viewPagerRef.get() ?: return false
        isNavigationItemClicking = true
        viewPager.setCurrentItem(items[item.itemId], smoothScroll)
        isNavigationItemClicking = false
        previousPosition = position
        return true
    }

    init {
        viewPagerRef = WeakReference(viewPager)
        this.listener = listener
        this.smoothScroll = smoothScroll
        this.isNavigationItemClicking = isNavigationItemClicking
        val menu = bnve.menu
        val size = menu.size()
        items = SparseIntArray(size)
        for (i in 0 until size) {
            val itemId = menu.getItem(i).itemId
            items.put(itemId, i)
        }
    }
}