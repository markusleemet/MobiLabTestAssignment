package cs.ut.ee.mobilabtestassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENDCALL
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        //listener listens submit button and then adds item to list
        editTextAddItem.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val item = outlinedTextFieldInsertItem.editText?.text.toString()
                    Log.i("ShoppingList", "return action took place(item: $item)")
                    return true
                }
                return false
            }
        })

        recyler_view_items_list.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = ItemsAdapter(arrayListOf<ItemEntity>(ItemEntity("piim", true), ItemEntity("viin", false)))
            addItemDecoration(DividerItemDecoration(this@ListActivity, LinearLayoutManager.VERTICAL))
        }
    }
}
