package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LongMapImplTest {

    private final int INITIAL_MAX_SIZE = 32;

    @Test
    public void testIsEmptyPositive() {
        LongMapImpl<String> map = new LongMapImpl<>();

        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void testIsEmptyNegative() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "some");

        Assert.assertFalse(map.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMaxMapSizeIllegalArgumentException() {
        LongMapImpl<String> map = new LongMapImpl<>(0);
    }

    @Test
    public void testSetMaxMapSizeLessThanInitialMaxMapSize() {
        LongMapImpl<String> map = new LongMapImpl<>(10);

        Assert.assertEquals(INITIAL_MAX_SIZE, map.getMaxMapSize());
    }

    @Test
    public void testSetMaxMapSizeGraterThanInitialMaxMapSize() {
        LongMapImpl<String> map = new LongMapImpl<>(64);

        Assert.assertEquals(64, map.getMaxMapSize());
    }

    @Test
    public void testSetInitialMaxMapSize() {
        LongMapImpl<String> map = new LongMapImpl<>();

        Assert.assertEquals(INITIAL_MAX_SIZE, map.getMaxMapSize());
    }

    @Test
    public void testSizeEmptyMap() {
        LongMapImpl<String> map = new LongMapImpl<>();

        Assert.assertEquals(0, map.size());
    }

    @Test
    public void testSizeShouldBeTwo() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "some");
        map.put(4, "some text");

        Assert.assertEquals(2, map.size());
    }

    @Test
    public void testPutValue() {
        LongMapImpl<String> map = new LongMapImpl<>();
        String actualResult = map.put(2, "some");

        Assert.assertEquals(1, map.size());
        Assert.assertEquals("some", actualResult);
    }

    @Test
    public void testPutNullValue() {
        LongMapImpl<String> map = new LongMapImpl<>();
        String actualResult = map.put(2, null);

        Assert.assertEquals(1, map.size());
        Assert.assertNull(null, actualResult);
    }

    @Test
    public void testPutByLongKey() {
        LongMapImpl<String> map = new LongMapImpl<>();
        String actualResult = map.put(245648945554544L, "long key");

        Assert.assertEquals(1, map.size());
        Assert.assertEquals("long key", actualResult);
    }

    @Test
    public void testReplaceValueByKey() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value1");
        String actualResult = map.put(2, "value2");

        Assert.assertEquals(1, map.size());
        Assert.assertEquals("value2", actualResult);
    }

    @Test
    public void testGetValue() {
        LongMapImpl<String> map = new LongMapImpl<>();
        String expectedResult = map.put(2, "some1");

        String actualResult = map.get(2);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetValueWrongKey() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "some1");

        String actualResult = map.get(6); // wrong key

        Assert.assertNull(actualResult);
    }

    @Test
    public void testGetNullValue() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, null);

        String actualResult = map.get(2);

        Assert.assertNull(actualResult);
    }

    @Test
    public void testRemoveByKey() {
        LongMapImpl<String> map = new LongMapImpl<>();
        String expectedResult = map.put(2, "value");

        String actualResult = map.remove(2);

        Assert.assertNotNull(actualResult);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAfterRemoveItemAndSizeEqualsToZero() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value");
        map.remove(2);

        Assert.assertEquals(0, map.size());
    }

    @Test
    public void testRemoveByKeyReturnNull() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, null);
        String actualResult = map.remove(2);

        Assert.assertNull(actualResult);
    }

    @Test
    public void testRemoveUnExistedMapItem() {
        LongMapImpl<String> map = new LongMapImpl<>();

        Assert.assertNull(map.remove(2));
    }

    @Test
    public void testContainsKeyAssertTrue() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value");

        Assert.assertTrue(map.containsKey(2));
    }

    @Test
    public void testContainsKeyAssertFalse() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value");

        Assert.assertFalse(map.containsKey(5));
    }

    @Test
    public void testContainsKeyAssertFalseAfterRemove() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value");
        map.remove(2);

        Assert.assertFalse(map.containsKey(2));
    }

    @Test
    public void testContainsValueAssertTrue() {
        String expectedResult = "value";

        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(23, expectedResult);
        map.put(90, "value1");
        map.put(54, "value2");
        map.put(72, "value3");

        Assert.assertTrue(map.containsValue(expectedResult));
    }

    @Test
    public void testContainsNullValueAssertTrue() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(23, null);
        map.put(90, "value1");
        map.put(54, "value2");
        map.put(72, "value3");

        Assert.assertTrue(map.containsValue(null));
    }

    @Test
    public void testContainsValueAssertFalseInEmptyMap() {
        LongMapImpl<String> map = new LongMapImpl<>();

        Assert.assertFalse(map.containsValue("value"));
    }

    @Test
    public void testContainsValueAssertFalse() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value1");
        map.put(5, "value2");
        map.put(7, "value3");

        Assert.assertFalse(map.containsValue("value"));
    }

    @Test
    public void testGetKeys() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value1");
        map.put(5, "value2");
        map.put(7, "value3");

        long[] keys = map.keys();

        Assert.assertNotNull(keys);

        Assert.assertTrue(contains(keys, 2));
        Assert.assertTrue(contains(keys, 5));
        Assert.assertTrue(contains(keys, 7));
    }

    @Test
    public void testGetValues() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value1");
        map.put(5, "value2");
        map.put(7, null);

        Object[] values = map.values();

        Assert.assertNotNull(values);

        List<Object> list = Arrays.asList(values);

        Assert.assertTrue(list.contains(null));
        Assert.assertTrue(list.contains("value1"));
        Assert.assertTrue(list.contains("value2"));
    }

    @Test
    public void testGetNotNullValues() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value1");
        map.put(5, "value2");
        map.put(7, "value3");

        Object[] values = map.values();

        Assert.assertNotNull(values);

        List<Object> list = Arrays.asList(values);

        Assert.assertTrue(list.contains("value1"));
        Assert.assertTrue(list.contains("value2"));
        Assert.assertTrue(list.contains("value3"));
    }

    @Test
    public void testClearEmptyMap() {
        LongMapImpl<String> map = new LongMapImpl<>();
        long size = map.size();

        map.clear();

        Assert.assertEquals(size, map.size());
    }

    @Test
    public void testClearMap() {
        LongMapImpl<String> map = new LongMapImpl<>();
        map.put(2, "value1");
        map.put(5, "value2");
        map.put(7, null);

        map.clear();

        Assert.assertEquals(0, map.size());
    }

    @Test
    public void testPutLargeNumberOfPair() {
        int totalPairs = 1000;
        LongMapImpl<String> map = new LongMapImpl<>();

        for (int i = 0; i < totalPairs; i++) {
            map.put(getRandomLong(), "value " + i);
        }

        Assert.assertEquals(1000, map.size());
    }

    private boolean contains(long[] array, long v) {
        boolean result = false;

        for(long i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }

    private long getRandomLong() {
        return new Random().nextLong();
    }
}
