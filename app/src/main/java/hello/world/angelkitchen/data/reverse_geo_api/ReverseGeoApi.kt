package hello.world.angelkitchen.data.reverse_geo_api

data class ReverseGeoApi(
    val results: List<Result1>?
)

data class Result1(
    val land: Land?,
    val region: Region?
)

data class Land(
    val number1: String?,
    val number2: String?,
)

data class Region(
    val area1: Area1?,
    val area2: Area2?,
    val area3: Area3?,
    val area4: Area4?
)

data class Area1(
    val name: String?
)

data class Area2(
    val name: String?
)

data class Area3(
    val name: String?
)

data class Area4(
    val name: String?
)

