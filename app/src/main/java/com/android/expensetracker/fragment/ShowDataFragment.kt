package com.android.expensetracker.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.expensetracker.interfaces.DeleteExpense
import com.android.expensetracker.dataModel.ExpenseData
import com.android.expensetracker.adapter.ExpenseListAdapter
import com.android.expensetracker.database.DatabaseProvider
import com.android.expensetracker.database.data.Expense
import com.android.expensetracker.databinding.FragmentShowDataBinding
import kotlinx.coroutines.launch


class ShowDataFragment : Fragment(), DeleteExpense {

    private var _binding: FragmentShowDataBinding? = null
    private val binding get() = _binding!!

    private lateinit var context: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDataBinding.inflate(inflater, container, false)
        context = requireContext()
        return binding.root
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var expensesText = ""
        val database = DatabaseProvider.getDatabase(context)
        val expenseDao = database.expenseDao()
        val expenseList = expenseDao.getAllExpenses()

        lifecycleScope.launch {
            expenseList.collect { expenseList ->
                for (expense in expenseList) {
                    expensesText += "${expense.date} - ${expense.expense} - ${expense.amount} - ${expense.tag}\n"
                    Log.d("TAG", "Expense: $expense") // Log each Expense object
                }
                val dailyExpense = convertToExpenseData(expenseList)

                val adapter = ExpenseListAdapter(this@ShowDataFragment, dailyExpense)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private fun convertToExpenseData(expenses: List<Expense>): List<ExpenseData> {

        return expenses.groupBy { it.date }
            .map { (date, expensesForDate) ->
                var totalExpense = 0.0
                for (expense in expensesForDate) {
                    totalExpense += expense.amount
                }
                ExpenseData(date, totalExpense.toString(), expensesForDate)
            }
    }

    companion object {
        private const val TAG = "ShowDataFragment"
    }

    override fun onDeleteExpense(expense: Expense) {
        val database = DatabaseProvider.getDatabase(this@ShowDataFragment.context)
        val expenseDao = database.expenseDao()
        lifecycleScope.launch {
            expenseDao.deleteExpense(expense)
        }
    }

}

