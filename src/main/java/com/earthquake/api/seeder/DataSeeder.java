package com.earthquake.api.seeder;

import com.earthquake.api.model.Earthquake;
import com.earthquake.api.repository.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements ApplicationRunner {

    private final EarthquakeRepository earthquakeRepository;

    private static final String[][] LOCATIONS = {
        {"10km NE of Tokyo, Japan", "35.76", "139.83"},
        {"5km S of Osaka, Japan", "34.62", "135.50"},
        {"15km W of Sendai, Japan", "38.27", "140.75"},
        {"25km SW of Hokkaido, Japan", "42.78", "141.36"},
        {"8km W of Kyushu, Japan", "33.55", "130.38"},
        {"20km NE of San Francisco, CA", "37.83", "-122.31"},
        {"5km SW of Los Angeles, CA", "33.98", "-118.30"},
        {"10km SE of Seattle, WA", "47.52", "-122.21"},
        {"25km W of Anchorage, AK", "61.21", "-150.02"},
        {"50km SW of Fairbanks, AK", "64.12", "-148.92"},
        {"8km N of Santiago, Chile", "-33.33", "-70.62"},
        {"12km E of Valparaiso, Chile", "-33.02", "-71.47"},
        {"20km S of Concepcion, Chile", "-36.97", "-73.08"},
        {"6km W of Istanbul, Turkey", "41.02", "28.88"},
        {"15km NE of Ankara, Turkey", "39.98", "33.02"},
        {"8km S of Izmir, Turkey", "38.33", "27.08"},
        {"30km E of Erzincan, Turkey", "39.75", "39.95"},
        {"20km N of Jakarta, Indonesia", "-5.98", "106.85"},
        {"35km W of Sumatra, Indonesia", "-0.52", "100.18"},
        {"15km NE of Bali, Indonesia", "-8.38", "115.37"},
        {"5km E of Rome, Italy", "41.91", "12.54"},
        {"12km W of Naples, Italy", "40.83", "14.12"},
        {"18km N of Catania, Sicily", "37.68", "15.11"},
        {"8km N of Athens, Greece", "38.03", "23.74"},
        {"15km E of Thessaloniki, Greece", "40.65", "23.07"},
        {"25km W of Patras, Greece", "38.22", "21.58"},
        {"10km SW of Mexico City, Mexico", "19.32", "-99.19"},
        {"20km NE of Oaxaca, Mexico", "17.14", "-96.61"},
        {"15km E of Guadalajara, Mexico", "20.73", "-103.18"},
        {"5km SE of Lima, Peru", "-12.09", "-76.92"},
        {"18km W of Cusco, Peru", "-13.52", "-72.05"},
        {"12km N of Tehran, Iran", "35.82", "51.42"},
        {"8km E of Tabriz, Iran", "38.12", "46.41"},
        {"25km SW of Mashhad, Iran", "36.23", "59.49"},
        {"25km S of Wellington, New Zealand", "-41.42", "174.78"},
        {"10km N of Christchurch, New Zealand", "-43.42", "172.68"},
        {"30km W of Kathmandu, Nepal", "27.71", "85.18"},
        {"15km E of Sichuan, China", "30.65", "102.82"},
        {"20km NE of Kunming, China", "25.24", "102.93"},
        {"40km NE of Vanuatu", "-15.28", "167.04"},
        {"25km SE of Tonga", "-21.18", "-175.13"},
        {"15km N of Solomon Islands", "-9.28", "160.12"},
        {"30km W of Papua New Guinea", "-9.47", "147.12"},
        {"50km SW of Alaska Peninsula", "56.38", "-157.82"},
        {"20km NE of Philippines", "13.02", "121.85"},
        {"25km W of Quito, Ecuador", "-0.22", "-78.55"},
        {"12km E of Bogota, Colombia", "4.72", "-73.98"},
        {"15km SE of Managua, Nicaragua", "12.12", "-86.12"},
        {"20km W of Guatemala City", "14.62", "-90.62"},
        {"30km S of Costa Rica", "9.68", "-83.82"},
        {"15km W of Kabul, Afghanistan", "34.52", "68.98"},
        {"20km NE of Islamabad, Pakistan", "33.82", "73.22"},
        {"12km S of Delhi, India", "28.52", "77.18"},
        {"30km W of Mumbai, India", "19.02", "72.68"},
        {"8km N of Kathmandu, Nepal", "27.78", "85.32"},
        {"20km E of Bishkek, Kyrgyzstan", "42.90", "74.81"},
        {"15km SW of Almaty, Kazakhstan", "43.18", "76.73"},
        {"25km NE of Taipei, Taiwan", "25.22", "121.67"},
        {"10km W of Manila, Philippines", "14.62", "120.87"},
        {"18km S of Pyongyang, North Korea", "38.82", "125.68"},
        {"30km NE of Ulaanbaatar, Mongolia", "47.98", "107.18"},
    };

    private static final String[] TYPES = {
        "earthquake", "earthquake", "earthquake", "earthquake", "earthquake",
        "earthquake", "earthquake", "earthquake", "earthquake", "earthquake",
        "earthquake", "earthquake", "earthquake", "earthquake", "explosion",
        "earthquake", "earthquake", "earthquake", "earthquake", "quarry blast"
    };

    private static final String[] STATUSES = {
        "reviewed", "reviewed", "reviewed", "automatic", "automatic"
    };

    @Override
    public void run(ApplicationArguments args) {
        long count = earthquakeRepository.count();
        if (count > 0) {
            log.info("Database already has {} earthquake records, skipping seed", count);
            return;
        }

        log.info("Seeding earthquake database with 1200 records...");

        Random random = new Random(42);
        List<Earthquake> earthquakes = new ArrayList<>(1200);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);
        long totalMinutes = 60L * 24 * 365 * 5;

        for (int i = 0; i < 1200; i++) {
            String[] location = LOCATIONS[random.nextInt(LOCATIONS.length)];

            double magnitude = generateMagnitude(random);
            double depth = Math.round((5 + random.nextDouble() * 595) * 10.0) / 10.0;
            double lat = Math.round((Double.parseDouble(location[1]) + (random.nextDouble() - 0.5) * 2.0) * 10000.0) / 10000.0;
            double lon = Math.round((Double.parseDouble(location[2]) + (random.nextDouble() - 0.5) * 2.0) * 10000.0) / 10000.0;

            long offset = (long) (random.nextDouble() * totalMinutes);
            LocalDateTime occurredAt = startDate.plusMinutes(offset);

            earthquakes.add(Earthquake.builder()
                    .magnitude(magnitude)
                    .place(location[0])
                    .occurredAt(occurredAt)
                    .depth(depth)
                    .latitude(lat)
                    .longitude(lon)
                    .type(TYPES[random.nextInt(TYPES.length)])
                    .status(STATUSES[random.nextInt(STATUSES.length)])
                    .build());
        }

        earthquakeRepository.saveAll(earthquakes);
        log.info("Successfully seeded {} earthquake records", earthquakes.size());
    }

    private double generateMagnitude(Random random) {
        double r = random.nextDouble();
        double raw;
        if (r < 0.40) raw = 2.5 + random.nextDouble();
        else if (r < 0.70) raw = 3.5 + random.nextDouble();
        else if (r < 0.90) raw = 4.5 + random.nextDouble();
        else if (r < 0.98) raw = 5.5 + random.nextDouble() * 1.5;
        else raw = 7.0 + random.nextDouble() * 1.5;
        return Math.round(raw * 10.0) / 10.0;
    }
}
