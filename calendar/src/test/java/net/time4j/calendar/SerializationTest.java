package net.time4j.calendar;

import net.time4j.calendar.astro.JulianDay;
import net.time4j.history.HistoricEra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class SerializationTest {

    @Test
    public void serializeHijri() throws IOException, ClassNotFoundException {
        roundtrip(HijriCalendar.ofUmalqura(1437, 3, 17));
    }

    @Test
    public void serializePersian() throws IOException, ClassNotFoundException {
        roundtrip(PersianCalendar.of(1425, 1, 7));
    }

    @Test
    public void serializeMinguo() throws IOException, ClassNotFoundException {
        roundtrip(MinguoCalendar.of(MinguoEra.ROC, 105, 1, 7));
    }

    @Test
    public void serializeCoptic() throws IOException, ClassNotFoundException {
        roundtrip(CopticCalendar.of(1723, 13, 6));
    }

    @Test
    public void serializeEthiopianDate() throws IOException, ClassNotFoundException {
        roundtrip(EthiopianCalendar.of(EthiopianEra.AMETE_MIHRET, 2007, 13, 6));
    }

    @Test
    public void serializeEthiopianTime() throws IOException, ClassNotFoundException {
        roundtrip(EthiopianTime.ofDay(4, 45, 23));
    }

    @Test
    public void serializeJulian() throws IOException, ClassNotFoundException {
        roundtrip(JulianCalendar.of(HistoricEra.AD, 1752, 9, 14));
    }

    @Test
    public void serializeThaiSolar() throws IOException, ClassNotFoundException {
        roundtrip(ThaiSolarCalendar.of(ThaiSolarEra.BUDDHIST, 2482, 2, 7));
    }

    @Test
    public void serializeJapanese() throws IOException, ClassNotFoundException {
        roundtrip(JapaneseCalendar.ofGregorian(Nengo.HEISEI, 29, 4, 12));
        roundtrip(JapaneseCalendar.ofGregorian(Nengo.SHOWA, 64, 1, 7)); // would fail in lax mode
        roundtrip(JapaneseCalendar.of(Nengo.ofRelatedGregorianYear(1857), 4, EastAsianMonth.valueOf(5).withLeap(), 1));
    }

    @Test
    public void serializeIndian() throws IOException, ClassNotFoundException {
        roundtrip(IndianCalendar.of(1912, 5, 31));
    }

    @Test
    public void serializeJulianDay() throws IOException, ClassNotFoundException {
        roundtrip(JulianDay.ofEphemerisTime(2451545.0));
    }

    private static int roundtrip(Object obj)
        throws IOException, ClassNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        byte[] data = baos.toByteArray();
        oos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        assertThat(ois.readObject(), is(obj));
        ois.close();
        return data.length;
    }

}