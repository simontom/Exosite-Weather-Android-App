package cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection

/**
 * Interface for implementing required methods in a section.
 */
interface Section<C> {
    /**
     * Getter for the list of this parent's child items.
     * If list is empty, the parent has no children.
     *
     * @return A [List] of the children of this [Section]
     */
    val childItems: List<C>
}
