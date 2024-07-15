package com.android.expensetracker.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.expensetracker.R
import com.android.expensetracker.dataModel.ExpenseData
import com.android.expensetracker.database.data.Expense
import com.android.expensetracker.databinding.ExpenseListItemLayoutBinding
import com.android.expensetracker.interfaces.DeleteExpense
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseListAdapter(
    private val deleteExpense: DeleteExpense,
    private val data: List<ExpenseData>,
) :
    RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>(), DeleteExpense {

    private var previousImageView: ImageView? = null
    private var previousLinearLayout: LinearLayout? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ExpenseListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.binding.apply {

            dateTextView.text = "Date: ${formatDate(item.date)}"
            amountTextView.text = "Amount: ${item.expense}"

            imgOpenDetails.setOnClickListener {

                if (imgOpenDetails.tag == "OPEN") {
                    closeDetails(imgOpenDetails, llOpenDetails)
                } else {
                    if (previousImageView != null && previousLinearLayout != null) {
                        closeDetails(previousImageView!!, previousLinearLayout!!)
                    }
                    openDetails(imgOpenDetails, llOpenDetails, detailsRecyclerView, item)
                    previousImageView = imgOpenDetails
                    previousLinearLayout = llOpenDetails
                }
                Log.d(TAG, "onBindViewHolder: show details button clicked.")
            }

        }
    }

    override fun getItemCount(): Int = data.size


    class ViewHolder(val binding: ExpenseListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // No need for findViewById here, access views directly through binding
    }

    private fun closeDetails(imageView: ImageView, linearLayout: LinearLayout) {
        imageView.tag = "CLOSE"
        imageView.setImageResource(R.drawable.icon_right_arrow)
        linearLayout.visibility = View.GONE
    }

    private fun openDetails(
        imageView: ImageView,
        linearLayout: LinearLayout,
        recyclerView: RecyclerView,
        item: ExpenseData
    ) {
        imageView.tag = "OPEN"
        imageView.setImageResource(R.drawable.icon_down_arrow)
        linearLayout.visibility = View.VISIBLE

        val adapter = ExpenseDetailsAdapter(this,item.expenseDetails)
        recyclerView.layoutManager = LinearLayoutManager(imageView.context)
        recyclerView.adapter = adapter
    }

    private fun formatDate(dateString: String): String {
        val inputFormat= SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE dd/MM/yyyy", Locale.ENGLISH)

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }
    companion object {
        private const val TAG = "ExpenseListAdapter"
    }

    override fun onDeleteExpense(expense: Expense) {
        deleteExpense.onDeleteExpense(expense)
    }
}