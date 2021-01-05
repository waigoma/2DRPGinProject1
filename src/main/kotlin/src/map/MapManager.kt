package src.map

class MapManager {
    private var maps: LinkedHashMap<String, MapTemplate> = LinkedHashMap()
    private var currentMap: MapTemplate? = null
    private var change: Boolean = false

    fun register(mapString: String, map: MapTemplate){
        this.maps[mapString] = map
    }

    fun get(mapString: String): MapTemplate{
        return maps[mapString]!!
    }

    fun exists(mapString: String): Boolean{
        return maps.containsKey(mapString)
    }

    fun setCurrentMap(map: MapTemplate){
        currentMap = map
        change = true
    }

    fun getCurrentMap(): MapTemplate{
        return currentMap!!
    }

    fun getChange(): Boolean {
        return change
    }

    fun notChange(){
        change = false
    }
}