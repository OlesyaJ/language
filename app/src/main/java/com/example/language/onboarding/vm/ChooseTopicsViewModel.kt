package com.example.language.onboarding.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.language.onboarding.data.TopicItem

class ChooseTopicsViewModel : ViewModel() {

    private val topicsList = listOf<TopicItem>(
        TopicItem(
            "https://images.prismic.io/buzzsprout/06fd34b4c2d73e8e104196b1227469811a05f768_royalty-free-music2x.png?auto=compress,format",
            "Music"
        ),
        TopicItem(
            "https://64.media.tumblr.com/6a14a8e3c37d1c95967f59b23f707747/tumblr_nsgqgiSSXw1t7cmmpo1_1280.png",
            "Movies"
        ),
        TopicItem(
            "https://cdn0.iconfinder.com/data/icons/learning-icons-rounded/110/Books-512.png",
            "Books"
        ),
        TopicItem(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSem64pL6ID99k8l2ObXH0W1L56Z9J6lgLO-Q&usqp=CAU",
            "Travel"
        ),
        TopicItem(
            "https://lh6.ggpht.com/HlgucZ0ylJAfZgusynnUwxNIgIp5htNhShF559x3dRXiuy_UdP3UQVLYW6c=s1200",
            "Art"
        ),
        TopicItem(
            "https://cdn2.iconfinder.com/data/icons/round-set-vol-2/120/gamepad-512.png",
            "Games"
        )
    )

    private val _topics = MutableLiveData<List<TopicItem>>()
    val topics: LiveData<List<TopicItem>> = _topics

    init {
        _topics.value = topicsList
    }

}
