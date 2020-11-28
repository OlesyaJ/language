package com.example.language.onboarding.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.language.onboarding.data.TopicItem

class ChooseTopicsViewModel : ViewModel() {

    private val topicsList = listOf<TopicItem>(
        TopicItem(
            "",
            "Music"
        ),
        TopicItem(
            "",
            "Movies"
        ),
        TopicItem(
            "",
            "News"
        ),
        TopicItem(
            "",
            "Books"
        ),
        TopicItem(
            "",
            "Travel"
        ),
        TopicItem(
            "https://lh6.ggpht.com/HlgucZ0ylJAfZgusynnUwxNIgIp5htNhShF559x3dRXiuy_UdP3UQVLYW6c=s1200",
            "Art"
        ),
        TopicItem(
            "",
            "Games"
        )
    )

    private val _topics = MutableLiveData<List<TopicItem>>()
    val topics: LiveData<List<TopicItem>> = _topics

    init {
        _topics.value = topicsList
    }

}
