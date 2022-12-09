package com.example.kotlinprac.room.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/* DAO : Data Access Object, 추상 인터페이스를 제공해서 지속성 레이어를 앱의 나머지 부분과 분리할 때 사용
* DB 작업 관련 모든 복잡성을 숨긴다 -> 데이터 사용 코드와 상관없이 데이터 접근 계층 변경 가능
* 여기서 만드는 DAO는 DB 쿼리 / 검색, 삽입, 삭제, 업데이트를 위한 편의 함수를 제공하는 인터페이스다
* Room DB는 컴파일 타임에 이 클래스의 구현을 만든다. DB 작업은 오래 걸릴 수 있어서 별도 쓰레드에서 실행해야 한다. 함수를 정지 함수로 만들어
* 코루틴에서 이 함수를 호출하게 한다 */
@Dao
interface ItemDao {
    // OnConflict : 충돌 발생 시 Room에 실행할 작업을 알려줌. IGNORE : PK가 이미 DB에 있으면 새 항목을 무시함
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    /* Flow나 LiveData를 리턴형으로 쓰면 DB 데이터가 바뀔 때마다 알림을 받을 수 있다
    * 지속성 레이어에서 Flow를 쓰는 게 좋다. Room은 Flow를 자동 업데이트하므로 명시적으로 한 번만 데이터를 가져오면 된다
    * 리턴형이 Flow기 때문에 Room은 백그라운드 쓰레드에서도 쿼리를 실행한다. 이걸 일시정지 함수로 만들고 코루틴 스코프에서 호출할 필요는 없다 */
    @Query("SELECT * FROM item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * FROM item ORDER BY name ASC")
    fun getItems(): Flow<List<Item>>
}