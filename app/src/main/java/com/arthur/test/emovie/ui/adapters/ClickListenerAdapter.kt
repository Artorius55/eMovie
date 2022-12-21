package com.arthur.test.emovie.ui.adapters

/**
 * Interface for propagate an Click event from Adapter to View
 *
 * @param T the type of items in the adapter constructor
 */
interface ClickListenerAdapter<T> {

    /**
     * Function trigger when a click is received
     *
     * @param position of the item clicked
     * @param item Model of the item clicked
     */
    fun onItemClicked(position: Int, item: T)
}