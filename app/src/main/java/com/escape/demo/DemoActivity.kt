package com.escape.demo

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.escape.demo.databinding.ActivityDemoBinding
import com.escape.demo.model.CategoryInfo

class DemoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDemoBinding

    private val viewModel by viewModels<DemoViewModel>(
        factoryProducer = { DemoViewModel.factory() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val param = DemoParam(intent)
        val item = CategoryInfo.findByKeyName(param.groupKeyName, param.itemKeyName)
        val fragment = item?.createFragment()
        if (item == null || fragment == null) {
            finish()
            return
        }

        viewModel.setLastSelectedItem(item)

        binding.title.text = item.title
        binding.iconBack.setOnClickListener {
            backPressed()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(binding.containerFragment.id, fragment, item.keyName)
            }
        }

        setUpBackPressCallback()
    }

    private fun setUpBackPressCallback() {
        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        })
    }

    private fun backPressed() {
        viewModel.clearExecuteItem()
        finish()
    }
}