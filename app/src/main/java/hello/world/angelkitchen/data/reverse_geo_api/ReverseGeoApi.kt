package hello.world.angelkitchen.data.reverse_geo_api


//data class ReverseGeoApi(
//    val results: List<Result>,
//    val status: Status
//)
//
//data class Result(
//    val code: Code,
//    val name: String,
//    val region: Region
//)
//
//data class Status(
//    val code: Int,
//    val message: String,
//    val name: String
//)
//
//data class Code(
//    val id: String,
//    val mappingId: String,
//    val type: String
//)
//
//data class Region(
//    val area0: Area0,
//    val area1: Area1,
//    val area2: Area2,
//    val area3: Area3,
//    val area4: Area4
//)
//
//data class Area0(
//    val coords: Coords,
//    val name: String
//)
//
//data class Area1(
//    val alias: String,
//    val coords: CoordsX,
//    val name: String
//)
//
//data class Area2(
//    val coords: CoordsXX,
//    val name: String
//)
//
//data class Area3(
//    val coords: CoordsXXX,
//    val name: String
//)
//
//data class Area4(
//    val coords: CoordsXXXX,
//    val name: String
//)
//
//data class Coords(
//    val center: Center
//)
//
//data class Center(
//    val crs: String,
//    val x: Double,
//    val y: Double
//)
//
//data class CoordsX(
//    val center: CenterX
//)
//
//data class CenterX(
//    val crs: String,
//    val x: Double,
//    val y: Double
//)
//
//data class CoordsXX(
//    val center: CenterXX
//)
//
//data class CenterXX(
//    val crs: String,
//    val x: Double,
//    val y: Double
//)
//
//data class CoordsXXX(
//    val center: CenterXXX
//)
//
//data class CenterXXX(
//    val crs: String,
//    val x: Double,
//    val y: Double
//)
//
//data class CoordsXXXX(
//    val center: CenterXXXX
//)
//
//data class CenterXXXX(
//    val crs: String,
//    val x: Double,
//    val y: Double
//)


data class ReverseGeoApi(
    val results: List<Result1>
)

data class Result1(
    val region: Region
)

data class Region(
    val area1: Area1,
    val area2: Area2,
    val area3: Area3
)

data class Area1(
    val name: String
)

data class Area2(
    val name: String
)

data class Area3(
    val name: String
)


data class TestData(
    val lat: Double,
    val lng: Double
)