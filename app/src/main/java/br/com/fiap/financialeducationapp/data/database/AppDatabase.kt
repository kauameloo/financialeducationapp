package br.com.fiap.financialeducationapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fiap.financialeducationapp.data.dao.BudgetEntryDao
import br.com.fiap.financialeducationapp.data.dao.CourseDao
import br.com.fiap.financialeducationapp.data.dao.SavingsGoalDao
import br.com.fiap.financialeducationapp.data.dao.UserDao
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import br.com.fiap.financialeducationapp.data.model.Course
import br.com.fiap.financialeducationapp.data.model.CourseProgress
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import br.com.fiap.financialeducationapp.data.model.User
import br.com.fiap.financialeducationapp.util.Converters

@Database(
    entities = [
        User::class,
        BudgetEntry::class,
        SavingsGoal::class,
        Course::class,
        CourseProgress::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun budgetEntryDao(): BudgetEntryDao
    abstract fun savingsGoalDao(): SavingsGoalDao
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "financial_education_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}