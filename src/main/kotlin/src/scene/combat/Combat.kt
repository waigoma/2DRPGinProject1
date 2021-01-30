package src.scene.combat

import processing.core.PApplet
import src.Main
import src.character.mob.MobTemplate
import src.state.StateType

class Combat(private val plet: PApplet) {
    private val psm = Main.playerStatManager
    private val mm = Main.mobManager
    private val ca = CombatAnimation(plet)

    private var playerData = psm.getPlayerData()
    private lateinit var mobData: MobTemplate

    private var count = 0
    private var first = true
    private var playerFirst = true
    private var mobFirst = true

    private var playerTurn = true
    private var mobTurn = false

    private var attackKey = false

    private var narration = true
    private var eventing = false
    private var attackEvent = false

    private var levelup = false

    private var eventStartTime = 0
    private var currentTime = 0

    fun keyPressed() {
        if (plet.key == 'a' || plet.key == 'A') attackKey = true
    }

    fun keyReleased() {
    }

    fun display(){
        if (first){
            //todo 呼び出すときにmobの指定を出来るようにする
            val bg = this.javaClass.getResourceAsStream("/data/img/background/plains3.jpg")
            val mobImg = this.javaClass.getResourceAsStream("/data/img/mob/batta.png")
            plet.surface.setSize(1280, 720)

            val res = mm.getMobNames()[0]
            mm.setCurrentMob(mm.getMobData(res))
            mobData = mm.getCurrentMob()

            ca.setup(bg, mobImg)
            first = false
            plet.delay(100)
        }
        playerData = psm.getPlayerData()
        mobData = mm.getCurrentMob()
        currentTime = plet.millis()

        ca.draw(playerData, mobData)

        if (playerTurn && !mobTurn) playerTurn()
        if (mobTurn && !playerTurn) mobTurn()

        if (mobData.hp <= 0) deathEvent("mob")
        if (playerData.hp <= 0) deathEvent("player")
    }

    private fun playerTurn(){
        if (!eventing) {
            if (attackKey) {
                attackKey = false
                attackEvent = true
                eventing = true
                eventStartTime = plet.millis()
            } else {
                ca.narration("${mobData.name}が現れた。\n${playerData.name}はどうする？(TODO)")
            }
        }else{
            when {
                attackEvent -> attackEvent(100)
            }
            if (elapsed(1.5)){
                playerTurn = false
                mobTurn = true
                playerFirst = true
                eventStartTime = plet.millis()
            }else {
                playerFirst = false
            }
        }
    }

    private fun mobTurn(){
        when {
            attackEvent -> {
                attackEvent(10)
                ca.receiveDamage(true)
            }
        }
        if (elapsed(0.25)) ca.receiveDamage(false)
        if (elapsed(1.0)){
            playerTurn = true
            mobTurn = false
            eventing = false
            mobFirst = true
        }else{
            mobFirst = false
        }

    }

    private fun attackEvent(damage: Int){
        when {
            playerTurn -> {
                if (playerFirst) mobData.addHp(-damage)
                ca.narration("敵に${damage}ダメージ与えた。")
            }
            mobTurn -> {
                if (mobFirst) playerData.addHp(-damage)
                ca.narration("${damage}ダメージ受けた。")
            }
        }
    }

    private fun deathEvent(name: String){
        playerTurn = false
        mobTurn = false
        if (!eventing){
            eventing = true
            eventStartTime = plet.millis()
        }
        when (name){
            "player" -> {
                ca.narration("死んでしまった...")
                if (elapsed(1.0)){
                    //todo プレイヤー情報で HP0 の状態で保存されているはずなので初期化する
                    initialize()
                    Main.stateType.setState(StateType.TITLE_STATE)
                }
            }

            "mob" ->{
                if (narration) {
                    if (!elapsed(2.0)) ca.narration("${mobData.name}を倒した！")
                    if (elapsed(2.0)) {
                        if (count == 0){
                            //todo 範囲指定してランダムにする
                            if(playerData.addExp(mobData.exp)) levelup = true
                            playerData.addMoney(mobData.money)
                            count = 1
                        }
                        ca.narration("${mobData.exp}経験値、${mobData.money}円獲得した！")
                    }
                    if (elapsed(3.5)){
                        narration = false
                        eventStartTime = plet.millis()
                    }
                }else {
                    if (levelup) ca.narration("レベルが${playerData.level}に上がった！")
                    if (elapsed(2.0)) {
                        initialize()
                        Main.stateType.setState(StateType.WORLD_STATE)
                    }
                }
            }
        }
    }

    private fun elapsed(second: Double): Boolean{
        val elapsedTime = currentTime - eventStartTime
        return elapsedTime > 1000 * second
    }

    private fun initialize(){
        count = 0
        first = true
        playerFirst = true
        mobFirst = true

        playerTurn = true
        mobTurn = false

        attackKey = false

        narration = true
        eventing = false
        attackEvent = false

        levelup = true

        eventStartTime = 0
        currentTime = 0
    }
}