package cs.ut.ee.mobilabtestassignment

import androidx.lifecycle.ViewModel

class ShoppingListViewModel: ViewModel(){
    var shoppingLists = ArrayList<ArrayList<ItemEntity>>()


    fun addItemToShoppingList(item: ItemEntity, listIndex: Int){
        shoppingLists[listIndex].add(item)
    }

    fun addNewShoppingList(){
        shoppingLists.add(ArrayList<ItemEntity>())
    }
}