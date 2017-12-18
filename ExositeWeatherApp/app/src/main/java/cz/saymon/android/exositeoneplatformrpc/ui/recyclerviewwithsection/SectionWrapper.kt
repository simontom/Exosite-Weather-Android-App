package cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection

/**
 * Wrapper used to link metadata with a list item.
 *
 * Source: https://github.com/IntruderShanky/Sectioned-RecyclerView
 *
 * @param <S> Section list item
 * @param <C> Child list item
</C></S> */
class SectionWrapper<S, C> where S : Section<C> {

    var isSection: Boolean = false
        private set

    var section: S? = null
    var child: C? = null

    var sectionPosition: Int = 0
        private set

    private var childPosition: Int = 0
        private set

    /**
     * Constructor to wrap a section object of type [S].
     *
     * @param section The parent object to wrap
     */
    constructor(section: S, sectionPosition: Int) {
        this.isSection = true
        this.section = section
        this.sectionPosition = sectionPosition
        this.childPosition = -1
    }

    /**
     * Constructor to wrap a child object of type [C].
     *
     * @param child The child object to wrap
     */
    constructor(child: C, sectionPosition: Int, childPosition: Int) {
        this.child = child
        this.sectionPosition = sectionPosition
        this.isSection = false
        this.childPosition = childPosition
    }

    fun getChildPosition(): Int {
        if (childPosition == -1) {
            throw IllegalAccessError("This is not child")
        }
        return childPosition
    }
}
