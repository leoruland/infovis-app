package de.leoruland.infovisapp

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

object ChoiceTopicScreen : Screen<ChoiceTopicScreen>() {
    val title = KTextView { withId(R.id.header) }
    val nextButton = KButton { withId(R.id.fab_next) }
    val numberInputButton = KButton { withId(R.id.fab_numberinput) }
    val topicItems = KRecyclerView(
        builder = { withId(R.id.topicRecyclerView) },
        itemTypeBuilder = { itemType(::TopicItem) }
    )

    class TopicItem(parent: Matcher<View>) : KRecyclerItem<TopicItem>(parent) {
        val title = KTextView(parent) { withId(R.id.topic_item_title) }
    }
}