package cs.ut.ee.mobilabtestassignment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.view.*

private const val LIST_INDEX = ""

class ListFragment : Fragment() {
    var listIndex: Int = -1
    var model: ShoppingListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listIndex = it.getInt(LIST_INDEX)
        }
        model = ViewModelProvider(activity!!).get(ShoppingListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.floating_action_button?.hide()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //listener listens submit button and then adds item to list
        view.editTextAddItem.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val itemName = outlinedTextFieldInsertItem.editText?.text.toString()
                    val item = ItemEntity(itemName, false)
                    Log.i("ShoppingList", "return action took place(item: $item)")
                    model?.addItemToShoppingList(item, listIndex!!)
                    return true
                }
                return false
            }
        })


        //set up recyclerView to show items in sopping list
        view.recyler_view_items_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ItemsAdapter(model!!.shoppingLists[listIndex])
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(listIndex: Int) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_INDEX, listIndex)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.floating_action_button?.show()
    }
}
