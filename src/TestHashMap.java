import org.junit.Test;

import static org.junit.Assert.*;

public class TestHashMap {

    @Test
    public void testSize(){
        MidnightMap<Object,Object> map = new JianghongHashMap<>();
        assertEquals(0,map.size());

        map.put(null,null);
        assertEquals(1,map.size());

        map.put(null,null);
        assertEquals(1,map.size());

        map.put(1,1);
        assertEquals(2,map.size());

        map.put(1,2);
        assertEquals(2,map.size());

    }

    @Test
    public void testIsEmpty() {
        JianghongHashMap<Object,Object> map = new JianghongHashMap<>();
        assertTrue(map.isEmpty());
        map.put(1,2);
        assertFalse(map.isEmpty());
    }

}
