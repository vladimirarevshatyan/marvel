package com.marvel.list_views.expandable_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.marvel.R


class ExpandableAdapter : BaseExpandableListAdapter() {

    private var data: HashMap<String, ArrayList<String>> = HashMap()
    private var group = ArrayList<String>().apply {
        add("Stories")
        add("Events")
        add("Comics")
        add("Series")
    }

    override fun getGroupCount(): Int {
        return group.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data[group[groupPosition]]?.size ?: 0
    }


    override fun getGroup(groupPosition: Int): Any {
        return group[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return data[group[groupPosition]]?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val headerTitle = getGroup(groupPosition) as String
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(
                R.layout.group_list_header,
                parent, false
            )
        }

        val lblListHeader = view?.findViewById(R.id.header_title) as TextView
        lblListHeader.text = headerTitle
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childText = getChild(groupPosition, childPosition) as String

        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(
                R.layout.group_list_children,
                parent, false
            )
        }

        val txtListChild = view?.findViewById(R.id.children_text) as TextView
        txtListChild.text = childText
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    fun setData(data: HashMap<String, ArrayList<String>>) {
        this.data = data
        notifyDataSetChanged()
    }
}