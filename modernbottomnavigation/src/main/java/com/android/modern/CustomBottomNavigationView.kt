package  com.android.modern

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ThemeEnforcement

/**
 * Created by Albert on 2020/2/26.
 * Description: Main attribute methods
 */
fun dp2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

@SuppressLint("RestrictedApi")
open class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context!!, attrs, defStyleAttr) {
    private var mShiftAmount = 0f
    private var mScaleUpFactor = 0f
    private var mScaleDownFactor = 0f
    private var animationRecord = false
    private var mLargeLabelSize = 0f
    private var mSmallLabelSize = 0f
    private var visibilityTextSizeRecord = false
    private var visibilityHeightRecord = false
    private var mItemHeight = 0
    private var textVisibility = true
    private var mViewPager: ViewPager2? = null
    private var mMyOnNavigationItemSelectedListener: OnBottomItemSelectedListener? = null
    private var mPageChangeListener: OnNavigationPageChangeListener? = null
    private var mMenuView: BottomNavigationMenuView? = null
    private var mButtons: Array<BottomNavigationItemView?>?= null
    open fun setIconVisibility(visibility: Boolean): CustomBottomNavigationView { // 1. get mMenuView
      //  val mMenuView = bottomNavigationMenuView
        val mMenuView = obtainBottomNavigationMenuView()
        // 2. get mButtons
        val mButtons = obtainBottomNavigationItemViews()
        // 3. get mIcon in mButtons
        for (button in mButtons!!) {
            val mIcon = getField<ImageView>((button as BottomNavigationItemView).javaClass, button, "icon")!!
            // 4. set mIcon visibility gone
            mIcon.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
        }
        // 5. change mItemHeight to only text size in mMenuView
        if (!visibility) { // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                mItemHeight = itemHeight
            }
            // change mItemHeight
            val button = mButtons[0]
            if (null != button) {
                val mIcon = getField<ImageView>(button.javaClass, button, "icon")
                mIcon?.post { setItemHeight(mItemHeight - mIcon.measuredHeight) }
            }
        } else {
            if (!visibilityHeightRecord) return this
            setItemHeight(mItemHeight)
        }
        mMenuView!!.updateMenuView()
        return this
    }

    open fun setTextVisibility(visibility: Boolean): CustomBottomNavigationView {
        textVisibility = visibility
        // 1. get mMenuView
      // val mMenuView = bottomNavigationMenuView
        val mMenuView = obtainBottomNavigationMenuView()
        // 2. get mButtons
        val mButtons = obtainBottomNavigationItemViews()
        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>((button as BottomNavigationItemView).javaClass, button, "largeLabel")!!
            val mSmallLabel = getField<TextView>(button.javaClass, button, "smallLabel")!!
            if (!visibility) {
                if (!visibilityTextSizeRecord && !animationRecord) {
                    visibilityTextSizeRecord = true
                    mLargeLabelSize = mLargeLabel.textSize
                    mSmallLabelSize = mSmallLabel.textSize
                }
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)
                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)
            } else {
                if (!visibilityTextSizeRecord) break
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize)
                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
            }
        }
        // 4 change mItemHeight to only icon size in mMenuView
        if (!visibility) {
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                mItemHeight = itemHeight
            }
            setItemHeight(
                mItemHeight - CustomBottomNavigationView.Companion.getFontHeight(
                    mSmallLabelSize
                )
            )
        } else {
            if (!visibilityHeightRecord) return this
            setItemHeight(mItemHeight)
        }
        mMenuView!!.updateMenuView()
        return this
    }

    open fun animationEnabled(enable: Boolean): CustomBottomNavigationView { // 1. get mMenuView
       // val mMenuView = bottomNavigationMenuView enableAnimation
        val mMenuView = obtainBottomNavigationMenuView()
        // 2. get mButtons
        val mButtons = obtainBottomNavigationItemViews()
        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>((button as BottomNavigationItemView).javaClass, button, "largeLabel")!!
            val mSmallLabel = getField<TextView>(button.javaClass, button, "smallLabel")!!
            if (!enable) {
                if (!animationRecord) {
                    animationRecord = true
                    mShiftAmount = getField<Float>(button.javaClass, button, "shiftAmount")!!
                    mScaleUpFactor = getField<Float>(button.javaClass, button, "scaleUpFactor")!!
                    mScaleDownFactor = getField<Float>(button.javaClass, button, "scaleDownFactor")!!
                    mLargeLabelSize = mLargeLabel.textSize
                    mSmallLabelSize = mSmallLabel.textSize
                }
                setField(button.javaClass, button, "shiftAmount", 0)
                setField(button.javaClass, button, "scaleUpFactor", 1)
                setField(button.javaClass, button, "scaleDownFactor", 1)
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
            } else {
                if (!animationRecord) return this
                setField(button.javaClass, button, "shiftAmount", mShiftAmount)
                setField(button.javaClass, button, "scaleUpFactor", mScaleUpFactor)
                setField(button.javaClass, button, "scaleDownFactor", mScaleDownFactor)
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize)
            }
        }
        mMenuView!!.updateMenuView()
        return this
    }

    @SuppressLint("WrongConstant")
    @Deprecated("")
    open fun shiftingModeEnabled(enable: Boolean): CustomBottomNavigationView {
        labelVisibilityMode = if (enable) 0 else 1
        return this
    }

    @Deprecated("")
    open fun itemShiftingModeEnabled(enable: Boolean): CustomBottomNavigationView {
        isItemHorizontalTranslationEnabled = enable
        return this
    }

    open fun obtainCurrentItem(): Int {
        val mButtons: Array<BottomNavigationItemView?>? = obtainBottomNavigationItemViews()
        val menu = menu
        for (i in mButtons!!.indices) {
            if (menu.getItem(i).isChecked) {
                return i
            }
        }
        return 0
    }

    open fun obtainBottomNavigationItemViews(): Array<BottomNavigationItemView?>? {
        if (null != mButtons) return mButtons
        val mMenuView: BottomNavigationMenuView? = obtainBottomNavigationMenuView()
        mButtons = getField<Array<BottomNavigationItemView?>>(
            (mMenuView as BottomNavigationMenuView).javaClass,
            mMenuView,
            "buttons"
        )
        return mButtons
    }

    open fun obtainBottomNavigationMenuView(): BottomNavigationMenuView? {
        if (null == mMenuView) mMenuView =
            getField<BottomNavigationMenuView>(BottomNavigationView::class.java, this, "menuView")
        return mMenuView
    }

    open fun getMenuItemPosition(item: MenuItem): Int {
        val itemId = item.itemId
        val menu = menu
        val size = menu.size()
        for (i in 0 until size) {
            if (menu.getItem(i).itemId == itemId) {
                return i
            }
        }
        return -1
    }

    open fun setCurrentItem(index: Int): CustomBottomNavigationView {
        selectedItemId = menu.getItem(index).itemId
        return this
    }

    open fun getOnNavigationItemSelectedListener(): OnNavigationItemSelectedListener {
        return getField<OnNavigationItemSelectedListener>(
            BottomNavigationView::class.java, this, "selectedListener"
        )!!
    }

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        if (null == mMyOnNavigationItemSelectedListener) {
            super.setOnNavigationItemSelectedListener(listener)
            return
        }
        mMyOnNavigationItemSelectedListener!!.setOnNavigationItemSelectedListener(listener)
    }

    open fun clearIconTintColor(): CustomBottomNavigationView {
      obtainBottomNavigationMenuView()!!.iconTintList=null
       return this
    }

    open fun obtainBottomNavigationItemView(position: Int): BottomNavigationItemView {
        val obtainBottomNavigationItemViews = obtainBottomNavigationItemViews()
        return obtainBottomNavigationItemViews!![position]!!
    }

    open fun getIconAt(position: Int): ImageView {
        val mButtons = obtainBottomNavigationItemView(position)
        return (getField<ImageView>(
            BottomNavigationItemView::class.java, mButtons, "icon"
        ))!!
    }

    open fun getSmallLabelAt(position: Int): TextView {
        val mButtons = obtainBottomNavigationItemView(position)
        return (getField<TextView>(
            BottomNavigationItemView::class.java, mButtons, "smallLabel"
        ))!!
    }

    open fun getLargeLabelAt(position: Int): TextView {
        val mButtons = obtainBottomNavigationItemView(position)
        return (getField<TextView>(
            BottomNavigationItemView::class.java, mButtons, "largeLabel"
        ))!!
    }


    open fun obtainItemCount(): Int {
        val bottomNavigationItemViews: Array<BottomNavigationItemView?> =obtainBottomNavigationItemViews() ?: return 0
        return bottomNavigationItemViews.size
    }

    open fun setSmallTextSize(sp: Float): CustomBottomNavigationView {
        val count = obtainItemCount()
        for (i in 0 until count) {
            getSmallLabelAt(i).textSize = sp
        }
        mMenuView!!.updateMenuView()
        return this
    }

    open fun setLargeTextSize(sp: Float): CustomBottomNavigationView {
        val count = obtainItemCount()
        for (i in 0 until count) {
            val tvLarge = getLargeLabelAt(i)
            if (null != tvLarge) tvLarge.textSize = sp
        }
        mMenuView!!.updateMenuView()
        return this
    }

    open fun setTextSize(sp: Float): CustomBottomNavigationView {
        setLargeTextSize(sp)
        setSmallTextSize(sp)
        return this
    }

    open fun setIconSizeAt(position: Int, width: Float, height: Float): CustomBottomNavigationView {
        val icon = getIconAt(position)
        val layoutParams = icon.layoutParams
        layoutParams.width =dp2px(context, width)
        layoutParams.height = dp2px(context, height)
        icon.layoutParams = layoutParams
        mMenuView!!.updateMenuView()
        return this
    }

    open fun setIconSize(width: Float, height: Float): CustomBottomNavigationView {
        val count = obtainItemCount()
        for (i in 0 until count) {
            setIconSizeAt(i, width, height)
        }
        return this
    }

    open fun setIconSize(dpSize: Float): CustomBottomNavigationView {
        itemIconSize = dp2px(context, dpSize)
        return this
    }

    open fun setItemHeight(height: Int): CustomBottomNavigationView { // 1. get mMenuView
        val obtainBottomNavigationMenuView = obtainBottomNavigationMenuView()
        val mMenuView = (obtainBottomNavigationMenuView).apply {
            // 2. set private final int mItemHeight in mMenuView
            setField(javaClass, this, "itemHeight", height)
        }
        mMenuView!!.updateMenuView()
        return this

    }

    open fun obtainItemHeight(): Int { // 1. get mMenuView
        val mMenuView: BottomNavigationMenuView? = obtainBottomNavigationMenuView()
        // 2. get private final int mItemHeight in mMenuView
        return getField<Int>((mMenuView as BottomNavigationMenuView) .javaClass, mMenuView, "itemHeight")!!
    }


    // 1. get mMenuView
    open val itemHeight: Int
        // 2. get private final int mItemHeight in mMenuView
        get() { // 1. get mMenuView
            val mMenuView = obtainBottomNavigationMenuView()
            // 2. get private final int mItemHeight in mMenuView
            return getField<Int>((mMenuView as BottomNavigationMenuView).javaClass, mMenuView, "itemHeight")!!
        }

    open fun setTypeface(typeface: Typeface?, style: Int): CustomBottomNavigationView {
        val count = obtainItemCount()
        for (i in 0 until count) {
            getLargeLabelAt(i).setTypeface(typeface, style)
            getSmallLabelAt(i).setTypeface(typeface, style)
        }
        mMenuView!!.updateMenuView()
        return this
    }

    open fun setTypeface(typeface: Typeface?): CustomBottomNavigationView {
        val count = obtainItemCount()
        for (i in 0 until count) {
            getLargeLabelAt(i).typeface = typeface
            getSmallLabelAt(i).typeface = typeface
        }
        mMenuView!!.updateMenuView()
        return this
    }

    private fun <T> getField(targetClass: Class<*>, instance: Any?, fieldName: String): T? {
        try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            return field[instance] as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun setField(targetClass: Class<*>, instance: Any?, fieldName: String, value: Any) {
        try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field[instance] = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmOverloads
    open fun setupWithViewPager(
        viewPager: ViewPager2?,
        smoothScroll: Boolean = false
    ): CustomBottomNavigationView {
        if (mViewPager != null) { // If we've already been setup with a ViewPager, remove us from it
            if (mPageChangeListener != null) {
                mViewPager!!.unregisterOnPageChangeCallback(mPageChangeListener!!)
            }
        }
        if (null == viewPager) {
            mViewPager = null
            super.setOnNavigationItemSelectedListener(null)
            return this
        }
        mViewPager = viewPager
        if (mPageChangeListener == null) {
            mPageChangeListener = OnNavigationPageChangeListener(this, CustomBottomNavigationView.Companion.isNavigationItemClicking
            )
        }
        viewPager.registerOnPageChangeCallback(mPageChangeListener!!)
        val listener = getOnNavigationItemSelectedListener()
        mMyOnNavigationItemSelectedListener = OnBottomItemSelectedListener(viewPager, this, smoothScroll, listener, CustomBottomNavigationView.Companion.isNavigationItemClicking)
        super.setOnNavigationItemSelectedListener(mMyOnNavigationItemSelectedListener)
        return this
    }

    open fun shiftingModeEnabled(position: Int, enable: Boolean): CustomBottomNavigationView {
        obtainBottomNavigationItemView(position).setShifting(enable)
        return this
    }

    open fun setItemBackground(position: Int, background: Int): CustomBottomNavigationView {
        obtainBottomNavigationItemView(position).setItemBackground(background)
        return this
    }

    open fun setIconTintList(position: Int, tint: ColorStateList?): CustomBottomNavigationView {
        obtainBottomNavigationItemView(position).setIconTintList(tint)
        return this
    }

    open fun setTextTintList(position: Int, tint: ColorStateList?): CustomBottomNavigationView {
        obtainBottomNavigationItemView(position).setTextColor(tint)
        return this
    }

    open fun setIconsMarginTop(marginTop: Int): CustomBottomNavigationView {
        for (i in 0 until obtainItemCount()) {
            setIconMarginTop(i, marginTop)
        }
        return this
    }

    open fun setIconMarginTop(position: Int, marginTop: Int): CustomBottomNavigationView {
        val itemView = obtainBottomNavigationItemView(position)
        setField(BottomNavigationItemView::class.java, itemView, "defaultMargin", marginTop)
        mMenuView!!.updateMenuView()
        return this
    }

    companion object {
        private const val isNavigationItemClicking = false
        private fun getFontHeight(fontSize: Float): Int {
            val paint = Paint()
            paint.textSize = fontSize
            val fm = paint.fontMetrics
            return Math.ceil(fm.descent - fm.top.toDouble()).toInt() + 2
        }
    }

    init {
        val a = ThemeEnforcement.obtainTintedStyledAttributes(
            context!!, attrs,
            R.styleable.BottomNavigationView,
            defStyleAttr, R.style.Widget_Design_BottomNavigationView,
            R.styleable.BottomNavigationView_itemTextAppearanceInactive,
            R.styleable.BottomNavigationView_itemTextAppearanceActive
        )
        // clear if you don't have set item icon tint list
        if (!a.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            clearIconTintColor()
        }
        a.recycle()
    }
}