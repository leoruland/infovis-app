package de.leoruland.infovisapp

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

object DetailExhibitScreen : Screen<DetailExhibitScreen>() {
    val title = KTextView { withId(R.id.exhibit_title) }
    val repositoryName = KTextView { withId(R.id.exhibit_repository_label) }
    val map = KView { withId(R.id.map) }
    val description = KTextView { withId(R.id.exhibit_description) }
    val backButton = KButton { withId(R.id.fab_back) }
    val numberInputButton = KButton { withId(R.id.fab_numberinput) }
    val openBrowser = KButton { withId(R.id.fab_open) }
    val topicBubbles = KRecyclerView(
        builder = { withId(R.id.topicBubblesRecyclerView) },
        itemTypeBuilder = { itemType(::TopicBubbleItem) }
    )

    class TopicBubbleItem(parent: Matcher<View>) : KRecyclerItem<TopicBubbleItem>(parent) {
        val title = KTextView(parent) { withId(R.id.topic_item_title) }
    }
}