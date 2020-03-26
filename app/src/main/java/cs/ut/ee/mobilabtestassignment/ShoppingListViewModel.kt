package cs.ut.ee.mobilabtestassignment

import android.util.Log
import androidx.lifecycle.ViewModel

class ShoppingListViewModel: ViewModel(){
    var shoppingLists = ArrayList<ArrayList<ItemEntity>>()


    fun addItemToShoppingList(item: ItemEntity, listIndex: Int){
        shoppingLists[listIndex].add(0, item)
    }

    fun addNewShoppingList(){
        shoppingLists.add(ArrayList<ItemEntity>())
    }
}