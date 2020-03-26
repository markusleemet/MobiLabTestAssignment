package cs.ut.ee.mobilabtestassignment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var model: ShoppingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //reference to viewModel
        model = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
    }

    /**
     * This function creates new items list and stores it into view model.
     */
    fun addNewShoppingList(v: View){
        model.addNewShoppingList()
        val newShoppingListIndex = model.shoppingLists.lastIndex

        val listFragment = ListFragment.newInstance(newShoppingListIndex)
        val transaction = supportFragmentManager.beginTransaction().add(R.id.constraint_layout_shopping_lists, listFragment)
        transaction.addToBackStack("listFragment")
        transaction.commit()

        floating_action_button.hide()
    }

    override fun onResume() {
        super.onResume()
        floating_action_button.show()
    }
}
