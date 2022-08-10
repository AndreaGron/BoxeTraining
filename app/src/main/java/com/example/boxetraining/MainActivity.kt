package com.example.boxetraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val males= listOf<String>("Luca","Marco","Edo","Pino","Gino","Cristo","Luca","Marco","Edo","Pino","Gino","Cristo").map { it -> MaleItem(it) }
        val females= listOf<String>("Maria","Pina","Giorgia","Sandra","Gina","Cristo","Maria","Pina","Giorgia","Sandra","Gina","Crista").map { it -> FemaleItem(it) }
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        val persons = males.zip(females).flatMap { it -> listOf(it.first,it.second) }
        rv.adapter = ItemAdapter(persons.shuffled())
    }
}

open class Item (val name:String)

class MaleItem(name:String) : Item(name)

class FemaleItem(name:String) : Item(name)

class ItemAdapter(val items:List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val nameView = v.findViewById<TextView>(R.id.textView)
        val button = v.findViewById<Button>(R.id.button)

        fun bind(item:Item){
            nameView.text=item.name
            button.setOnClickListener{
                Toast.makeText(nameView.context, item.name, Toast.LENGTH_LONG).show()
            }
        }

        fun unbind(){
            button.setOnClickListener { null }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout =  if(viewType == R.layout.male_layout) {
                inflater.inflate(R.layout.male_layout, parent, false)
        }
        else{
            inflater.inflate(R.layout.female_layout, parent, false)
        }
        return ItemViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemViewType(position: Int): Int {
        if(items[position] is MaleItem)
            return R.layout.male_layout
        else
            return R.layout.female_layout

    }

}