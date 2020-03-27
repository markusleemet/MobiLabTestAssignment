package cs.ut.ee.mobilabtestassignment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

private const val LIST_INDEX = "shoppingListIndex"

class ListFragment :OnItemClickListener, OnItemLongClickListener, FragmentEdit.OnDeletePressedListener, Fragment() {
    private var shoppingListIndex: Int = -1
    private var model: ShoppingListViewModel? = null
    private var recyclerViewAdapter: ItemsAdapter? = null
    private var callback: OnFragmentClosedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shoppingListIndex = it.getInt(LIST_INDEX)
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
                    outlinedTextFieldInsertItem.editText?.setText("")
                    val item = ItemEntity(itemName, false)
                    model?.addItemToShoppingList(item, shoppingListIndex)
                    recyclerViewAdapter?.notifyDataSetChanged()
                    Log.i("ShoppingList", "return action took place(item: $item)")
                    Log.i("ShoppingList", "list new content: ${model!!.shoppingLists[shoppingListIndex]}")
                }
                return false
            }
        })

        //set up recyclerView to show items in sopping list
        recyclerViewAdapter = ItemsAdapter(model!!.shoppingLists[shoppingListIndex], this, this)
        view.recyler_view_items_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recyclerViewAdapter
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
        callback?.onFragmentClosed()
    }

    override fun onItemClick(position: Int) {
        Log.i("ShoppingList", "Clicked view position: $position")
        Log.i("ShoppingList", "Clicked item: ${model!!.shoppingLists[shoppingListIndex][position]}")
        model?.changeItemStatus(shoppingListIndex, position)
        recyclerViewAdapter?.notifyDataSetChanged()
    }

    override fun onItemLongClick(position: Int) {
        val editFragment = FragmentEdit.newInstance(position, shoppingListIndex, "Delete item?")
        editFragment.setOnDeletePressedListener(this)
        val transaction = activity!!.supportFragmentManager.beginTransaction().add(R.id.constraint_layout_items_list, editFragment)
        transaction.addToBackStack("editFragment")
        transaction.commit()
    }

    fun setOnFragmentClosedListner(callback: OnFragmentClosedListener) {
        this.callback = callback
    }

    interface OnFragmentClosedListener{
        fun onFragmentClosed()
    }

    override fun onDeletePressed(shoppingListIndex: Int, itemIndex: Int) {
        model!!.shoppingLists[shoppingListIndex].removeAt(itemIndex)
        recyclerViewAdapter?.notifyDataSetChanged()
    }
}
