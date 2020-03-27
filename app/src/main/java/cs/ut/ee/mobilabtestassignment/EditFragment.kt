package cs.ut.ee.mobilabtestassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_edit.view.*

private const val ITEM_INDEX = "itemIndex"
private const val SHOPPING_LIST_INDEX = "shoppingListIndex"
private const val FRAGMENT_TEXT = "fragmentText"

class FragmentEdit : Fragment() {
    private var itemIndex: Int = -1
    private var shoppingListIndex: Int = -1
    private var fragmentText: String? = null
    private var callback: OnDeletePressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemIndex = it.getInt(ITEM_INDEX)
            shoppingListIndex = it.getInt(SHOPPING_LIST_INDEX)
            fragmentText = it.getString(FRAGMENT_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        view.text_view_edit_fragment.text = fragmentText
        view.button_cancel.setOnClickListener {
            buttonCancel()
        }
        view.button_delete.setOnClickListener {
            callback!!.onDeletePressed(shoppingListIndex, itemIndex)
            activity!!.supportFragmentManager.popBackStack()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(itemIndex: Int, shoppingListIndex: Int, fragmentText: String) =
            FragmentEdit().apply {
                arguments = Bundle().apply {
                    putInt(ITEM_INDEX, itemIndex)
                    putInt(SHOPPING_LIST_INDEX, shoppingListIndex)
                    putString(FRAGMENT_TEXT, fragmentText)
                }
            }
    }

    fun buttonCancel(){
        activity!!.supportFragmentManager.popBackStack()
    }

    interface OnDeletePressedListener{
        fun onDeletePressed(shoppingListIndex: Int, itemIndex: Int)
    }

    fun setOnDeletePressedListener(onDeletePressedListener: OnDeletePressedListener){
        callback = onDeletePressedListener
    }
}
