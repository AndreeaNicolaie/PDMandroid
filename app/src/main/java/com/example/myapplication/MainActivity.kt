package com.example.myapplication
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonAddItem: Button
    private lateinit var listView: ListView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    private val fakeEmail = "user@example.com"
    private val fakePassword = "password123"

    private val listFragment = ListFragment()
    private val cameraFragment = CameraFragment()

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonAddItem = findViewById(R.id.buttonAddItem)
        listView = findViewById(R.id.listView)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment)
            .commit()

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_list -> {
                    switchFragment(listFragment)
                    true
                }
                R.id.navigation_camera -> {
                    switchFragment(cameraFragment)
                    true
                }
                else -> false
            }
        }

        buttonLogin.setOnClickListener {
            login()
        }

        buttonAddItem.setOnClickListener {
            addItem()
        }

        val items = mutableListOf("Item 1", "Item 2", "Item 3", "Item 4")
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            editItem(position)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            deleteItem(position)
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun login() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (email == fakeEmail && password == fakePassword) {
            showList()
        } else {
            // Autentificare esuata 

        }
    }

    private fun showList() {
        // Afișează lista și ascunde elementele de login
        listView.visibility = View.VISIBLE
        editTextEmail.visibility = View.GONE
        editTextPassword.visibility = View.GONE
        buttonLogin.visibility = View.GONE
        buttonAddItem.visibility = View.VISIBLE
    }

    private fun editItem(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Item")

        val input = EditText(this)
        input.setText(adapter.getItem(position))
        builder.setView(input)

        builder.setPositiveButton("Save") { _, _ ->
            val newItem = input.text.toString()
            adapter.remove(adapter.getItem(position))
            adapter.insert(newItem, position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Item edited", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun deleteItem(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Item")

        builder.setPositiveButton("Yes") { _, _ ->
            adapter.remove(adapter.getItem(position))
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addItem() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Item")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val newItem = input.text.toString()
            adapter.add(newItem)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
