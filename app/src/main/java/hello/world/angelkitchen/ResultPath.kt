package hello.world.angelkitchen

data class ResultPath(
    val route : Result_trackoption,
    val message : String,
    val code : Int
)
data class Result_trackoption(
    val traoptimal : List<Result_path>
)
data class Result_path(
    val summary : Result_distance,
    val path : List<List<Double>>,
    val guide : List<guide>
)
data class Result_distance(
    val distance : Int
)
data class guide(
    val instructions : String
)