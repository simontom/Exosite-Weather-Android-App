package cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * RecyclerView.Adapter implementation that adds the ability to manage Sections and their child.
 *
 * Source: https://github.com/IntruderShanky/Sectioned-RecyclerView
 */
abstract class SectionRecyclerViewAdapter<
        in TSection, TChild, TSectionViewHolder, TChildViewHolder>
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
        where TSection : Section<TChild>, TSectionViewHolder : RecyclerView.ViewHolder,
              TChildViewHolder : RecyclerView.ViewHolder {

    private val SECTION_VIEW_TYPE = 1
    private val CHILD_VIEW_TYPE = 2

    /**
     * A [List] of all sections and their children, in order.
     * Changes to this list should be made through the add/remove methods
     * available in [SectionRecyclerViewAdapter].
     */
    private var flattenedItemList: List<SectionWrapper<TSection, TChild>>? = null

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
    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, flatPosition: Int) {
        val sectionWrapper = flattenedItemList!![flatPosition]
        if (sectionWrapper.isSection) {
            val sectionViewHolder = holder as TSectionViewHolder
            onBindSectionViewHolder(sectionViewHolder, sectionWrapper.sectionPosition,
                    sectionWrapper.section!!)
        } else {
            val childViewHolder = holder as TChildViewHolder
            onBindChildViewHolder(childViewHolder, sectionWrapper.sectionPosition,
                    sectionWrapper.getChildPosition(), sectionWrapper.child!!)
        }
    }

    /**
     * Callback called from [.onCreateViewHolder] when
     * the list item created is a section.
     *
     * @param sectionViewGroup The [ViewGroup] in the list for which a [TSectionViewHolder] is being created
     * @return A `TSectionViewHolder` corresponding to the parent with the `ViewGroup` parentViewGroup
     */
    abstract fun onCreateSectionViewHolder(sectionViewGroup: ViewGroup, viewType: Int): TSectionViewHolder

    /**
     * Callback called from [.onCreateViewHolder] when
     * the list item created is a child.
     *
     * @param childViewGroup The [ViewGroup] in the list for which a [TChildViewHolder] is being created
     * @return A `TChildViewHolder` corresponding to the child with the `ViewGroup` childViewGroup
     */
    abstract fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): TChildViewHolder

    /**
     * Callback called from onBindViewHolder(RecyclerView.ViewHolder, int)
     * when the list item bound to is a section.
     * Bind data to the [TSectionViewHolder] here.
     *
     * @param sectionViewHolder The `TSectionViewHolder` to bind data to
     * @param sectionPosition   The position of the parent to bind
     * @param section           The parent which holds the data to be bound to the `TSectionViewHolder`
     */
    abstract fun onBindSectionViewHolder(sectionViewHolder: TSectionViewHolder,
                                         sectionPosition: Int, section: TSection)

    /**
     * Callback called from onBindViewHolder(RecyclerView.ViewHolder, int)
     * when the list item bound to is a child.
     * Bind data to the [TChildViewHolder] here.
     *
     * @param childViewHolder The `TChildViewHolder` to bind data to
     * @param sectionPosition The position of the parent that contains the child to bind
     * @param childPosition   The position of the child to bind
     * @param child           The child which holds that data to be bound to the `TChildViewHolder`
     */
    abstract fun onBindChildViewHolder(childViewHolder: TChildViewHolder, sectionPosition: Int, childPosition: Int, child: TChild)

    private fun generateSectionWrapper(flatItemList: MutableList<SectionWrapper<TSection, TChild>>,
                                       section: TSection, sectionPosition: Int) {
        val sectionWrapper = SectionWrapper(section, sectionPosition)
        flatItemList.add(sectionWrapper)
        val childList = section.childItems
        for (i in childList.indices) {
            val childWrapper = SectionWrapper<TSection, TChild>(childList[i], sectionPosition, i)
            flatItemList.add(childWrapper)
        }
    }

    /**
     * Generates a full list of all sections and their children, in order.
     *
     * @param sectionItemList A list of the sections from the [SectionRecyclerViewAdapter]
     * @return A list of all sections and their children
     */
    private fun generateFlatItemList(sectionItemList: List<TSection>):
            List<SectionWrapper<TSection, TChild>> {
        val flatItemList = ArrayList<SectionWrapper<TSection, TChild>>()
        for (i in sectionItemList.indices) {
            val section = sectionItemList[i]
            generateSectionWrapper(flatItemList, section, i)
        }
        return flatItemList
    }

    override fun getItemCount(): Int {
        return flattenedItemList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val section = flattenedItemList!![position]
        return if (section.isSection) SECTION_VIEW_TYPE else CHILD_VIEW_TYPE
    }

    private fun isSectionViewType(viewType: Int): Boolean {
        return viewType == SECTION_VIEW_TYPE
    }

    fun notifyDataChanged(sectionItemList: List<TSection>) {
        flattenedItemList = generateFlatItemList(sectionItemList)
        notifyDataSetChanged()
    }

}
