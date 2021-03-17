package com.marvel.list_views.recycler_view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvel.databinding.FooterViewBinding
import com.marvel.databinding.SingleCharacterBinding
import com.marvel.list_views.recycler_view.listeners.OnItemClicked
import com.marvel.list_views.recycler_view.view_holders.CharacterListVH
import com.marvel.list_views.recycler_view.view_holders.FooterView
import com.marvel.retrofit.api.response.character.Character

class CharacterListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var visible: Boolean = false
    private val FOOTER_VIEW = 1
    private var characters = ArrayList<Character>()
    private lateinit var context: Context
    private var onItemClicked: OnItemClicked<Character>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == FOOTER_VIEW) {
            val views = FooterViewBinding.inflate(inflater, parent, false)
            FooterView(views)
        } else {
            val views = SingleCharacterBinding.inflate(inflater, parent, false)
            CharacterListVH(views)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterView) {
            holder.bind(visible)
        } else {
            (holder as CharacterListVH).bind(characters[position], context, onItemClicked)
        }
    }

    override fun getItemCount(): Int {
        return characters.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == characters.size) {
            return FOOTER_VIEW
        }
        return super.getItemViewType(position)
    }

    fun setCharacters(characters: ArrayList<Character>, shouldAppend: Boolean) {
        if (characters.isNotEmpty()) {
            if (shouldAppend) {
                val lastIndex = this.characters.size
                this.characters.addAll(lastIndex, characters)
            } else {
                this.characters.clear()
                this.characters.addAll(characters)
            }
            notifyDataSetChanged()
        }
    }

    fun setListener(onItemClicked: OnItemClicked<Character>) {
        this.onItemClicked = onItemClicked
    }

    fun setFooterVisible(visible: Boolean) {
        this.visible = visible
        notifyDataSetChanged()
    }
}