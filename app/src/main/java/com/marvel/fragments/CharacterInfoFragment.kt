package com.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.marvel.MainActivity
import com.marvel.R
import com.marvel.databinding.FragmentCharacterInfoBinding
import com.marvel.fragments.communicators.CharacterInfoCommunicator
import com.marvel.fragments.communicators.Communicator
import com.marvel.list_views.expandable_list.ExpandableAdapter
import com.marvel.retrofit.api.response.character.Character

class CharacterInfoFragment : Fragment(), Communicator<CharacterInfoCommunicator> {

    private var characterInfoCommunicator: CharacterInfoCommunicator? = null
    private lateinit var adapter: ExpandableAdapter

    private lateinit var views: FragmentCharacterInfoBinding

    private var character: Character? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        return views.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        initExpandableView()
        val characterId = arguments?.getLong(CHARACTER_KEY)
        characterId?.let {
            characterInfoCommunicator?.onGetCharacter(it)
        }
    }

    private fun loadCharacter(character: Character?) {
        character?.let {
            val url = "${it.thumbnail.path}.${it.thumbnail.extension}"
            Glide.with(this)
                .load(url)
                .centerInside()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_error)
                .into(views.characterAvatar)
            views.characterInfoName.text = it.name
        }
    }

    private fun initExpandableView() {
        adapter = ExpandableAdapter()
        views.comicsElv.setAdapter(adapter)
    }

    private fun askForGroupedCharacters() {
        characterInfoCommunicator?.groupCharacters(character)
    }

    private fun subscribeToLiveData() {
        (activity as MainActivity).mainVM.groupedCharactersLiveData.observe(this, {
            adapter.setData(it)
        })
        (activity as MainActivity).mainVM.characterLiveData.observe(this, {
            character = it
            loadCharacter(character)
            askForGroupedCharacters()
        })
    }

    override fun setCommunicator(communicator: CharacterInfoCommunicator) {
        this.characterInfoCommunicator = communicator
    }

    companion object {
        fun newInstance(characterId: Long): CharacterInfoFragment {
            val args = Bundle()
            args.putLong(CHARACTER_KEY, characterId)
            val fragment = CharacterInfoFragment()
            fragment.arguments = args
            return fragment
        }

        private const val CHARACTER_KEY = "character_id"
    }
}