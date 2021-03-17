package com.marvel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marvel.databinding.ActivityMainBinding
import com.marvel.fragments.CharacterInfoFragment
import com.marvel.fragments.CharactersListFragment
import com.marvel.fragments.communicators.CharacterFragmentCommunicator
import com.marvel.fragments.communicators.CharacterInfoCommunicator
import com.marvel.retrofit.api.response.character.Character
import com.marvel.utils.Injector
import com.marvel.utils.Logger
import com.marvel.view_models.MainVM
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injector,
    CharacterFragmentCommunicator,
    CharacterInfoCommunicator {


    private lateinit var views: ActivityMainBinding

    private var currentOffset = 1
    private var currentLimit = 10

    @Inject
    lateinit var mainVM: MainVM

    @Inject
    lateinit var logger: Logger

    private var disposable: Disposable? = null

    private lateinit var characterFragment: CharactersListFragment

    private lateinit var characterInfoFragment: CharacterInfoFragment

    override fun inject() {
        (application as MainApp).appComponent.inject(this)
    }

    override fun onRefreshData() {
        getData()
    }

    override fun onCharacterClicked(character: Character) {
        createCharacterInfoFragment(character)
    }

    override fun onNewDataRequest() {
        currentOffset += 1
        getData()
    }

    private fun createCharacterInfoFragment(character: Character) {
        characterInfoFragment = CharacterInfoFragment.newInstance(character.id)
        characterInfoFragment.setCommunicator(this)
        showFragment(characterInfoFragment)
    }

    override fun groupCharacters(character: Character?) {
        mainVM.groupCharacters(character)
    }

    override fun onGetCharacter(characterId: Long) {
        mainVM.getCharacter(characterId)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        initViewBinding()
        showCharactersFragment()
        setCharactersFragmentCommunicator()
        getData()
    }


    private fun setCharactersFragmentCommunicator() {
        characterFragment.setCommunicator(this)
    }

    private fun showCharactersFragment() {
        characterFragment = CharactersListFragment()
        showFragment(characterFragment)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also {
            it.add(R.id.fragments_container, fragment).addToBackStack(fragment::class.java.name)
            it.commit()
        }
    }

    private fun initViewBinding() {
        views = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    private fun getData() {
        disposable = mainVM.getCharacters(currentLimit, currentOffset)
            .subscribe({
                checkCurrentOffsetAndPost(it)
            }, {
                mainVM.postCharacters(null)
                logger.logInDebug(it)
            }, {})
    }

    private fun checkCurrentOffsetAndPost(result: ArrayList<Character>) {
        if (result.isEmpty() && currentOffset <= 1) {
            currentOffset += 1
            getData()
        } else if (result.isEmpty() && currentOffset > 1) {
            mainVM.postNoNewDataAvailable()
        } else {
            mainVM.saveCharacters(result)
            mainVM.postCharacters(result)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}