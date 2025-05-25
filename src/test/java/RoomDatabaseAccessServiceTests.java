import com.kayak.hotelsearch.room.Room;
import com.kayak.hotelsearch.room.RoomDatabaseAccessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoomDatabaseAccessServiceTests {
    @Test
    public void getInstanceTest() {
        RoomDatabaseAccessService service=RoomDatabaseAccessService.getInstance();
        Assertions.assertNotNull(service);
    }
    @Test
    public void loadRoomTest() {
        RoomDatabaseAccessService service=RoomDatabaseAccessService.getInstance();
        Assertions.assertNotNull(service);
        Room room = service.loadRoom(105);
        Assertions.assertInstanceOf(Room.class,room);
        Assertions.assertEquals(105,room.getRoomNumber());
    }
}
