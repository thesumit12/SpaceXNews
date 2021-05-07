package com.slakra.common

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding> constructor(
        @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<VDB>(this, layoutResId).run {
            this.lifecycleOwner = this@BaseActivity
            initComponents(savedInstanceState, this)
        }
    }

    // initialize other components required after onCreate
    abstract fun initComponents(savedInstanceState: Bundle?, binding: VDB)

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(@StringRes msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}