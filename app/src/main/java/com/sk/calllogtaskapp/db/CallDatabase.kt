package com.sk.calllogtaskapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CallCacheEntity::class, ContactCacheEntity::class], version = 1)
abstract class CallDatabase : RoomDatabase() {
    abstract fun callDao(): CallDao

    abstract  fun contactDao(): ContactDao



    companion object {
        //        val DATABASE_NAME: String = "call_db"
        @Volatile
        private var INSTANCE: CallDatabase? = null
        /*val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE contacts ADD COLUMN Date")
            }
        }*/

        fun getDatabase(context: Context): CallDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CallDatabase::class.java,
                    "db_calls"
                )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}