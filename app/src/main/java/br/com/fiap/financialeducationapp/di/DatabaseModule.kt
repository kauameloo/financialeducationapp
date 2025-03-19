package br.com.fiap.financialeducationapp.di

import android.content.Context
import br.com.fiap.financialeducationapp.data.dao.BudgetEntryDao
import br.com.fiap.financialeducationapp.data.dao.CourseDao
import br.com.fiap.financialeducationapp.data.dao.SavingsGoalDao
import br.com.fiap.financialeducationapp.data.dao.UserDao
import br.com.fiap.financialeducationapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideBudgetEntryDao(database: AppDatabase): BudgetEntryDao {
        return database.budgetEntryDao()
    }

    @Provides
    fun provideSavingsGoalDao(database: AppDatabase): SavingsGoalDao {
        return database.savingsGoalDao()
    }

    @Provides
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.courseDao()
    }
}