package lab.cadl.lirui.study.java.basic.versions;

import javax.swing.text.DateFormatter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Java8 {

    public static void main(String[] args) {
        Predicate<String> notEmpty = s -> !s.isEmpty();
        System.out.println(notEmpty.test(""));

        Predicate<String> notNull = Objects::nonNull;
        notNull.and(s -> !s.isEmpty());
        System.out.println(notNull.test(null));

        Supplier<Person> factory = Person::new;
        Person person = factory.get();

        Optional<String> optional  = Optional.of("bam");
        optional.ifPresent(System.out::println);

        Stream.of("abc", "bcd", "dad", "cad")
                .sorted()
                .reduce((s1, s2) -> s1 + "#" + s2)
                .ifPresent(System.out::println);

        List<String> values = Utils.stringList(100_0000);
        Utils.timeIt(() -> System.out.println(values.stream().sorted().count()));
        Utils.timeIt(() -> System.out.println(values.parallelStream().sorted().count()));

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        System.out.println(now1.isBefore(now2));

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239

        long hours = ChronoUnit.HOURS.between(now1, now2);
        System.out.println(hours);

        Clock clock = Clock.systemDefaultZone();

        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(Locale.CHINA)
                .withZone(ZoneId.systemDefault());
        System.out.println(formatter.format(clock.instant()));

        TemporalAccessor time = formatter.parse("2018年3月28日 上午10时56分16秒");
        System.out.println(time);

        LocalDateTime localDateTime = LocalDateTime.parse("2018年3月28日 上午10时56分16秒", formatter);
        System.out.println(localDateTime);

        System.out.println(localDateTime.atZone(zone1));
        System.out.println(localDateTime.atZone(zone1).toInstant());
    }
}
