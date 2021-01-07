package src.character.player

import kotlin.collections.ArrayList

class PlayerPositionManager {
    private var playerX: Float = 0f
    private var playerY: Float = 0f
    private var playerSpeed: Int = 0
    private var playerWidth: Int = 0
    private var playerHeight: Int = 0

    private var canMove: Boolean = false

    private var up: Boolean = false
    private var down: Boolean = false
    private var left: Boolean = false
    private var right: Boolean = false

    /**
     * プレイヤーのx座標設定
     * @param px Float
     */
    fun setPlayerX(px: Float){
        playerX = px
    }

    /**
     * プレイヤーのy座標設定
     * @param py Float
     */
    fun setPlayerY(py: Float){
        playerY = py
    }

    /**
     * プレイヤースピード設定
     * @param speed Int
     */
    fun setPlayerSpeed(speed: Int){
        playerSpeed = speed
    }

    /**
     * プレイヤーの幅設定
     * @param width Int
     */
    fun setPlayerWidth(width: Int){
        playerWidth = width
    }

    /**
     * プレイヤーの高さ設定
     * @param height Int
     */
    fun setPlayerHeight(height: Int){
        playerHeight = height
    }

    /**
     * 上キー入力設定
     * @param up Boolean
     */
    fun setUp(up: Boolean){
        this.up = up
    }

    /**
     * 下キー入力設定
     * @param down Boolean
     */
    fun setDown(down: Boolean){
        this.down = down
    }

    /**
     * 左キー入力設定
     * @param left Boolean
     */
    fun setLeft(left: Boolean){
        this.left = left
    }

    /**
     * 右キー入力設定
     * @param right Boolean
     */
    fun setRight(right: Boolean){
        this.right = right
    }

    /**
     * プレイヤーが動けるかどうか設定
     * @param cm Boolean
     */
    fun setCanMove(cm: Boolean){
        this.canMove = cm
    }

    /**
     * プレイヤーのXY座標取得
     * @return ArrayList<Float> [X, Y]
     */
    fun getPlayerXY(): ArrayList<Float>{
        return ArrayList(listOf(
                playerX,
                playerY
        ))
    }

    /**
     * プレイヤーの縦横サイズ取得
     * @return ArrayList<Int> [Width, Height]
     */
    fun getPlayerWH(): ArrayList<Int>{
        return ArrayList(listOf(
                playerWidth,
                playerHeight
        ))
    }

    /**
     * プレイヤースピード取得
     * @return Int
     */
    fun getPlayerSpeed(): Int{
        return playerSpeed
    }

    /**
     * 上キー入力取得
     * @return Boolean
     */
    fun getUp(): Boolean{
        return up
    }

    /**
     * 下キー入力取得
     * @return Boolean
     */
    fun getDown(): Boolean{
        return down
    }

    /**
     * 左キー入力取得
     * @return Boolean
     */
    fun getLeft(): Boolean{
        return left
    }

    /**
     * 右キー入力取得
     * @return Boolean
     */
    fun getRight(): Boolean{
        return right
    }

    /**
     * プレイヤーが動けるかどうか
     * @return Boolean
     */
    fun getCanMove(): Boolean{
        return canMove
    }
}