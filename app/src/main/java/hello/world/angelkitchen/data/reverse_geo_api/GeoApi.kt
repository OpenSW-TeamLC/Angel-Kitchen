package hello.world.angelkitchen.data.reverse_geo_api

data class GeoApi(
    val addresses: List<Addresse>?,
)

data class Addresse(
    val jibunAddress: String?,
    val x: String?,
    val y: String?
)