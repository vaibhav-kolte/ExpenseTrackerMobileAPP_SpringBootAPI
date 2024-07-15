package com.android.expensetracker.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.expensetracker.database.DatabaseProvider
import com.android.expensetracker.database.data.Expense
import com.android.expensetracker.R
import com.android.expensetracker.databinding.ActivityAddExpensesBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddExpensesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpensesBinding
    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        id = intent.getStringExtra("ID").toString()
        val expense = intent.getStringExtra("EXPENSE")
        val date = intent.getStringExtra("DATE")
        val amount = intent.getStringExtra("AMOUNT")
        val tag = intent.getStringExtra("TAG")

        Log.d(TAG, "onCreate: id=${id} expense=${expense} amount=${amount} date=${date} tag=${tag}")
        clearValue()
        handleClickEvents()

        binding.etDate.setText(date)
        binding.etName.setText(expense)
        binding.etAmount.setText(amount)
        binding.tag.setText(tag)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleClickEvents() {
        binding.etDate.setOnTouchListener { _, event ->
            val drawableRight = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.etDate.right - binding.etDate.compoundDrawables[drawableRight].bounds.width())) {
                    showDatePickerDialog()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.btnShowAll.setOnClickListener {
            var expensesText = ""
            val database = DatabaseProvider.getDatabase(this)
            val expenseDao = database.expenseDao()
            val expenseList = expenseDao.getAllExpenses()
            lifecycleScope.launch {
                expenseList.collect { expenseList ->
                    for (expense in expenseList) {
                        expensesText += "${expense.date} - ${expense.expense} - ${expense.amount} - ${expense.tag}\n"
                        Log.d("TAG", "Expense: $expense") // Log each Expense object
                    }
                    binding.tvListview.text = expensesText
                }
            }


        }
        binding.btnInsert.setOnClickListener {
            val database = DatabaseProvider.getDatabase(this)
            val expenseDao = database.expenseDao()


            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = format.parse(binding.etDate.text.toString())
            val expense = binding.etName.text.toString()
            if (binding.etAmount.text.toString().isEmpty()) return@setOnClickListener
            val amount = binding.etAmount.text.toString().toFloat()

            val tag: String? = binding.tag.text.toString().takeIf { it != "Select Tag" }

            if (date == null || expense.isEmpty()) {
                Toast.makeText(this, "Enter valid value", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            // Insert
            val newExpense =
                Expense(date = date.toString(), expense = expense, amount = amount, tag = tag)
            lifecycleScope.launch {
                expenseDao.insertExpense(newExpense)
                clearValue()
            }
        }

        binding.btnUpdate.setOnClickListener {
            val database = DatabaseProvider.getDatabase(this)
            val expenseDao = database.expenseDao()


            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = format.parse(binding.etDate.text.toString())
            val expense = binding.etName.text.toString()
            if (binding.etAmount.text.toString().isEmpty()) return@setOnClickListener
            val amount = binding.etAmount.text.toString().toFloat()

            val tag: String? = binding.tag.text.toString().takeIf { it != "Select Tag" }

            if (date == null || expense.isEmpty()) {
                Toast.makeText(this, "Enter valid value", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            // Insert
            val newExpense =
                Expense(
                    id.toInt(),
                    date = date.toString(),
                    expense = expense,
                    amount = amount,
                    tag = tag
                )
            lifecycleScope.launch {
                expenseDao.updateExpense(newExpense)
                clearValue()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clearValue() {
        val currentDate = Date()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

        binding.etDate.setText(formattedDate)


        val items =
            listOf("Select Tag", "Food", "Transport", "Entertainment", "Home Expenses", "Personal")
        binding.tag.setText(items[0])
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (binding.tag as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.etName.setText("")
        binding.etAmount.setText("")
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the TextView with the selected date
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                binding.etDate.setText(selectedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    companion object {
        private const val TAG = "AddExpensesActivity"
    }
}