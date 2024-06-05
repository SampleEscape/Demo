package com.escape.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.escape.demo.databinding.ActivityMainBinding
import com.escape.demo.extension.repeatOnStarted
import com.escape.demo.list.category.CategoryItemListener
import com.escape.demo.list.group.CategoryGroupAdapter
import com.escape.demo.list.group.CategoryGroupListener
import com.escape.demo.model.CategoryGroup
import com.escape.demo.model.CategoryInfo
import com.escape.demo.model.CategoryItem
import com.escape.demo.model.MainEvent
import kotlinx.coroutines.flow.Flow

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>(
        factoryProducer = { MainViewModel.factory() }
    )

    private val categoryAdapter = CategoryGroupAdapter(
        groupListener = object: CategoryGroupListener {
            override fun isExpandFlow(item: CategoryGroup): Flow<Boolean> {
                return viewModel.isExpandFlow(item)
            }

            override fun isLastSelectedFlow(item: CategoryGroup): Flow<Boolean> {
                return viewModel.isLastSelectedFlow(item)
            }

            override fun onClick(item: CategoryGroup, position: Int) {
                viewModel.setExpandGroup(item)
                binding.list.smoothScrollToPosition(position)
            }
        },
        itemListener = object: CategoryItemListener {
            override fun isLastSelectedFlow(item: CategoryItem): Flow<Boolean> {
                return viewModel.isLastSelectedFlow(item)
            }

            override fun onClick(item: CategoryItem, position: Int) {
                executeItem(item)
            }
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCategoryGroup()
        setUpCollectEvent()
    }

    private fun setUpCategoryGroup() {
        categoryAdapter.submitList(CategoryInfo.list)

        binding.list.apply {
            adapter = categoryAdapter
        }
    }

    private fun setUpCollectEvent() {
        repeatOnStarted {
            viewModel.eventChannel.collect { event ->
                when (event) {
                    is MainEvent.ScrollCategory -> scrollCategoryGroup(event.group)
                    is MainEvent.ExecuteItem -> executeItem(event.item)
                }
            }
        }
    }

    private fun scrollCategoryGroup(item: CategoryGroup) {
        if (!::binding.isInitialized) {
            return
        }
        if (binding.list.width == 0 || binding.list.height == 0) {
            binding.list.post {
                scrollCategoryGroup(item)
            }
        } else {
            val list = CategoryInfo.list
            categoryAdapter.submitList(list) {
                val position = categoryAdapter.getPosition(item)
                if (position != RecyclerView.NO_POSITION) {
                    binding.list.smoothScrollToPosition(position)
                }
            }
        }
    }

    private fun executeItem(item: CategoryItem) {
        val param = DemoParam(item)
        val intent = param.buildIntent(applicationContext)
        startActivity(intent)
    }
}