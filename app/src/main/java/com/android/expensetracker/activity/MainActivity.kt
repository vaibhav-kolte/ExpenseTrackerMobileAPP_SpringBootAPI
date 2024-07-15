package com.android.expensetracker.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.expensetracker.R
import com.android.expensetracker.database.DatabaseProvider
import com.android.expensetracker.database.data.Lent
import com.android.expensetracker.databinding.ActivityMainBinding
import com.android.expensetracker.fragment.HomeFragment
import com.android.expensetracker.fragment.ShowDataFragment
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddExpensesActivity::class.java))
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, HomeFragment()) // Replace with your Fragment and container ID
                        .commit()

                    addLentAmount()
                    true
                }
                R.id.navigation_show_data -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, ShowDataFragment()) // Replace with your Fragment and container ID
                        .commit()
                    showLentAmount()
                    true
                }
                else -> false
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, HomeFragment()) // Replace with your Fragment and container ID
            .commit()
    }

    private fun showLentAmount() {
        val database = DatabaseProvider.getDatabase(this)
        val lentDao = database.lentDao()
        val lentList = lentDao.getAllLent()
        lifecycleScope.launch {
            lentList.collect { lentList ->
                for (lent in lentList) {
                    Log.d(TAG, "Lent: $lent") // Log each Expense object
                }
            }
        }
    }

    private fun addLentAmount() {
        val database = DatabaseProvider.getDatabase(this)
        val lentDao = database.lentDao()
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.parse("12/07/2024")
        val name = "Amol Rane"
        val reason = "For admission requirements"
        val amount = 7000.0f

        val lent = Lent(date = date!!.toString(),name = name,reason = reason,amount = amount)
        lifecycleScope.launch {
            lentDao.insertLent(lent)
        }
    }

    private fun getFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // For .xlsx files
            // You can add moreMIME types if you want to support other Excel formats
        }

        val pickExcelLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        readExcelFile(uri)
                    }
                }
            }
        pickExcelLauncher.launch(intent)

    }

    private fun readExcelFile(uri: Uri) {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0) // Assuming you want the first sheet

            for (row in sheet) {
                for (cell in row) {
                    val cellValue = when (cell.cellType) {
                        CellType.STRING -> cell.stringCellValue
                        CellType.NUMERIC -> cell.numericCellValue.toString()
                        else -> "Unknown Cell Type"
                    }
                    Log.d("ExcelData", "Cell Value: $cellValue")
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}