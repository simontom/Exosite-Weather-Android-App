package cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * RecyclerView.Adapter implementation that
 * adds the ability to manage Sections and their child.
 *
 * Changes should be notified through:
 * [.insertNewSection]
 * [.insertNewSection]
 * [.removeSection]
 * [.insertNewChild]
 * [.insertNewChild]
 * [.removeChild]
 * [.notifyDataChanged]
 * methods and not the notify methods of RecyclerView.Adapter.
 */
abstract class SectionRecyclerViewAdapter<S : Section<C>, C, SVH : RecyclerView.ViewHolder, CVH : RecyclerView.ViewHolder>
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Default ViewType for section rows
     */
    private val SECTION_VIEW_TYPE = 1

    /**
     * Default ViewType for child rows
     */
    private val CHILD_VIEW_TYPE = 2

    /**
     * A [List] of all sections and their children, in order.
     * Changes to this list should be made through the add/remove methods
     * available in [SectionRecyclerViewAdapter].
     */
    private var flatItemList: List<SectionWrapper<S, C>>? = null

/*    private val sectionItemList: MutableList<S> = ArrayList<S>()
    init {
        this.flatItemList = generateFlatItemList(sectionItemList)
    }*/

    /**
     * Implementation of Adapter.onCreateViewHolder(ViewGroup, int)
     * that determines if the list item is a section or a child and calls through
     * to the appropriate implementation of either [.onCreateSectionViewHolder]
     * or [.onCreateChildViewHolder].
     *
     * @param viewGroup The [ViewGroup] into which the new [android.view.View]
     * will be added after it is bound to an adapter position.
     * @param viewType  The view type of the new `android.view.View`.
     * @return A new RecyclerView.ViewHolder
     * that holds a `android.view.View` of the given view type.
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isSectionViewType(viewType)) {
            onCreateSectionViewHolder(viewGroup, viewType)
        } else {
            onCreateChildViewHolder(viewGroup, viewType)
        }
    }

    /**
     * Implementation of Adapter.onBindViewHolder(RecyclerView.ViewHolder, int)
     * that determines if the list item is a section or a child and calls through
     * to the appropriate implementation of either
     * [.onBindSectionViewHolder] or
     * [.onBindChildViewHolder].
     *
     * @param holder       The RecyclerView.ViewHolder to bind data to
     * @param flatPosition The index in the merged list of children and parents at which to bind
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, flatPosition: Int) {
        if (flatPosition > flatItemList!!.size) {
            throw IllegalStateException("Trying to bind item out of bounds, size " + flatItemList!!.size
                    + " flatPosition " + flatPosition + ". Was the data changed without a call to notify...()?")
        }
        val sectionWrapper = flatItemList!![flatPosition]
        if (sectionWrapper.isSection) {
            val sectionViewHolder = holder as SVH
            onBindSectionViewHolder(sectionViewHolder, sectionWrapper.sectionPosition, sectionWrapper.section!!)
        } else {
            val childViewHolder = holder as CVH
            onBindChildViewHolder(childViewHolder, sectionWrapper.sectionPosition,
                    sectionWrapper.getChildPosition(), sectionWrapper.child!!)
        }
    }

    /**
     * Callback called from [.onCreateViewHolder] when
     * the list item created is a section.
     *
     * @param sectionViewGroup The [ViewGroup] in the list for which a [SVH] is being
     * created
     * @return A `SVH` corresponding to the parent with the `ViewGroup` parentViewGroup
     */
    abstract fun onCreateSectionViewHolder(sectionViewGroup: ViewGroup, viewType: Int): SVH

    /**
     * Callback called from [.onCreateViewHolder] when
     * the list item created is a child.
     *
     * @param childViewGroup The [ViewGroup] in the list for which a [CVH]
     * is being created
     * @return A `CVH` corresponding to the child with the `ViewGroup` childViewGroup
     */
    abstract fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): CVH

    /**
     * Callback called from onBindViewHolder(RecyclerView.ViewHolder, int)
     * when the list item bound to is a section.
     *
     *
     * Bind data to the [SVH] here.
     *
     * @param sectionViewHolder The `SVH` to bind data to
     * @param sectionPosition   The position of the parent to bind
     * @param section           The parent which holds the data to be bound to the `SVH`
     */
    abstract fun onBindSectionViewHolder(sectionViewHolder: SVH, sectionPosition: Int, section: S)

    /**
     * Callback called from onBindViewHolder(RecyclerView.ViewHolder, int)
     * when the list item bound to is a child.
     *
     *
     * Bind data to the [CVH] here.
     *
     * @param childViewHolder The `CVH` to bind data to
     * @param sectionPosition The position of the parent that contains the child to bind
     * @param childPosition   The position of the child to bind
     * @param child           The child which holds that data to be bound to the `CVH`
     */
    abstract fun onBindChildViewHolder(childViewHolder: CVH, sectionPosition: Int, childPosition: Int, child: C)

    private fun generateSectionWrapper(flatItemList: MutableList<SectionWrapper<S, C>>, section: S, sectionPosition: Int) {
        val sectionWrapper = SectionWrapper(section, sectionPosition)
        flatItemList.add(sectionWrapper)
        val childList = section.childItems
        for (i in childList.indices) {
            val childWrapper = SectionWrapper<S, C>(childList[i], sectionPosition, i)
            flatItemList.add(childWrapper)
        }
    }

    /**
     * Generates a full list of all sections and their children, in order.
     *
     * @param sectionItemList A list of the sections from
     * the [SectionRecyclerViewAdapter]
     * @return A list of all sections and their children
     */
    private fun generateFlatItemList(sectionItemList: List<S>): List<SectionWrapper<S, C>> {
        val flatItemList = ArrayList<SectionWrapper<S, C>>()
        for (i in sectionItemList.indices) {
            val section = sectionItemList[i]
            generateSectionWrapper(flatItemList, section, i)
        }
        return flatItemList
    }

    override fun getItemCount(): Int {
        return flatItemList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (flatItemList!![position].isSection) SECTION_VIEW_TYPE else CHILD_VIEW_TYPE
    }

    fun isSectionViewType(viewType: Int): Boolean {
        return viewType == SECTION_VIEW_TYPE
    }

    /*fun insertNewSection(section: S, sectionPosition: Int = sectionItemList.size) {
        if (sectionPosition > sectionItemList.size || sectionPosition < 0)
            throw IndexOutOfBoundsException("sectionPosition =  " + sectionPosition + " , Size is " + sectionItemList.size)
        notifyDataChanged(sectionItemList)
    }

    fun removeSection(sectionPosition: Int) {
        if (sectionPosition > sectionItemList.size - 1 || sectionPosition < 0)
            throw IndexOutOfBoundsException("sectionPosition =  " + sectionPosition + " , Size is " + sectionItemList.size)
        sectionItemList.removeAt(sectionPosition)
        notifyDataChanged(sectionItemList)
    }

    fun insertNewChild(child: C, sectionPosition: Int) {
        if (sectionPosition > sectionItemList.size - 1 || sectionPosition < 0)
            throw IndexOutOfBoundsException("Invalid sectionPosition =  " + sectionPosition + " , Size is " + sectionItemList.size)
        insertNewChild(child, sectionPosition, sectionItemList[sectionPosition].childItems.size)
    }

    fun insertNewChild(child: C, sectionPosition: Int, childPosition: Int) {
        if (sectionPosition > sectionItemList.size - 1 || sectionPosition < 0)
            throw IndexOutOfBoundsException("Invalid sectionPosition =  " + sectionPosition + " , Size is " + sectionItemList.size)
        if (childPosition > sectionItemList[sectionPosition].childItems.size || childPosition < 0)
            throw IndexOutOfBoundsException("Invalid childPosition =  " + childPosition + " , Size is " + sectionItemList[sectionPosition].childItems.size)
        sectionItemList[sectionPosition].childItems.add(childPosition, child)
        notifyDataChanged(sectionItemList)
    }

    fun removeChild(sectionPosition: Int, childPosition: Int) {
        if (sectionPosition > sectionItemList.size - 1 || sectionPosition < 0)
            throw IndexOutOfBoundsException("Invalid sectionPosition =  " + sectionPosition + " , Size is " + sectionItemList.size)
        if (childPosition > sectionItemList[sectionPosition].childItems.size - 1 || childPosition < 0)
            throw IndexOutOfBoundsException("Invalid childPosition =  " + childPosition + " , Size is " + sectionItemList[sectionPosition].childItems.size)
        sectionItemList[sectionPosition].childItems.removeAt(childPosition)
        notifyDataChanged(sectionItemList)
    }*/

    fun notifyDataChanged(sectionItemList: List<S>) {
        flatItemList = ArrayList()
        flatItemList = generateFlatItemList(sectionItemList)
        notifyDataSetChanged()
    }

}
