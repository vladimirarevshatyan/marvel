package com.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvel.MainActivity
import com.marvel.R
import com.marvel.databinding.FragmentCharactersListBinding
import com.marvel.fragments.communicators.CharacterFragmentCommunicator
import com.marvel.fragments.communicators.Communicator
import com.marvel.list_views.recycler_view.adapter.CharacterListAdapter
import com.marvel.list_views.recycler_view.listeners.OnItemClicked
import com.marvel.retrofit.api.response.character.Character
import com.marvel.view_models.MainVM


class CharactersListFragment : Fragment(), OnItemClicked<Character>,
    Communicator<CharacterFragmentCommunicator> {

    lateinit var mainVM: MainVM

    private lateinit var views: FragmentCharactersListBinding

    private lateinit var characterListAdapter: CharacterListAdapter

    private var characterFragmentCommunicator: CharacterFragmentCommunicator? = null

    private var dataAvailable = true

    private var dataProcessing = false

    override fun onItemClicked(data: Character) {
        characterFragmentCommunicator?.onCharacterClicked(data)
    }

    override fun setCommunicator(communicator: CharacterFragmentCommunicator) {
        this.characterFragmentCommunicator = communicator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = FragmentCharactersListBinding.inflate(layoutInflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainVM = (activity as MainActivity).mainVM
        startRefreshing()
        setRefreshListener()
        initAdapterAndRV()
        subscribeToLiveData()
    }

    private fun startRefreshing() {
        views.charactersStr.post {
            views.charactersStr.isRefreshing = true
        }
    }

    private fun setRefreshListener() {
        views.charactersStr.setOnRefreshListener {
            characterFragmentCommunicator?.onRefreshData()
        }
    }

    private fun initAdapterAndRV() {
        val layoutManager = LinearLayoutManager(context)
        views.characterRv.layoutManager = layoutManager
        characterListAdapter = CharacterListAdapter()
        views.characterRv.adapter = characterListAdapter
        characterListAdapter.setListener(this)
        setRVScrollListener()
    }

    private fun setRVScrollListener() {
        views.characterRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (dataAvailable) {
                        if (!dataProcessing) {
                            dataProcessing = true
                            characterListAdapter.setFooterVisible(true)
                            characterFragmentCommunicator?.onNewDataRequest()
                        }
                    }
                }
            }
        })
    }

    private fun subscribeToLiveData() {
        mainVM.charactersListLiveData.observe(this, {
            if (it != null) {
                characterListAdapter.setCharacters(it, !views.charactersStr.isRefreshing)
            } else {
                Toast.makeText(context, getString(R.string.unableToProceed), Toast.LENGTH_SHORT)
                    .show()
            }
            views.charactersStr.isRefreshing = false
            characterListAdapter.setFooterVisible(false)
            dataProcessing = false
        })
        mainVM.dataAvailability.observe(this, {
            dataAvailable = it
            characterListAdapter.setFooterVisible(false)
            dataProcessing = false
        })
    }
}