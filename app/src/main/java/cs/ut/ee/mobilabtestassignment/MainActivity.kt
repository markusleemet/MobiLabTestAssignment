package cs.ut.ee.mobilabtestassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * This function creates new items list and stores it into view model.
     */
    fun addNewItemsList(v: View){
        val listActivityIntent = Intent(this, ListActivity::class.java)
        startActivity(listActivityIntent)
    }
}
