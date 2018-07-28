package calle.david.udacityrecipeapp.Data.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface RecentVisitsDao {
    @Insert
    void save(RecentVisit recentVisit);
    @Query("DELETE FROM RecentVisit")
    void deleteRecentVisit();
}
