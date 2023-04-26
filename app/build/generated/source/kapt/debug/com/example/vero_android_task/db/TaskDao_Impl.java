package com.example.vero_android_task.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskClass> __insertionAdapterOfTaskClass;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskClass = new EntityInsertionAdapter<TaskClass>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tasks` (`task`,`title`,`description`,`sort`,`wageType`,`businessUnitKey`,`businessUnit`,`parentTaskID`,`preplanningBoardQuickSelect`,`colorCode`,`workingTime`,`isAvailableInTimeTrackingKioskMode`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskClass value) {
        if (value.getTask() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTask());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getSort() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSort());
        }
        if (value.getWageType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWageType());
        }
        if (value.getBusinessUnitKey() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBusinessUnitKey());
        }
        if (value.getBusinessUnit() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getBusinessUnit());
        }
        if (value.getParentTaskID() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getParentTaskID());
        }
        if (value.getPreplanningBoardQuickSelect() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getPreplanningBoardQuickSelect());
        }
        if (value.getColorCode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getColorCode());
        }
        if (value.getWorkingTime() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getWorkingTime());
        }
        final int _tmp = value.isAvailableInTimeTrackingKioskMode() ? 1 : 0;
        stmt.bindLong(12, _tmp);
      }
    };
  }

  @Override
  public void insert(final TaskClass task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskClass.insert(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Object getAll(final Continuation<? super List<TaskClass>> $completion) {
    final String _sql = "SELECT * FROM tasks";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TaskClass>>() {
      @Override
      public List<TaskClass> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTask = CursorUtil.getColumnIndexOrThrow(_cursor, "task");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSort = CursorUtil.getColumnIndexOrThrow(_cursor, "sort");
          final int _cursorIndexOfWageType = CursorUtil.getColumnIndexOrThrow(_cursor, "wageType");
          final int _cursorIndexOfBusinessUnitKey = CursorUtil.getColumnIndexOrThrow(_cursor, "businessUnitKey");
          final int _cursorIndexOfBusinessUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "businessUnit");
          final int _cursorIndexOfParentTaskID = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskID");
          final int _cursorIndexOfPreplanningBoardQuickSelect = CursorUtil.getColumnIndexOrThrow(_cursor, "preplanningBoardQuickSelect");
          final int _cursorIndexOfColorCode = CursorUtil.getColumnIndexOrThrow(_cursor, "colorCode");
          final int _cursorIndexOfWorkingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "workingTime");
          final int _cursorIndexOfIsAvailableInTimeTrackingKioskMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isAvailableInTimeTrackingKioskMode");
          final List<TaskClass> _result = new ArrayList<TaskClass>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskClass _item;
            final String _tmpTask;
            if (_cursor.isNull(_cursorIndexOfTask)) {
              _tmpTask = null;
            } else {
              _tmpTask = _cursor.getString(_cursorIndexOfTask);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSort;
            if (_cursor.isNull(_cursorIndexOfSort)) {
              _tmpSort = null;
            } else {
              _tmpSort = _cursor.getString(_cursorIndexOfSort);
            }
            final String _tmpWageType;
            if (_cursor.isNull(_cursorIndexOfWageType)) {
              _tmpWageType = null;
            } else {
              _tmpWageType = _cursor.getString(_cursorIndexOfWageType);
            }
            final String _tmpBusinessUnitKey;
            if (_cursor.isNull(_cursorIndexOfBusinessUnitKey)) {
              _tmpBusinessUnitKey = null;
            } else {
              _tmpBusinessUnitKey = _cursor.getString(_cursorIndexOfBusinessUnitKey);
            }
            final String _tmpBusinessUnit;
            if (_cursor.isNull(_cursorIndexOfBusinessUnit)) {
              _tmpBusinessUnit = null;
            } else {
              _tmpBusinessUnit = _cursor.getString(_cursorIndexOfBusinessUnit);
            }
            final String _tmpParentTaskID;
            if (_cursor.isNull(_cursorIndexOfParentTaskID)) {
              _tmpParentTaskID = null;
            } else {
              _tmpParentTaskID = _cursor.getString(_cursorIndexOfParentTaskID);
            }
            final String _tmpPreplanningBoardQuickSelect;
            if (_cursor.isNull(_cursorIndexOfPreplanningBoardQuickSelect)) {
              _tmpPreplanningBoardQuickSelect = null;
            } else {
              _tmpPreplanningBoardQuickSelect = _cursor.getString(_cursorIndexOfPreplanningBoardQuickSelect);
            }
            final String _tmpColorCode;
            if (_cursor.isNull(_cursorIndexOfColorCode)) {
              _tmpColorCode = null;
            } else {
              _tmpColorCode = _cursor.getString(_cursorIndexOfColorCode);
            }
            final String _tmpWorkingTime;
            if (_cursor.isNull(_cursorIndexOfWorkingTime)) {
              _tmpWorkingTime = null;
            } else {
              _tmpWorkingTime = _cursor.getString(_cursorIndexOfWorkingTime);
            }
            final boolean _tmpIsAvailableInTimeTrackingKioskMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAvailableInTimeTrackingKioskMode);
            _tmpIsAvailableInTimeTrackingKioskMode = _tmp != 0;
            _item = new TaskClass(_tmpTask,_tmpTitle,_tmpDescription,_tmpSort,_tmpWageType,_tmpBusinessUnitKey,_tmpBusinessUnit,_tmpParentTaskID,_tmpPreplanningBoardQuickSelect,_tmpColorCode,_tmpWorkingTime,_tmpIsAvailableInTimeTrackingKioskMode);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
