package com.android.example.aboutme.style

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.example.aboutme.R
import com.android.example.aboutme.databinding.ActivityDemoBinding
import com.android.modern.BottomNavigation
import com.android.modern.dp2px
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Albert on 2020/2/27.
 * Description:
 */

class DemoActivity : AppCompatActivity() {
    private var bind: ActivityDemoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_demo)
        init()
    }

    private fun init() {
        bind!!.bmnNoAnimation.animationEnabled(false)
        bind!!.bmnNoShiftingMode.shiftingModeEnabled(false)
        bind!!.bmnNoItemShiftingMode.itemShiftingModeEnabled(false)
        bind!!.bmnNoText.setTextVisibility(false)
        bind!!.bmnNoIcon.setIconVisibility(false)
        bind!!.bmnNoAnimationShiftingMode.animationEnabled(false)
        bind!!.bmnNoAnimationShiftingMode.shiftingModeEnabled(false)
        bind!!.bmnNoAnimationItemShiftingMode.animationEnabled(false)
        bind!!.bmnNoAnimationItemShiftingMode.itemShiftingModeEnabled(false)
        disableAllAnimation(bind!!.bmnNoAnimationShiftingModeItemShiftingMode)
        bind!!.bmnNoShiftingModeItemShiftingModeText.shiftingModeEnabled(false)
        bind!!.bmnNoShiftingModeItemShiftingModeText.itemShiftingModeEnabled(false)
        bind!!.bmnNoShiftingModeItemShiftingModeText.setTextVisibility(false)
        disableAllAnimation(bind!!.bmnNoAnimationShiftingModeItemShiftingModeText)
        bind!!.bmnNoAnimationShiftingModeItemShiftingModeText.setTextVisibility(false)
        bind!!.bmnNoShiftingModeItemShiftingModeAndIcon.shiftingModeEnabled(false)
        bind!!.bmnNoShiftingModeItemShiftingModeAndIcon.itemShiftingModeEnabled(false)
        bind!!.bmnNoShiftingModeItemShiftingModeAndIcon.setIconVisibility(false)
        bind!!.bmnNoItemShiftingModeIcon.itemShiftingModeEnabled(false)
        bind!!.bmnNoItemShiftingModeIcon.setIconVisibility(false)
        disableAllAnimation(bind!!.bmnNoAnimationShiftingModeItemShiftingModeIcon)
        bind!!.bmnNoAnimationShiftingModeItemShiftingModeIcon.setIconVisibility(false)
        disableAllAnimation(bind!!.bmnWithPadding)
        bind!!.bmnWithPadding.setIconVisibility(false)
        initCenterIconOnly()
        initSmallerText()
        initBiggerIcon()
        initCustomTypeface()
        bind!!.bmnIconSelector.animationEnabled(false)
        initMargin()
        initUncheckedFirstTime()
    }

    private fun disableAllAnimation(bmn: BottomNavigation) {
        bmn.animationEnabled(false)
        bmn.shiftingModeEnabled(false)
        bmn.itemShiftingModeEnabled(false)
    }

    private fun initCenterIconOnly() {
        disableAllAnimation(bind!!.bmnCenterIconOnly)
        val centerPosition = 2
        bind!!.bmnCenterIconOnly.setIconSizeAt(centerPosition, 48f, 48f)
        bind!!.bmnCenterIconOnly.setItemBackground(centerPosition, R.color.colorGreen)
        bind!!.bmnCenterIconOnly.setIconTintList(
            centerPosition,
            resources.getColorStateList(R.color.selector_item_gray_color)
        )
        bind!!.bmnCenterIconOnly.setIconMarginTop(centerPosition, dp2px(this, 4f))
        bind!!.bmnCenterIconOnly.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.menu_add) {
                Toast.makeText(this@DemoActivity, "add", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener false
            }
            true
        })
    }

    private fun initSmallerText() {
        disableAllAnimation(bind!!.bmnSmallerText)
        bind!!.bmnSmallerText.setTextSize(8f)
    }

    private fun initBiggerIcon() {
        disableAllAnimation(bind!!.bmnBiggerIcon)
        // hide text
        bind!!.bmnBiggerIcon.setTextVisibility(false)
        // set icon size
        val iconSize = 36
        bind!!.bmnBiggerIcon.setIconSize(iconSize.toFloat(), iconSize.toFloat())
        // set item height
        bind!!.bmnBiggerIcon.setItemHeight(dp2px(this, iconSize + 16.toFloat()))
    }

    private fun initCustomTypeface() {
        disableAllAnimation(bind!!.bmnCustomTypeface)
        // set typeface : bold
        bind!!.bmnCustomTypeface.setTypeface(Typeface.DEFAULT_BOLD)
    }

    private fun initMargin() {
        disableAllAnimation(bind!!.bmnIconMarginTop)
        bind!!.bmnIconMarginTop.setTextVisibility(false)
        bind!!.bmnIconMarginTop.setItemHeight(dp2px(this, 56f))
        bind!!.bmnIconMarginTop.setIconsMarginTop(dp2px(this, 16f))
          }

   
    private fun initUncheckedFirstTime() {
        disableAllAnimation(bind!!.bmnUncheckedFirstTime)
        // use the unchecked color for first item
        bind!!.bmnUncheckedFirstTime.setIconTintList(
            0, resources
                .getColorStateList(R.color.colorGray)
        )
        bind!!.bmnUncheckedFirstTime.setTextTintList(
            0, resources
                .getColorStateList(R.color.colorGray)
        )
        bind!!.bmnUncheckedFirstTime.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            private var firstClick = true
            override fun onNavigationItemSelected(item: MenuItem): Boolean { // restore the color when click
                if (firstClick) {
                    val position = bind!!.bmnUncheckedFirstTime.getMenuItemPosition(item)
                    if (0 == position) {
                        firstClick = false
                        bind!!.bmnUncheckedFirstTime.setIconTintList(
                            0, resources
                                .getColorStateList(R.color.selector_item_primary_color)
                        )
                        bind!!.bmnUncheckedFirstTime.setTextTintList(
                            0, resources
                                .getColorStateList(R.color.selector_item_primary_color)
                        )
                    }
                }
                // do other
                return true
            }
        })
    }
}