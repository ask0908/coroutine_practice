package com.example.kotlinprac.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/* Database class : 개발자가 정의한 DAO 인스턴스를 앱에 제공. 앱은 DAO를 써서 DB 데이터를 데이터 아이템 객체의 인스턴스로 검색할 수 있다
* 앱은 정의된 데이터 항목을 써서 일치하는 테이블의 행을 업데이트하거나 새 행을 만들 수도 있다. 아래는 RoomDatabase 인스턴스를 가져오는 일반적인 프로세스다
* RoomDatabase 클래스를 상속하는 public abstract class를 만든다
* -> 클래스에 @Database 어노테이션 추가. 인수에는 DB 항목을 나열하고 버전 번호를 설정한다
* -> ItemDao 인스턴스를 리턴하는 추상 함수나 속성을 정의한다. 그러면 Room이 구현을 생성한다
* -> 전체 앱에 RoomDatabase 인스턴스 하나만 있으면 되므로 RoomDatabase를 싱글톤으로 만든다
* -> Room.DatabaseBuilder를 써서 DB 생성. 있으면 기존 DB 반환 */
// entities : @Entity를 쓴 data class 나열, exportSchema : false면 스키마 버전 기록 백업을 유지하지 않음
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase: RoomDatabase() {
    // database는 DAO를 알아야 한다. 클래스 본문에서 ItemDao 인터페이스를 리턴하는 추상 함수를 선언한다. DAO는 여럿 있을 수 있다
    abstract fun itemDao(): ItemDao

    companion object {
        /* 휘발성 변수의 값은 캐시되지 않고 모든 읽기, 쓰기는 기본 메모리에서 실행됨 -> INSTANCE 값이 항상 최신으로 유지되고 모든 실행 쓰레드에서
        * 같은지 확인할 수 있다 -> 한 쓰레드에서 INSTANCE를 바꾸면 다른 모든 쓰레드에 즉시 표시됨 */
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null

        fun getDatabase(context: Context): ItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                )
                    /* 일반적으로 스키마 변경 시점에 관한 이전 전략과 같이 이전 객체를 제공해야 한다
                    * 이전 객체는 데이터가 손실되지 않게 이전 스키마의 모든 행을 가져와 새 스키마의 행으로 바꾸는 방법을 정의하는 객체다
                    * DB를 제거했다가 다시 빌드해 데이터를 손실시킨다 */
                    .fallbackToDestructiveMigration()
                    .build()
                return instance
            }
        }
    }
}