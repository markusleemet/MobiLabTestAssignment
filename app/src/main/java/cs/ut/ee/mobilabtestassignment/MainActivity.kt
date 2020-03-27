package cs.ut.ee.mobilabtestassignment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ListFragment.OnFragmentClosedListener, ShoppingListViewHolder.OnShoppingListClickListener, ShoppingListViewHolder.OnShoppingListLongClickListener, FragmentEdit.OnDeletePressedListener{
    lateinit var model: ShoppingListViewModel
    lateinit var shoppingListAdapter: ShoppingListsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //reference to viewModel
        model = ViewModelProvider(this).get(ShoppingListViewModel::class.java)

        //setup recycler view
        shoppingListAdapter = ShoppingListsAdapter(model.shoppingLists, this, this)
        recycler_view_shopping_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = shoppingListAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        }

        //read in data from database
        model.readFromDataBase()
        shoppingListAdapter.notifyDataSetChanged()
        model.dataReceived.observe(this, Observer {
            shoppingListAdapter.notifyDataSetChanged()
            checkIfShoppingListIsEmpty()
        })
    }

    /**
     * This function creates new items list and stores it into view model.
     */
    fun addNewShoppingList(v: View){
        model.addNewShoppingList()
        val newShoppingListIndex = model.shoppingLists.lastIndex
        openListFragment(newShoppingListIndex)
    }

    override fun onFragmentClosed() {
        shoppingListAdapter.notifyDataSetChanged()
        checkIfShoppingListIsEmpty()
    }

    //show or hide empty list text
    private fun checkIfShoppingListIsEmpty(){
        if (model.shoppingLists.isEmpty().not()) {
            text_view_no_lists_available.visibility = View.INVISIBLE
        }else{
            text_view_no_lists_available.visibility = View.VISIBLE
        }
    }

    private fun openListFragment(listIndex: Int){
        val listFragment = ListFragment.newInstance(listIndex)
        listFragment.setOnFragmentClosedListner(this)
        val transaction = supportFragmentManager.beginTransaction().add(R.id.constraint_layout_shopping_lists, listFragment)
        transaction.addToBackStack("listFragment")
        transaction.commit()

        floating_action_button.hide()
    }

    override fun onDeletePressed(shoppingListIndex: Int, itemIndex: Int) {
        model.shoppingLists.removeAt(shoppingListIndex)
        shoppingListAdapter.notifyDataSetChanged()
        checkIfShoppingListIsEmpty()
    }

    override fun onShoppingListClick(position: Int) {
        openListFragment(position)
    }

    override fun onShoppingListLongClick(position: Int) {
        val editFragment = FragmentEdit.newInstance(-1, position, "Delete list?")
        editFragment.setOnDeletePressedListener(this)
        val transaction = supportFragmentManager.beginTransaction().add(R.id.constraint_layout_shopping_lists, editFragment)
        transaction.addToBackStack("editFragment")
        transaction.commit()
    }

    override fun onPause() {
        super.onPause()
        model.saveToDataBase()
        Log.i("ShoppingList", "onPause() was called")
    }
}
