package com.example.gesports.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gesports.models.Facility
import com.example.gesports.models.Reservation
import com.example.gesports.models.Team
import com.example.gesports.models.User

@Database(
    entities = [User::class, Team::class, Reservation::class, Facility::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun teamDao(): TeamDao
    abstract fun reservationDao(): ReservationDao
    abstract fun facilityDao(): FacilityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gessport_db"
                )
                    // Recomendado mientras desarrollas: si cambias las tablas, borra la DB anterior
                     //.fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}