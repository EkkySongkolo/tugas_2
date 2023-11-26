package com.example.tugas_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_2.R.*
import java.util.Collections
import java.util.Locale

class MainActivity : AppCompatActivity() {


    private lateinit var newRecylerview : RecyclerView
    private lateinit var newArrayList : ArrayList<profil>
    private lateinit var tempArrayList : ArrayList<profil>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var deskripsi : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        imageId = arrayOf(
            drawable.a,
            drawable.b,
            drawable.c,
            drawable.d,
            drawable.e,
            drawable.f,
            drawable.g,
            drawable.h,
            drawable.i,
            drawable.j
        )

        heading = arrayOf(
            "Sartika Dewi  ",
            "Muttiara Ahzahra ",
            "Ayu Putri Ningsih ",
            "Susi Susanti ",
            "Andi Kumala Hijau",
            "Ratu Consima ",
            "Nurul Azizah Zhra ",
            "Amelia ",
            "Nana Raihanza ",
            "A Rizki Awan "
        )

        deskripsi = arrayOf(

            getString(string.profil_a),
            getString(string.profil_b),
            getString(string.profil_c),
            getString(string.profil_d),
            getString(string.profil_e),
            getString(string.profil_f),
            getString(string.profil_g),
            getString(string.profil_h),
            getString(string.profil_i),
            getString(string.profil_j)
        )





        newRecylerview =findViewById(id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)


        newArrayList = arrayListOf<profil>()
        tempArrayList = arrayListOf<profil>()
        getUserdata()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    newArrayList.forEach {

                        if (it.heading.toLowerCase(Locale.getDefault()).contains(searchText)){


                            tempArrayList.add(it)

                        }

                    }

                    newRecylerview.adapter!!.notifyDataSetChanged()

                }else{

                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecylerview.adapter!!.notifyDataSetChanged()

                }


                return false

            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getUserdata() {

        for(i in imageId.indices){

            val news = profil(imageId[i],heading[i])
            newArrayList.add(news)

        }

        tempArrayList.addAll(newArrayList)


        val adapter = MyAdapter(tempArrayList)
        val swipegesture = object : SwipeGesture(this){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

                val from_pos = viewHolder.adapterPosition
                val to_pos = target.adapterPosition

                Collections.swap(newArrayList,from_pos,to_pos)
                adapter.notifyItemMoved(from_pos,to_pos)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){

                    ItemTouchHelper.LEFT ->{

                        adapter.deleteItem(viewHolder.adapterPosition)
                    }

                    ItemTouchHelper.RIGHT -> {

                        val archiveItem = newArrayList[viewHolder.adapterPosition]
                        adapter.deleteItem(viewHolder.adapterPosition)
                        adapter.addItem(newArrayList.size,archiveItem)

                    }

                }

            }

        }
        val touchHelper = ItemTouchHelper(swipegesture)
        touchHelper.attachToRecyclerView(newRecylerview)
        newRecylerview.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {


                val intent = Intent(this@MainActivity,profil_activity::class.java)
                intent.putExtra("heading",newArrayList[position].heading)
                intent.putExtra("imageId",newArrayList[position].titleImage)
                intent.putExtra("deskripsi",deskripsi[position])
                startActivity(intent)

            }


        })

    }
}
