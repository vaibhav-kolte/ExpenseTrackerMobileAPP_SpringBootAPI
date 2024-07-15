package com.android.expensetracker.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.expensetracker.activity.AddExpensesActivity
import com.android.expensetracker.database.data.Expense
import com.android.expensetracker.databinding.ExpenseDetailsLayoutBinding
import com.android.expensetracker.interfaces.DeleteExpense
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseDetailsAdapter(
    private val deleteExpense: DeleteExpense,
    private val expenseDetailsList: List<Expense>
) :
    RecyclerView.Adapter<ExpenseDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ExpenseDetailsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseDetailsList[position]
        holder.binding.apply {
            tvTitle.text = item.expense
            tvAmount.text = item.amount.toString()

            imgEdit.setOnClickListener {
                val intent = Intent(imgEdit.context, AddExpensesActivity::class.java)
                intent.putExtra("ID", item.id.toString())
                intent.putExtra("DATE", formatDate(item.date))
                intent.putExtra("EXPENSE", item.expense)
                intent.putExtra("AMOUNT", item.amount.toString())
                intent.putExtra("TAG", item.tag)
                imgEdit.context.startActivity(intent)
            }

            imgDelete.setOnClickListener {
                deleteExpense.onDeleteExpense(item)
            }
        }


    }

    override fun getItemCount(): Int = expenseDetailsList.size

    private fun formatDate(dateString: String): String {
        val inputFormat= SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }
    class ViewHolder(val binding: ExpenseDetailsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // No need for findViewById here, access views directly through binding
    }
}
