package de.leoruland.infovisapp

import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView

object DirectNumberInputScreen : Screen<DirectNumberInputScreen>() {
    val title = KTextView { withId(R.id.directnumberinput_title) }
    val errorText = KTextView { withId(R.id.error_text) }
    val inputField = KTextInputLayout { withId(R.id.number_input) }
    val searchButton = KButton { withId(R.id.search_button) }
    val closeButton = KButton { withId(R.id.fab_close) }
}