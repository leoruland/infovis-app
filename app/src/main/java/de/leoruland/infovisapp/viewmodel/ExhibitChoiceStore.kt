package de.leoruland.infovisapp.viewmodel

import de.leoruland.infovisapp.model.Exhibit

object ExhibitChoiceStore {
    private var exhibit: Exhibit? = null

    fun getExhibit() = exhibit

    fun setExhibit(exhibit: Exhibit) {
        this.exhibit = exhibit
    }

    fun clearExhibit() {
        exhibit = null
    }
}