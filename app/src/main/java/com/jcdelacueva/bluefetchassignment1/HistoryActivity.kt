package com.jcdelacueva.bluefetchassignment1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcdelacueva.bluefetchassignment1.databinding.ActivityHistoryBinding
import com.jcdelacueva.bluefetchassignment1.databinding.ActivityMainBinding
import com.jcdelacueva.bluefetchassignment1.databinding.ItemHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var list: List<HistoryItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = intent.getParcelableArrayExtra(HISTORY)?.toList() as List<HistoryItem>

        adapter = HistoryAdapter(list)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = this@HistoryActivity.adapter
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {

        private const val HISTORY = "HISTORY"

        fun start(context: Context, history: List<HistoryItem>) {
            context.startActivity(Intent(context, HistoryActivity::class.java).apply {
                putExtra(HISTORY, history.toTypedArray())
            })
        }
    }
}

class HistoryAdapter(private val list: List<HistoryItem>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(ItemHistoryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryItem) {
            binding.tvFormula.text = "${item.leftOperand} ${item.operation} ${item.rightOperand}"
            binding.tvResult.text = item.result
        }
    }
}
