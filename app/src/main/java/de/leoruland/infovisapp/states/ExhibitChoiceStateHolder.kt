package de.leoruland.infovisapp.states

import de.leoruland.infovisapp.model.Exhibit

object ExhibitChoiceStateHolder {
    private var exhibit: Exhibit? = null

    fun getExhibit() = exhibit

    fun setExhibit(exhibit: Exhibit) {
        this.exhibit = exhibit
    }
}