package src.map

class MapManager {
    private var maps: LinkedHashMap<String, MapTemplate> = LinkedHashMap()

    fun register(mapString: String, map: MapTemplate){
        this.maps[mapString] = map
    }

    fun get(mapString: String): MapTemplate{
        return maps[mapString]!!
    }

    fun exists(mapString: String): Boolean{
        return maps.containsKey(mapString)
    }
}