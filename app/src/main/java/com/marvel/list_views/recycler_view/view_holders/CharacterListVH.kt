package com.marvel.list_views.recycler_view.view_holders

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvel.R
import com.marvel.databinding.SingleCharacterBinding
import com.marvel.list_views.recycler_view.listeners.OnItemClicked
import com.marvel.retrofit.api.response.character.Character

class CharacterListVH(private val binding: SingleCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character, context: Context, onItemClicked: OnItemClicked<Character>?) {
        with(binding) {
            loadAvatar(binding.characterAvatar, character, context)
            characterName.text = character.name
            characterDescription.text = character.description.run {
                if (isEmpty()) {
                    context.getString(R.string.no_description_available)
                } else {
                    this
                }
            }
            characterListRoot.setOnClickListener {
                onItemClicked?.onItemClicked(character)
            }
        }
    }

    private fun loadAvatar(
        characterAvatar: ImageView,
        character: Character,
        context: Context
    ) {
        val url = "${character.thumbnail.path}.${character.thumbnail.extension}"
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_image_loading)
            .error(R.drawable.ic_image_error)
            .into(characterAvatar)
    }
}