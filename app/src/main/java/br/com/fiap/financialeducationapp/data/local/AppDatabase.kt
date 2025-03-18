package br.com.fiap.financialeducationapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.fiap.financialeducationapp.data.local.dao.BudgetDao
import br.com.fiap.financialeducationapp.data.local.dao.SavingsGoalDao
import br.com.fiap.financialeducationapp.data.local.dao.UserDao
import br.com.fiap.financialeducationapp.data.local.entity.BudgetEntryEntity
import br.com.fiap.financialeducationapp.data.local.entity.SavingsGoalEntity
import br.com.fiap.financialeducationapp.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, BudgetEntryEntity::class, SavingsGoalEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun budgetDao(): BudgetDao
    abstract fun savingsGoalDao(): SavingsGoalDao

    companion object {
        const val DATABASE_NAME = "financial_education_db"
    }
}