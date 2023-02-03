package com.munene.haliyahewa.data.weather

enum class WeatherType(val range: IntRange) {
    RAIN(500..531),
    CLEAR(800..800),
    CLOUDS(801..804),
    THUNDERSTORM(200..232),
    DRIZZLE(300..321),
    ATMOSPHERE(701..781),
    SNOW(600..622),
}
