package com.gotruemotion.trueoverflow

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.gotruemotion.trueoverflow.service.SearchApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var sortBy = "creation"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        val viewAdapter = RecyclerAdapter(arrayListOf())

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean =
                query?.let {
                    NetworkClient.getService(this@MainActivity, SearchApi::class.java)
                        .search(order = "desc", sort = sortBy, query = query)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { result ->
                            viewAdapter.items.clear()
                            result.items?.let {
                                viewAdapter.items.addAll(result.items)
                                viewAdapter.notifyDataSetChanged()
                            }
                        }.subscribe({}, { error -> Log.e("TrueOverflow","Error searching", error)})

                    // close keyboard
                    (this@MainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow((this@MainActivity.currentFocus ?: View(this@MainActivity)).windowToken, 0)

                    true
                } ?: false

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        ArrayAdapter.createFromResource(this@MainActivity, R.array.sort_choices, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                sortBy = parent.getItemAtPosition(position) as String
            }
        }
    }
}
