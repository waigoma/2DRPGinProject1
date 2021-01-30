package src.map

class MapManager {
    private var maps: LinkedHashMap<String, MapTemplate> = LinkedHashMap()
    private var currentMap: MapTemplate? = null
    private var change: Boolean = false

    /**
     * マップ登録
     * @param mapString マップ名(name.tmx)
     * @param map MapTemplate
     */
    fun register(mapString: String, map: MapTemplate){
        this.maps[mapString] = map
    }

    /**
     * マップ取得
     * @param mapString マップ名(name.tmx)
     * @return MapTemplate
     */
    fun get(mapString: String): MapTemplate{
        return maps[mapString]!!
    }

    /**
     * マップがあるかどうか確認
     * @param mapString マップ名(name.tmx)
     * @return Boolean
     */
    fun exists(mapString: String): Boolean{
        return maps.containsKey(mapString)
    }

    /**
     * 現在使用中のマップ設定
     * マップ変更したというbool値を変更
     * @param map MapTemplate
     */
    fun setCurrentMap(map: MapTemplate){
        currentMap = map
        change = true
    }

    /**
     * 現在使用中のマップ取得
     * @return MapTemplate
     */
    fun getCurrentMap(): MapTemplate{
        return currentMap!!
    }

    /**
     * 変更があるかどうか取得
     * @return Boolean
     */
    fun getChange(): Boolean {
        return change
    }

    /**
     * 変更あり
     */
    fun setChange() {
        change = true
    }

    /**
     * 変更終了の合図
     */
    fun notChange(){
        change = false
    }
}