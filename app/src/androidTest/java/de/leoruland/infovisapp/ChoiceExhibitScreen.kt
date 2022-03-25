package de.leoruland.infovisapp;

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

object ChoiceExhibitScreen : Screen<ChoiceExhibitScreen>() {
    val title = KTextView { withId(R.id.header) }
    val backButton = KButton { withId(R.id.fab_back) }
    val numberInputButton = KButton { withId(R.id.fab_numberinput) }
    val exhibitItems = KRecyclerView(
        builder = { withId(R.id.exhibitRecyclerView) },
        itemTypeBuilder = { itemType(::ExhibitItem) }
    )

    class ExhibitItem(parent: Matcher<View>) : KRecyclerItem<ExhibitItem>(parent) {
        val title = KTextView(parent) { withId(R.id.exhibitTitle) }
    }
}