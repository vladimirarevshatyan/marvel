package com.marvel.list_views.recycler_view.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.marvel.databinding.FooterViewBinding
import com.marvel.gone
import com.marvel.visible

class FooterView(private val footerViewBinding: FooterViewBinding) :
    RecyclerView.ViewHolder(footerViewBinding.root) {

    fun bind(visible: Boolean) {
        if (visible) {
            footerViewBinding.footerParent.visible()
        } else {
            footerViewBinding.footerParent.gone()
        }
    }
}