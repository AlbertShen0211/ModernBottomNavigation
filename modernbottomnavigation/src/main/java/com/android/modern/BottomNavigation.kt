package com.android.modern
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
/**
 * Created by Albert on 2020/2/26.
 * Description: Modern bottom navigation bar
 */
class BottomNavigation : CustomBottomNavigationView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun setIconVisibility(visibility: Boolean): CustomBottomNavigationView {
        return super.setIconVisibility(visibility)
    }

    override fun setTextVisibility(visibility: Boolean): CustomBottomNavigationView {
        return super.setTextVisibility(visibility)
    }

    override fun animationEnabled(enable: Boolean): CustomBottomNavigationView {
        return super.animationEnabled(enable)
    }

    override fun shiftingModeEnabled(enable: Boolean): CustomBottomNavigationView {
        return super.shiftingModeEnabled(enable)
    }

    override fun itemShiftingModeEnabled(enable: Boolean): CustomBottomNavigationView {
        return super.itemShiftingModeEnabled(enable)
    }

    override fun obtainCurrentItem(): Int {
        return super.obtainCurrentItem()
    }

    override fun getMenuItemPosition(item: MenuItem): Int {
        return super.getMenuItemPosition(item)
    }

    override fun setCurrentItem(index: Int): CustomBottomNavigationView {
        return super.setCurrentItem(index)
    }

    override fun getOnNavigationItemSelectedListener(): OnNavigationItemSelectedListener {
        return super.getOnNavigationItemSelectedListener()
    }

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        super.setOnNavigationItemSelectedListener(listener)
    }

    override fun obtainBottomNavigationMenuView(): BottomNavigationMenuView? {
        return super.obtainBottomNavigationMenuView()
    }

    override fun obtainBottomNavigationItemViews(): Array<BottomNavigationItemView?>? {
        return super.obtainBottomNavigationItemViews()
    }

    override fun obtainBottomNavigationItemView(position: Int): BottomNavigationItemView {
        return super.obtainBottomNavigationItemView(position)
    }

    override fun getIconAt(position: Int): ImageView {
        return super.getIconAt(position)
    }

    override fun getSmallLabelAt(position: Int): TextView {
        return super.getSmallLabelAt(position)
    }

    override fun getLargeLabelAt(position: Int): TextView {
        return super.getLargeLabelAt(position)
    }

    override fun obtainItemCount(): Int {
        return super.obtainItemCount()
    }

    override fun clearIconTintColor(): CustomBottomNavigationView {
        return super.clearIconTintColor()
    }

    override fun setSmallTextSize(sp: Float): CustomBottomNavigationView {
        return super.setSmallTextSize(sp)
    }

    override fun setLargeTextSize(sp: Float): CustomBottomNavigationView {
        return super.setLargeTextSize(sp)
    }

    override fun setTextSize(sp: Float): CustomBottomNavigationView {
        return super.setTextSize(sp)
    }

    override fun setIconSizeAt(
        position: Int,
        width: Float,
        height: Float
    ): CustomBottomNavigationView {
        return super.setIconSizeAt(position, width, height)
    }

    override fun setIconSize(width: Float, height: Float): CustomBottomNavigationView {
        return super.setIconSize(width, height)
    }

    override fun setIconSize(dpSize: Float): CustomBottomNavigationView {
        return super.setIconSize(dpSize)
    }

    override fun setItemHeight(height: Int): CustomBottomNavigationView {
        return super.setItemHeight(height)
    }

    override fun obtainItemHeight(): Int {
        return super.obtainItemHeight()
    }


    override fun setTypeface(typeface: Typeface?, style: Int): CustomBottomNavigationView {
        return super.setTypeface(typeface, style)
    }

    override fun setTypeface(typeface: Typeface?): CustomBottomNavigationView {
        return super.setTypeface(typeface)
    }

    fun setupWithViewPager(viewPager: ViewPager2): CustomBottomNavigationView {
        return setupWithViewPager(viewPager, false)
    }

    override fun setupWithViewPager(
        viewPager: ViewPager2?,
        smoothScroll: Boolean
    ): CustomBottomNavigationView {
        return super.setupWithViewPager(viewPager, smoothScroll)
    }

    override fun shiftingModeEnabled(position: Int, enable: Boolean): CustomBottomNavigationView {
        return super.shiftingModeEnabled(position, enable)
    }

    override fun setItemBackground(position: Int, background: Int): CustomBottomNavigationView {
        return super.setItemBackground(position, background)
    }

    override fun setIconTintList(position: Int, tint: ColorStateList?): CustomBottomNavigationView {
        return super.setIconTintList(position, tint)
    }

    override fun setTextTintList(position: Int, tint: ColorStateList?): CustomBottomNavigationView {
        return super.setTextTintList(position, tint)
    }

    override fun setIconsMarginTop(marginTop: Int): CustomBottomNavigationView {
        return super.setIconsMarginTop(marginTop)
    }

    override fun setIconMarginTop(position: Int, marginTop: Int): CustomBottomNavigationView {
        return super.setIconMarginTop(position, marginTop)
    }
}