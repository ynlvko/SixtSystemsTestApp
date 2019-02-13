package com.sixtsystems.testapp.items.edit_item

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.sixtsystems.testapp.R
import com.sixtsystems.testapp.ext.supportActionBar
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_edit_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditItemFragment : Fragment() {
    private val viewModel: EditItemViewModel by viewModel { parametersOf(arguments?.getInt(ArgItemId)) }

    private val avatarsViews: List<ImageView> by lazy {
        arrayListOf(imageView1, imageView2, imageView3, imageView4)
    }
    private var selectedAvatar = 0

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable.add(
            Observable.combineLatest(
                etFirstName.afterTextChangeEvents(),
                etLastName.afterTextChangeEvents(),
                BiFunction<TextEvent, TextEvent, NameEvent> { e1, e2 ->
                    NameEvent(e1.editable.toString(), e2.editable.toString())
                }
            ).subscribe(::processUserName))

        viewModel.viewState().observe(this, Observer { viewState ->
            etFirstName.setText(viewState.firstName)
            etLastName.setText(viewState.lastName)
            setAvatar(viewState.itemAvatars, viewState.selectedAvatar)
        })
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setAvatar(itemAvatars: List<Int>, selectedAvatar: Int) {
        if (selectedAvatar == 0) {
            this.selectedAvatar = itemAvatars.first()
        } else {
            this.selectedAvatar = selectedAvatar
        }
        avatarsViews.forEachIndexed { position, imageView ->
            imageView.setBackgroundResource(itemAvatars[position])
            imageView.tag = itemAvatars[position]
            imageView.setOnClickListener(::onAvatarClick)
        }
        setSelectedAvatarImageView()
    }

    private fun setSelectedAvatarImageView() {
        avatarsViews.forEach { imageView ->
            if (imageView.tag == selectedAvatar) {
                imageView.alpha = 1f
            } else {
                imageView.alpha = 0.2f
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_save) {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            if (hasErrors(firstName, lastName)) {
                return true
            }
            viewModel.save(firstName, lastName, selectedAvatar)
            activity?.supportFragmentManager?.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hasErrors(firstName: String, lastName: String): Boolean {
        var error = false
        etFirstName.error = if (firstName.isBlank()) {
            error = true
            getString(R.string.error_empty_field)
        } else {
            null
        }
        etLastName.error = if (lastName.isBlank()) {
            error = true
            getString(R.string.error_empty_field)
        } else {
            null
        }
        return error
    }

    private fun onAvatarClick(v: View) {
        selectedAvatar = v.tag as Int
        setSelectedAvatarImageView()
    }

    private fun processUserName(nameEvent: NameEvent) {
        val title = "${extractTitle(nameEvent.first)} ${extractTitle(nameEvent.second)}"
        supportActionBar?.title = title
    }

    private fun extractTitle(name: String): String {
        return if (name.isBlank()) {
            ""
        } else {
            "${name[0]}."
        }
    }

    companion object {
        private const val ArgItemId = "ArgItemId"

        fun newInstance(itemId: Int? = null): EditItemFragment {
            val fragment = EditItemFragment()
            if (itemId != null) {
                fragment.arguments = Bundle().apply {
                    putInt(ArgItemId, itemId)
                }
            }
            return fragment
        }
    }
}

typealias TextEvent = TextViewAfterTextChangeEvent
typealias NameEvent = Pair<String, String>
