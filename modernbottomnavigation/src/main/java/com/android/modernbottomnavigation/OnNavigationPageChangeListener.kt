package  com.android.modernbottomnavigation

import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import java.lang.ref.WeakReference
/**
 * Created by Albert on 2020/2/26.
 * Description: Page Change Listener Event
 */
class OnNavigationPageChangeListener(
    bnve: CustomBottomNavigationView,
    isNavigationItemClicking: Boolean
) :
    OnPageChangeCallback() {
    private var mBnveRef: WeakReference<CustomBottomNavigationView>
    var isNavigationItemClicking: Boolean
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(
        position: Int, positionOffset: Float,
        positionOffsetPixels: Int
    ) {
    }

    override fun onPageSelected(position: Int) {
        var bnve = mBnveRef.get()
        if (null != bnve && !isNavigationItemClicking) bnve.setCurrentItem(position)
    }

    init {
        mBnveRef = WeakReference(bnve)
        this.isNavigationItemClicking = isNavigationItemClicking
    }
}